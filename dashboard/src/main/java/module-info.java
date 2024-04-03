module me.riccardo.dashboard_esiot3 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires json.simple;


    opens me.riccardo.dashboard_esiot3 to javafx.fxml;
    exports me.riccardo.dashboard_esiot3;
}
