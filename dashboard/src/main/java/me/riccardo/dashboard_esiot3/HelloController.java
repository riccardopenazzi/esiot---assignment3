package me.riccardo.dashboard_esiot3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HelloController {

    @FXML
    private Button btn_dec;

    @FXML
    private Button btn_inc;

    @FXML
    private Button btn_save;

    @FXML
    private Label lb_level;

    @FXML
    private Label lb_status;

    @FXML
    void decrementValve(MouseEvent event) {
        System.out.println("Decrement");
    }

    @FXML
    void incrementValve(MouseEvent event) {
        System.out.println("Increment");
    }

    @FXML
    void saveValue(MouseEvent event) {
        System.out.println("Save");
    }

}
