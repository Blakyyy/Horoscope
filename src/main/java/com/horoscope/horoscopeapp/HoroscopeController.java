package com.horoscope.horoscopeapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HoroscopeController {
    private String sign;
    private String date;
    private String DMW = "";
    private boolean monthly = false;
    private boolean weekly = false;

    @FXML
    private VBox signDetailsBox;

    @FXML
    private VBox responseHoroscopeBox;

    @FXML
    private Label signDetailsTitle;

    @FXML
    private Label responseHoroscopeTitle;

    @FXML
    private Label responseHoroscopeMessage;

    @FXML
    private Label errorLabel;

    @FXML
    private void onSignButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedSign = clickedButton.getText();

        signDetailsTitle.setText("Choose the day for " + selectedSign + " horoscope");
        signDetailsBox.setVisible(true);
        responseHoroscopeBox.setVisible(false);
        this.sign = selectedSign;

    }

    @FXML
    private void onDateButtonClick(ActionEvent event){
        Button clickedButton = (Button) event.getSource();
        this.date = clickedButton.getText();
        selectDate(this.date);
        getHoroscope();
    }

    @FXML
    private void getHoroscope() {
        String originalURL = "https://horoscope-app-api.vercel.app/api/v1/get-horoscope/";
        String finalURL = constructURL(originalURL);

        try {
            URL url = new URL(finalURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Response code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(conn.getInputStream());

                String horoscope = parseJSONData(response);
                responseHoroscopeBox.setVisible(true);

                responseHoroscopeTitle.setText("Your " + this.DMW + " horoscope" + " is:");

                responseHoroscopeMessage.setWrapText(true);
                responseHoroscopeMessage.setText(horoscope);

            } else {
                responseHoroscopeBox.setVisible(true);
                responseHoroscopeTitle.setText("");
                errorLabel.setText("An error occurred while getting your horoscope. Please try again later.");
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectDate(String selectDate){
        switch (selectDate) {
            case "Today": {
                LocalDate localDate = LocalDate.now();
                this.date = formatDate(localDate);
                this.DMW = "daily";
                break;
            }
            case "Weekly": {
                this.weekly = true;
                this.DMW = "weekly";
                break;
            }
            case "Monthly": {
                this.monthly = true;
                this.DMW = "monthly";
                break;
            }
        }
    }

    private String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private String parseJSONData(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject data  = (JSONObject) json.get("data");
        return (String) data.get("horoscope_data");
    }

    private String readResponse(InputStream conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    private String constructURL(String url){
        if (this.monthly) {
            url += "monthly?sign=" + this.sign;
        } else if (this.weekly) {
            url += "weekly?sign=" + this.sign;
        } else {
            url += "daily?sign=" + this.sign + "&day=" + this.date;
        }

        this.weekly = false;
        this.monthly = false;
        return url;
    }
}