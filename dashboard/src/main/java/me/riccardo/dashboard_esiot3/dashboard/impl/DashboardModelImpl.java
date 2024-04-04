package me.riccardo.dashboard_esiot3.dashboard.impl;

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

public class DashboardModelImpl implements DashboardModel {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private Socket client;
    private PrintWriter out;
    private InputStreamReader in;
    private BufferedReader reader;
    private String riverLevel;
    private int valveLevel;
    private final DashboardController dsController;

    public DashboardModelImpl(final String riverLevel, final int valveLevel, final DashboardController dsController) throws IOException {
        this.riverLevel = riverLevel;
        this.valveLevel = valveLevel;
        this.dsController = dsController;
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
        System.out.println("GETUPDATE");
        try {
            final JSONParser parser = new JSONParser();
            final JSONObject jsonObject = (JSONObject) parser.parse(data);
            this.riverLevel = jsonObject.get("state").toString();
            //System.out.println();
            int tmp = Integer.parseInt(jsonObject.get("valve_level").toString());
            final JSONArray jsonArray = (JSONArray) jsonObject.get("numbers");

            final StringBuilder numbers = new StringBuilder();
            for (final Object obj : jsonArray) {
                numbers.append(obj).append(", ");
            }
            this.valveLevel = tmp;
            System.out.println("hhhshs: " + tmp);
            System.out.println("Numbers: " + numbers.toString());
            this.dsController.setValues(this.riverLevel, this.valveLevel);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public void sendUpdates(final int value) {
        this.valveLevel = value;
        this.out.println(this.valveLevel);
        this.out.flush();
        //this.dsController.setValues(this.riverLevel, this.valveLevel);
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
}
