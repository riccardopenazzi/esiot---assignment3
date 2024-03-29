module me.riccardo.dashboard_esiot3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.riccardo.dashboard_esiot3 to javafx.fxml;
    exports me.riccardo.dashboard_esiot3;
}