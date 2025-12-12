package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UsgsResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WaterDataService {
    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // The NOAA API does redirects so in order to get an HTTP 200 Code, allowing redirects is mandatory.
    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private static final String url = "https://waterservices.usgs.gov/nwis/iv/?format=json&sites=251457080395802&parameterCd=00010,00095&period=PT2H";

    public String fetchRawJSON(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "EnvironmentalData/1.0 (test@example.com)")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        final HttpResponse<String> response;
        try {
            response = http.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("USGS request failed", e);
        }

        if(response.statusCode() != 200) {
            String body = response.body();
            String preview = body != null ? body.substring(0, Math.min(200, body.length())) : "";
            throw new IllegalStateException(
                    "USGS returned HTTP " + response.statusCode() +
                            " body preview: " + preview);
        }
        return response.body();
    }

    public UsgsResponse fetchWaterData() {
        String jsonResponse = fetchRawJSON();

        try {
            return mapper.readValue(jsonResponse, UsgsResponse.class);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse USGS response", e);
        }
    }

    // Alternative: Fetch and return the UsgsResponse directly
    public UsgsResponse getLatestWaterData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "EnvironmentalData/1.0 (test@example.com)")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        final HttpResponse<String> response;
        try {
            response = http.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("USGS request failed", e);
        }

        if(response.statusCode() != 200) {
            String body = response.body();
            String preview = body != null ? body.substring(0, Math.min(200, body.length())) : "";
            throw new IllegalStateException(
                    "USGS returned HTTP " + response.statusCode() +
                            " body preview: " + preview);
        }

        try {
            return mapper.readValue(response.body(), UsgsResponse.class);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse USGS response", e);
        }
    }
}