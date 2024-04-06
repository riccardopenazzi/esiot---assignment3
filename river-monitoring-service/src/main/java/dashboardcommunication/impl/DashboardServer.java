package dashboardcommunication.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.api.Logic;
import model.api.SystemState;
import model.impl.LogicImpl;
import model.impl.SystemStateImpl;

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
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();

        System.out.println("Client connected");

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Logic logic = new LogicImpl(randomizer());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                String data = createData();
                out.println(data);
                logic.updateEnvironment(randomizer());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }); 

        String temp = "";

        while ((temp = reader.readLine()) != null) {
            valveLevel = Integer.parseInt((temp));

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
