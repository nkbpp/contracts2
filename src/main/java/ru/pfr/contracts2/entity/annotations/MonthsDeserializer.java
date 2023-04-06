package ru.pfr.contracts2.entity.annotations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.MonthsDto;

import java.io.IOException;

public class MonthsDeserializer extends StdDeserializer<MonthsDto> {

    public MonthsDeserializer() {
        this(null);
    }

    public MonthsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MonthsDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //return jsonParser.getValueAsDouble();
        return jsonParser.readValueAs(MonthsDto.class);
        //return new MonthsDto();
    }
}
