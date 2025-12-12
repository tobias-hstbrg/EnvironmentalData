package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.dto.UsgsResponse;

import java.io.IOException;

public class UsgsWaterDeserializer extends JsonDeserializer<UsgsResponse>
{
    @Override
    public UsgsResponse deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        JsonNode node = jsonparser.getCodec().readTree(jsonparser);

        //timeSeries Array
        JsonNode valueNode = node.get("value");
        if(valueNode == null) {
            return new UsgsResponse(0.0, 0.0);
        }

        JsonNode timeSeriesArray = valueNode.get("timeSeries");
        if(timeSeriesArray == null || !timeSeriesArray.isArray()) {
            return new UsgsResponse(0.0, 0.0);
        }

        Double waterTemperature = null;
        Double salinity = null;

        //fetch values from timeSeries Array
        for (JsonNode timeSeries : timeSeriesArray) {
            JsonNode variable = timeSeries.get("variable");
            if (variable != null) {
                JsonNode variableCodeArray = variable.get("variableCode");
                if (variableCodeArray != null && variableCodeArray.isArray() && !variableCodeArray.isEmpty()) {
                    String code = variableCodeArray.get(0).get("value").asText();

                    // Get the latest value from the values array
                    JsonNode valuesArray = timeSeries.get("values");
                    if (valuesArray != null && valuesArray.isArray() && !valuesArray.isEmpty()) {
                        JsonNode valueArray = valuesArray.get(0).get("value");
                        if (valueArray != null && valueArray.isArray() && !valueArray.isEmpty()) {
                            // Get the last (most recent) measurement
                            JsonNode latestReading = valueArray.get(valueArray.size() - 1);
                            String valueStr = latestReading.get("value").asText();

                            if ("00010".equals(code)) {
                                waterTemperature = Double.parseDouble(valueStr);
                            } else if ("00095".equals(code)) {
                                salinity = Double.parseDouble(valueStr);
                            }
                        }
                    }
                }
            }
        }

        return new UsgsResponse(
                waterTemperature != null ? waterTemperature : 0.0,
                salinity != null ? salinity : 0.0
        );
    }
}
