package dashboardcommunication.impl;

import jssc.CommChannel;
import jssc.SerialCommChannel;
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

    private static final int PORT = 8080;
    private static int valveLevel = 42;
    private static final String mqttBroker = "tcp://test.mosquitto.org:1883";
    private static double waterLevel;
    private static final String SerialPort = "COM3";
    private static final int SerialRate = 9600;
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();

        MqttManager mqttManager = new MqttManager(mqttBroker);
        mqttManager.setOnMessageReceivedListener(new OnMessageReceivedListener() {
            @Override
            public void onMessageReceived(String topic, String message) {
                System.out.println("Messaggio ricevuto: " + message);
                waterLevel = Integer.parseInt(message);
            }

        });

        System.out.println("Client connected");

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        //SerialConnection
        CommChannel channel = new SerialCommChannel(SerialPort,SerialRate);

        Logic logic = new LogicImpl(randomizer());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                String data = createData();
                out.println(data);
                logic.updateEnvironment(waterLevel);
                mqttManager.sendMessage(String.valueOf(logic.getFrequency()));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        String temp = "";

        while ((temp = reader.readLine()) != null) {
            valveLevel = Integer.parseInt((temp));
            channel.sendMsg(String.valueOf(valveLevel));
            System.out.println("Value: " + valveLevel);
        }
        server.close();
    }

    private static String createData() {
        SystemState state = new SystemStateImpl(SystemState.State.NORMAL);
        List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state.getSystemState().getState());
        jsonObject.put("valve_level", valveLevel);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(dataList);
        jsonObject.put("numbers", jsonArray);
        return jsonObject.toJSONString();
    }

    private static double randomizer() {
        double leftLimit = 1D;
        double rightLimit = 10D;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }
}
