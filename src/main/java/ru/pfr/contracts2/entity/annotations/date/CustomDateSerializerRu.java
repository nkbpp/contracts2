package ru.pfr.contracts2.entity.annotations.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializerRu extends StdSerializer<Date> {

    private static final DateFormat formatterRu
            = new SimpleDateFormat("dd.MM.yyyy");

    protected CustomDateSerializerRu(Class<Date> t) {
        super(t);
    }

    protected CustomDateSerializerRu() {
        this(null);
    }

    @Override
    public void serialize(Date localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatterRu.format(localDate));
    }
}
