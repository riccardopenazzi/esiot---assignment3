package me.riccardo.dashboard_esiot3.dashboard.api;

public interface DashboardModel {
    String getLevel();
    int getValveLevel();
    void setValveLevel(int level);
}
