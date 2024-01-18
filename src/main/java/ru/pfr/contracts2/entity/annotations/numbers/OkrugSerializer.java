package ru.pfr.contracts2.entity.annotations.numbers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class OkrugSerializer extends StdSerializer<Double> {

    protected OkrugSerializer(Class<Double> t) {
        super(t);
    }

    protected OkrugSerializer() {
        this(null);
    }

    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        jsonGenerator.writeString(aDouble != null ?
                df.format(aDouble).replace(",", ".") : "");
    }

}
