package org.example;

public class Main {


    public static void main(String[] args) throws Exception {
        WeatherDataService weatherService = new WeatherDataService();
        WaterDataService waterService = new WaterDataService();
        var noaaResponse = weatherService.getData();

        var waterData = waterService.getLatestWaterData();
        System.out.println("Latest Water Temperature: " + waterData.waterTemperature() + "°C");
        System.out.println("Latest Salinity: " + waterData.salinity() + " µS/cm");

        System.out.println("-------------------------------------------------------------------------");

        System.out.println("Wetterstations ID: " + noaaResponse.stationId());
        System.out.println("Temperatur: " + noaaResponse.temperatureCelsius() + "°");
        System.out.println("Luftfeuchtigkeit: " + noaaResponse.relativeHumidity());
        System.out.println("Datenzeitraum: " + noaaResponse.timestamp());
    }
}
