package me.riccardo.dashboard_esiot3.dashboard.impl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardView.class.getResource("/me/riccardo/dashboard_esiot3/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("River Monitoring Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
        new DashboardController();
    }
}