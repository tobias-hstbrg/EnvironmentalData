package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.UsgsWaterDeserializer;

@JsonDeserialize(using = UsgsWaterDeserializer.class)
public record UsgsResponse(
        double waterTemperature,
        double salinity
){}
