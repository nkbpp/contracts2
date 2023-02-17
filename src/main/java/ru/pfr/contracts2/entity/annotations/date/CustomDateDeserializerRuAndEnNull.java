package ru.pfr.contracts2.entity.annotations.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializerRuAndEnNull extends StdDeserializer<Date> {

    private static final DateFormat formatterEn = new SimpleDateFormat("yyyy-MM-dd");

    private static final DateFormat formatterRu = new SimpleDateFormat("dd.MM.yyyy");

    public CustomDateDeserializerRuAndEnNull() {
        this(null);
    }

    public CustomDateDeserializerRuAndEnNull(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return formatterEn.parse(date);
        } catch (ParseException e) {
            try {
                return formatterRu.parse(date);
            } catch (ParseException e2) {
                return null;
            }
        }
    }

}
