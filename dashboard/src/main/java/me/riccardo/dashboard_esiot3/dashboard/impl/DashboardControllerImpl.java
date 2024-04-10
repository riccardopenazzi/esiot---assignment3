package me.riccardo.dashboard_esiot3.dashboard.impl;

import javafx.application.Platform;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardController;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardModel;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardView;

import java.util.HashMap;

public class DashboardControllerImpl implements DashboardController {

    private final DashboardModel model;
    private final DashboardView view;

    public DashboardControllerImpl() {
        this.model = new DashboardModelImpl("Normal", 100, this);
        this.view = new DashboardViewImpl(this);
    }

    @Override
    public DashboardView getView() {
        return this.view;
    }

    @Override
    public void setValues(final String state, final int valve, final HashMap<String, Double> waterLevelHistory) {
        /* To ensure that it runs on the JavaFX Application Thread */
        Platform.runLater(() -> {
            if (!this.view.isStatusLabelNull() && !this.view.isValveLevelLabelNull()) {
                this.view.setStatus(state);
                this.view.setValveLevel(String.valueOf(valve));
                this.view.popolateLineChart(waterLevelHistory);
            }
        });
    }

    @Override
    public void updateValveValueView() {
        this.view.setValveLevel(String.valueOf(getValveLevel()));
    }

    @Override
    public void sendUpdates(final int value) {
        this.model.sendUpdates(value);
    }

    @Override
    public boolean isModelNull() {
        return this.model == null;
    }



    @Override
    public String getRiverLevel() {
        return this.model.getLevel();
    }



    @Override
    public int getValveLevel() {
        return this.model.getValveLevel();
    }
}
