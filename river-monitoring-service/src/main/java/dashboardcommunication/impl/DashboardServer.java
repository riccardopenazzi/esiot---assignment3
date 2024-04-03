package dashboardcommunication.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

import dashboardcommunication.api.SystemState;

public class DashboardServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(PORT);
        Socket socket = server.accept();

        System.out.println("Client connected");

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                String data = createData();
                out.println(data);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        String temp = "";
        long value = 0L;

        while ((temp = reader.readLine()) != null) {
            value = Long.parseLong(temp);
            System.out.println("Value: " + value);
        }
        server.close();
    }

    private static String createData() {
        SystemState state = new SystemStateImpl(SystemState.State.NORMAL);
        int valveLevel = 42;
        List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", state.getSystemState().getState());
        jsonObject.put("valve_level", valveLevel);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(dataList);
        jsonObject.put("numbers", jsonArray);
        return jsonObject.toJSONString();
    }
}
