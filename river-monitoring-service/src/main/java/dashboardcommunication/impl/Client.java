package dashboardcommunication.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class Client {

    private static final int PORT = 8080;
    private static final String HOST = "http://localhost:";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HOST + PORT + "/request"))
                .build();

        while (true) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            if (response.statusCode() == 200) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.body());
                String state = jsonObject.get("state").toString();
                long valveLevel = (long) jsonObject.get("valve_level");
                JSONArray jsonArray = (JSONArray) jsonObject.get("numbers");

                StringBuilder numbers = new StringBuilder();
                for (final Object obj : jsonArray) {
                    numbers.append(obj).append(", ");
                }

                System.out.println("State: " + state);
                System.out.println("Valve_level: " + valveLevel);
                System.out.println("Numbers: " + numbers.toString());
            } else {
                System.err.println("Failed to fetch data from server. Status code: " + response.statusCode());
            }
            TimeUnit.SECONDS.sleep(5); // Wait for 5 seconds
        }
    }
}

