package dashboardcommunication.impl;

import jssc.CommChannel;
import jssc.SerialCommChannel;

import org.eclipse.paho.client.mqttv3.util.Strings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.api.Logic;
import model.api.SystemState;
import model.impl.LogicImpl;
import model.impl.SystemStateImpl;
import mqtt.api.OnMessageReceivedListener;
import mqtt.impl.MqttManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class DashboardServer {

    private static final String KEY = "state";

    private static final int PORT = 8080;
    private static final String mqttBroker = "tcp://test.mosquitto.org:1883";
    private static double waterLevel;
    private static final String SerialPort = "COM8";
    private static final int SerialRate = 9600;
    private static boolean msgReceived = false;
    private static double oldFrequency = -1;
    private static String serialMsg;
    private static boolean isManual = false;

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();

        System.out.println("Client connected");

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // SerialConnection
        CommChannel channel = new SerialCommChannel(SerialPort, SerialRate);

        Logic logic = new LogicImpl(randomizer(), isManual);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            MqttManager mqttManager = new MqttManager(mqttBroker);
            mqttManager.setOnMessageReceivedListener(new OnMessageReceivedListener() {
                @Override
                public void onMessageReceived(String topic, String message) {
                    System.out.println("Messaggio ricevuto: " + message);
                    waterLevel = Integer.parseInt(message);
                    msgReceived = true;
                    System.out.println("MsgRceived: " + msgReceived);
                }

            });
            while (true) {
                // System.out.println("Sto per entrare un attesa");
                while (!msgReceived) {
                    if (channel.isMsgAvailable()) {
                        try {
                            serialMsg = channel.receiveMsg();
                            /* Manual mode */
                            if (serialMsg.equalsIgnoreCase("manual")) {
                                System.out.println("MANUAL");
                                isManual = true;
                            /* Automatic mode */
                            } else if (serialMsg.equalsIgnoreCase("automatic")) {
                                System.out.println("AUTOMATIC");
                                isManual = false;
                            }

                            String valveLevel = channel.receiveMsg();
                            if (isManual && !valveLevel.equalsIgnoreCase("automatic")) {
                                logic.setValveLevel(Integer.parseInt(valveLevel));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // System.out.println("Fine attesa");
                msgReceived = false;
                logic.updateEnvironment(waterLevel, isManual);
                String data = createData(logic);
                out.println(data);
                // System.out.println("Old: " + oldFrequency + " current: " +
                // logic.getFrequency());
                if (oldFrequency != logic.getFrequency() || oldFrequency == -1) {
                    System.out.println("Aggiorno valore");
                    mqttManager.sendMessage(String.valueOf(logic.getFrequency()));
                    oldFrequency = logic.getFrequency();
                }
            }
        });

        String temp = "";

        while ((temp = reader.readLine()) != null) {
            logic.setValveLevel(Integer.parseInt((temp)));
            channel.sendMsg(String.valueOf(logic.getValveLevel()));
        }
        server.close();
    }

    private static String createData(final Logic logic) {
        SystemState state = new SystemStateImpl(logic.getState());
        List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state.getSystemState().getState());
        jsonObject.put("valve_level", logic.getValveLevel());
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(dataList);
        jsonObject.put("numbers", jsonArray);
        jsonObject.put("water_level", waterLevel);
        return jsonObject.toJSONString();
    }

    private static double randomizer() {
        double leftLimit = 1D;
        double rightLimit = 10D;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }
}
