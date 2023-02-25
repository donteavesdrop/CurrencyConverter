package ru.zakharova.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DoubleDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        String from_parser = parser.getText();
        if (from_parser.contains(","))
        {
            from_parser = from_parser.replace(",", ".");
        }
        return Double.valueOf(from_parser);
    }
}
