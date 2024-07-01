package me.riccardo.dashboard_esiot3.dashboard.impl;

import me.riccardo.dashboard_esiot3.dashboard.api.DashboardController;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardModel;
import me.riccardo.dashboard_esiot3.dashboard.api.Pair;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    private List<Pair<String, Double>> waterLevelHistory;
    private final DashboardController controller;


    public DashboardModelImpl(final String riverLevel, final int valveLevel, final DashboardController controller) {
        this.riverLevel = riverLevel;
        this.valveLevel = valveLevel;
        this.controller = controller;
        this.waterLevelHistory = new ArrayList<>();
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
            insertValue(time, this.waterLevel);
            final StringBuilder numbers = new StringBuilder();
            for (final Object obj : jsonArray) {
                numbers.append(obj).append(", ");
            }
            this.controller.setValues(this.riverLevel, this.valveLevel, this.waterLevelHistory);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    private void insertValue(final String time, final Double value) {
        if (this.waterLevelHistory.size() >= MAX_SIZE) {
            this.waterLevelHistory.remove(0);
        }
        this.waterLevelHistory.add(new Pair<String,Double>(time, value));
    }

}
