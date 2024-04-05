package me.riccardo.dashboard_esiot3.dashboard.impl;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardController;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardView;

public class DashboardViewImpl implements DashboardView, Initializable {

    @FXML
    private Button btn_save;

    @FXML
    private Label lb_level;

    @FXML
    private Label lb_status;

    @FXML
    private TextField txtValve;

    private DashboardController controller;

    public DashboardViewImpl(final DashboardController controller) {
        this.controller = controller;
    }

    @FXML
    @Override
    public void saveValue(final MouseEvent event) {
        System.out.println("Save");
        if (!getTextValue().isEmpty()) {
            this.controller.sendUpdates(Integer.parseInt(getTextValue()));
            this.controller.updateValveValueView();
            setTextValue("");
        }
    }

    @Override
    public String getStatus() {
        return this.lb_status.getText();
    }

    @Override
    public String getValveLevel() {
        return this.lb_level.getText();
    }

    @Override
    public String getTextValue() {
        return this.txtValve.getText();
    }

    @Override
    public void setStatus(final String status) {
        this.lb_status.setText(status);
    }

    @Override
    public void setValveLevel(final String level) {
        this.lb_level.setText(level);
    }

    @Override
    public void setTextValue(final String text) {
        this.txtValve.setText(text);
    }

    @Override
    public boolean isStatusLabelNull() {
        return this.lb_status == null;
    }

    @Override
    public boolean isValveLevelLabelNull() {
        return this.lb_level == null;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        if (!this.controller.isModelNull()) {
            setStatus(this.controller.getRiverLevel());
            setValveLevel(String.valueOf(this.controller.getValveLevel()));
        }
    }

}
