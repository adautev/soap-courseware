package com.itce.courseware.soap;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.jws.WebService;
import javax.jws.WebParam
import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@WebService
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class Weather {
    public String getTemperature(@WebParam(name = "town") String town) {
        return getOpenWeatherTemperature(town);
    }
    private static String getOpenWeatherTemperature(String town) {
        JSONObject json = null;
        try {
            URL url = new URL(String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=40fa41c665ba4a170f4bccad311467d2", town));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            if(connection.getResponseCode() == 404) {
                return "City not found.";
            }
            InputStream inputStream = connection.getInputStream();
            json = (JSONObject) JSONValue.parse(new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return  ((JSONObject) json.get("main")).get("temp").toString();
    }
}
