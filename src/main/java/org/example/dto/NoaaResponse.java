package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.NoaaWeatherDeserializer;

@JsonDeserialize(using = NoaaWeatherDeserializer.class)
public record NoaaResponse(
        String stationId,
        Double temperatureCelsius,
        Double relativeHumidity,
        String timestamp
) {}
