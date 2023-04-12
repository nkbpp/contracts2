package ru.pfr.contracts2.entity.annotations.numbers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomDoubleDeserializerNotNull extends StdDeserializer<Double> {

    public CustomDoubleDeserializerNotNull() {
        this(null);
    }

    public CustomDoubleDeserializerNotNull(Class<?> vc) {
        super(vc);
    }

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return jsonParser.getValueAsDouble();
    }

}
