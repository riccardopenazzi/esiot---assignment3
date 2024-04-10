package me.riccardo.dashboard_esiot3.dashboard.api;

import java.util.HashMap;

public interface DashboardController {

    DashboardView getView();

    void setValues(String state, int valve, HashMap<String, Double> waterLevel);

    void sendUpdates(int value);

    void updateValveValueView();

    boolean isModelNull();

    String getRiverLevel();

    int getValveLevel();
}
