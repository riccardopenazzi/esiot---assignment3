package me.riccardo.dashboard_esiot3.dashboard.api;

import java.util.List;

public interface DashboardController {

    DashboardView getView();

    void setValues(String state, int valve, List<Pair<String, Double>> waterLevel);

    void sendUpdates(int value);

    void updateValveValueView();

    boolean isModelNull();

    String getRiverLevel();

    int getValveLevel();
}
