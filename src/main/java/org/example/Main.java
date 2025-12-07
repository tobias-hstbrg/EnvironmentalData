package org.example;

public class Main {


    public static void main(String[] args) throws Exception {
        WeatherDataService weatherService = new WeatherDataService();
        var noaaResponse = weatherService.getData();

        System.out.println("Wetterstations ID: " + noaaResponse.stationId());
        System.out.println("Temperatur: " + noaaResponse.temperatureCelsius() + "°");
        System.out.println("Luftfeuchtigkeit: " + noaaResponse.relativeHumidity());
        System.out.println("Datenzeitraum: " + noaaResponse.timestamp());
    }
}
