package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        HttpClient http = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        String url = "https://api.weather.gov/points/25.25255556,-80.6662611";

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

        JsonNode root = mapper.readTree(body);

        int id = root.get("id").asInt();
        String title = root.get("title").asText();
        boolean completed = root.get("completed").asBoolean();

        System.out.println("ID:        " + id);
        System.out.println("Title:     " + title);
        System.out.println("Completed: " + completed);

        System.out.println("\nPretty JSON:\n" +
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
    }
}
