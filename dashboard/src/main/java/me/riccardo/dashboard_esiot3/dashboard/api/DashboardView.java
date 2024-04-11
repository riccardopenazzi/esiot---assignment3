package me.riccardo.dashboard_esiot3.dashboard.api;

import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.List;

public interface DashboardView {

    void saveValue(MouseEvent event);

    String getStatus();

    String getValveLevel();

    String getTextValue();

    void setStatus(String status);

    void setValveLevel(String level);

    void setTextValue(String text);

    boolean isStatusLabelNull();

    boolean isValveLevelLabelNull();

    //void popolateLineChart(HashMap<String, Double> values);

    void popolateLineChart(List<Pair<String, Double>> values);

}
