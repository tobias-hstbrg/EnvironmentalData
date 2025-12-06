package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.NoaaResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static final ObjectMapper mapper = new ObjectMapper();

    // The NOAA API does redirects so in order to get an HTTP 200 Code, allowing redirects is mandatory.
    private static final HttpClient http = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public static void main(String[] args) throws Exception {

        // API Endpoint
        String url = "https://api.weather.gov/stations/KTMB/observations/latest";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "EnvironmentalData/1.0 (test@example.com)")
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                http.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalStateException("HTTP error: " + response.statusCode());
        }

        String body = response.body();
        var test = mapper.readValue(body, NoaaResponse.class);

        System.out.println("Wetterstations ID: " + test.stationId());
        System.out.println("Temperatur: " + test.temperatureCelsius() + "°");
        System.out.println("Luftfeuchtigkeit: " + test.relativeHumidity());
        System.out.println("Datenzeitraum: " + test.timestamp());

        System.out.println("\nPretty JSON:\n" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(test));
    }
}
