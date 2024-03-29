package ru.pfr.contracts2.entity.annotations.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Преобразует строку даты (в русском и американском формате) в LocalDateTime
 * при ошибке выбрасывает RuntimeException
 */
public class CustomLocalDateTimeDeserializerRuAndEn extends StdDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatterEn
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter formatterRu
            = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public CustomLocalDateTimeDeserializerRuAndEn() {
        this(null);
    }

    public CustomLocalDateTimeDeserializerRuAndEn(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String date = jsonParser.getText() + " 00:00:00";
        try {
            return LocalDateTime.parse(date, formatterEn);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(date, formatterRu);
            } catch (DateTimeParseException e2) {
                throw new RuntimeException("LocalDate parse exception " + date);
            }
        }
    }

}
