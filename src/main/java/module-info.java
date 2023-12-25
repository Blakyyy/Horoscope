module com.horoscope.horoscopeapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.horoscope.horoscopeapp to javafx.fxml;
    exports com.horoscope.horoscopeapp;
}