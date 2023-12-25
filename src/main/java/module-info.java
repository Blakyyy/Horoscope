module com.horoscope.horoscopeapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.horoscope.horoscopeapp to javafx.fxml;
    exports com.horoscope.horoscopeapp;
}