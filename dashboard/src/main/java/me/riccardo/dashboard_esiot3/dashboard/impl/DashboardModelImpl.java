package me.riccardo.dashboard_esiot3.dashboard.impl;

import javafx.scene.chart.XYChart;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardController;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DashboardModelImpl implements DashboardModel {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final int MAX_SIZE = 2;

    private Socket client;
    private PrintWriter out;
    private InputStreamReader in;
    private BufferedReader reader;
    private String riverLevel;
    private int valveLevel;
    private Double waterLevel;
    private HashMap<String, Double> waterLevelHistory;
    private final DashboardController controller;


    public DashboardModelImpl(final String riverLevel, final int valveLevel, final DashboardController controller) {
        this.riverLevel = riverLevel;
        this.valveLevel = valveLevel;
        this.controller = controller;
        this.waterLevelHistory = new HashMap<>();
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

    @Override
    public void sendUpdates(final int value) {
        this.valveLevel = value;
        this.out.println(this.valveLevel);
        this.out.flush();
    }

    @Override
    public String getLevel() {
        return this.riverLevel;
    }

    @Override
    public int getValveLevel() {
        return this.valveLevel;
    }

    @Override
    public void setValveLevel(int level) {
        this.valveLevel = level;
    }

    private void getUpdates(final String data) {
        try {
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(data);
            this.riverLevel = jsonObject.get("state").toString();
            this.valveLevel = Integer.parseInt(jsonObject.get("valve_level").toString());
            this.waterLevel = Double.parseDouble(jsonObject.get("water_level").toString());
            final JSONArray jsonArray = (JSONArray) jsonObject.get("numbers");

            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            //insertValue(time, this.waterLevel);
            this.waterLevelHistory.put(time, this.waterLevel);
            final StringBuilder numbers = new StringBuilder();
            for (final Object obj : jsonArray) {
                numbers.append(obj).append(", ");
            }

            //System.out.println("hhhshs: " + this.valveLevel);
            //System.out.println("Numbers: " + numbers.toString());
            //System.out.println("River level: " + this.riverLevel + "; Valve level: " + this.valveLevel);
            this.controller.setValues(this.riverLevel, this.valveLevel, this.waterLevelHistory);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }
/*
    private void insertValue(final String time, final Double value) {
        System.out.println(this.waterLevelHistory);
        String
        if (this.waterLevelHistory.size() == MAX_SIZE) {
            for (Map.Entry<String, Double> entry : this.waterLevelHistory.entrySet()) {
                String key = entry.getKey();
                Double data = entry.getValue();

            }
            //this.waterLevelHistory.entrySet().stream().toList().remove(0);
            this.waterLevelHistory.
            System.out.println(this.waterLevelHistory.keySet().);
            this.waterLevelHistory.entrySet().remove(this.waterLevelHistory.keySet().stream().findFirst().get());
        }
        this.waterLevelHistory.put(time, value);
    }
 */
}
