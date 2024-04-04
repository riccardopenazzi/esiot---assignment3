module me.riccardo.dashboard_esiot3.dashboard {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires json.simple;

    exports me.riccardo.dashboard_esiot3.dashboard.api;
    opens me.riccardo.dashboard_esiot3.dashboard.api to javafx.fxml;
    exports me.riccardo.dashboard_esiot3.dashboard.impl;
    opens me.riccardo.dashboard_esiot3.dashboard.impl to javafx.fxml;
}
