package me.riccardo.dashboard_esiot3.dashboard.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

public class DashboardController implements Initializable {

    private DashboardModelImpl model;

    @FXML
    private Button btn_save;

    @FXML
    private Label lb_level;

    @FXML
    private Label lb_status;

    @FXML
    private TextField txtValve;

    public DashboardController() {
        try {
            this.model = new DashboardModelImpl("Normal", 100, this);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveValue(MouseEvent event) {
        System.out.println("Save");
        if (!this.txtValve.getText().isEmpty()) {
            this.model.sendUpdates(Integer.parseInt(this.txtValve.getText()));
            this.txtValve.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lb_status.setText(this.model.getLevel());
        this.lb_level.setText(String.valueOf(this.model.getValveLevel()));
    }

    public void setValues(final String state, final int valve) {
        if (this.lb_status != null && this.lb_level != null) {
            this.lb_status.setText(state);
            this.lb_level.setText(String.valueOf(valve));
        }
    }
}
