package me.riccardo.dashboard_esiot3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DashboardModel {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private Socket client;
    private PrintWriter out;
    private InputStreamReader in;
    private BufferedReader reader;
    private final long value = 40L;

    public DashboardModel() throws IOException {
        Thread thread = new Thread(() ->{
            try {
                this.client = new Socket(HOST, PORT);
                this.out = new PrintWriter(this.client.getOutputStream(), true);
        
                this.in = new InputStreamReader(this.client.getInputStream());
                this.reader = new BufferedReader(this.in);
        
                String line = "";
                while (true) {
                    line = this.reader.readLine();
                    getUpdates(line);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void getUpdates(final String data) {
        try {
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(data);
            final String state = jsonObject.get("state").toString();
            final long valveLevel = (long) jsonObject.get("valve_level");
            final JSONArray jsonArray = (JSONArray) jsonObject.get("numbers");

            final StringBuilder numbers = new StringBuilder();
            for (final Object obj : jsonArray) {
                numbers.append(obj).append(", ");
            }

            System.out.println("State: " + state);
            System.out.println("Valve_level: " + valveLevel);
            System.out.println("Numbers: " + numbers.toString());
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public void sendUpdates() {
        this.out.println(this.value);
        this.out.flush();
    }
}
