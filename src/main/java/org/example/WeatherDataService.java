package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.NoaaResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WeatherDataService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // The NOAA API does redirects so in order to get an HTTP 200 Code, allowing redirects is mandatory.
    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    // API Endpoint
    private static final String url = "https://api.weather.gov/stations/KTMB/observations/latest";

    private String fetchRawJson() {
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
            throw new IllegalStateException("NOAA request failed", e);
        }

        if(response.statusCode() != 200) {
            String body = response.body();
            String preview = body != null ? body.substring(0, Math.min(200, body.length())) : "";
            throw new IllegalStateException(
                    "NOAA returned HTTP " + response.statusCode() +
                            " body preview: " + preview);
        }
        return response.body();
    }

    private NoaaResponse parse(String json) {

        try {
            return mapper.readValue(json, NoaaResponse.class);
        }catch(JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON payload",e);
        }
    }

    public NoaaResponse getData(){

        final String apiResult = fetchRawJson();
        return parse(apiResult);
    }
}
