package me.riccardo.dashboard_esiot3.dashboard.api;

public interface DashboardModel {

    void sendUpdates(int value);

    String getLevel();

    int getValveLevel();

    void setValveLevel(int level);
}
