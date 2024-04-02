package dashboardcommunication.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import dashboardcommunication.api.SystemState;

public class Server {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        HttpHandler handler = createHandler();
        server.createContext("/request", handler);
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running...");
    }

    private static HttpHandler createHandler() {

        return new HttpHandler() {

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                final String jsonData = createData();
                // Send JSON response
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonData.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonData.getBytes());
                os.close();
            }
        };
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

