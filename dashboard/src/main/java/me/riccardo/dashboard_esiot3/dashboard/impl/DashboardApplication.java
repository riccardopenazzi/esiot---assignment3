package me.riccardo.dashboard_esiot3.dashboard.impl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardController;
import me.riccardo.dashboard_esiot3.dashboard.api.DashboardView;

import java.io.IOException;

public class DashboardApplication extends Application {

    private final DashboardController controller;
    private final DashboardView view;

    public DashboardApplication() {
        this.controller = new DashboardControllerImpl();
        this.view = this.controller.getView();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardApplication.class.getResource("/me/riccardo/dashboard_esiot3/hello-view.fxml"));
        fxmlLoader.setController(view);

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("River Monitoring Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}