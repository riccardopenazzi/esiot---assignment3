package me.riccardo.dashboard_esiot3.dashboard.api;

public interface DashboardController {

    DashboardView getView();

    void setValues(String state, int valve);

    void sendUpdates(int value);

    void updateValveValueView();

    boolean isModelNull();

    String getRiverLevel();

    int getValveLevel();
}
