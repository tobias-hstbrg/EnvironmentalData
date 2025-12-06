package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.dto.NoaaResponse;

import java.io.IOException;

public class NoaaWeatherDeserializer extends JsonDeserializer<NoaaResponse>
{
    @Override
    public NoaaResponse deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        JsonNode node = jsonparser.getCodec().readTree(jsonparser);
        JsonNode properties = node.get("properties");

        String stationId = extractStationId(properties);

        Double temperatureCelsius = extractValue(properties, "temperature");

        Double relativeHumidity = extractValue(properties, "relativeHumidity");

        String timestamp = getTextValue(properties, "timestamp");

        return new NoaaResponse(stationId, temperatureCelsius, relativeHumidity, timestamp);
    }

    private Double extractValue(JsonNode properties, String fieldName) {
        JsonNode fieldNode = properties.get(fieldName);
        if (fieldNode != null && fieldNode.has("value") && !fieldNode.get("value").isNull()) {
            return fieldNode.get("value").asDouble();
        }
        return null;
    }

    private String extractStationId(JsonNode properties) {
        JsonNode stationNode = properties.get("station");
        if (stationNode != null && !stationNode.isNull()) {
            String fullUrl = stationNode.asText();
            // Extract station ID from URL like "https://api.weather.gov/stations/KTMB"
            return fullUrl.substring(fullUrl.lastIndexOf('/') + 1);
        }
        return null;
    }

    private String getTextValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : null;
    }
}
