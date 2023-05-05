package ru.pfr.contracts2.entity.log.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParamLog {

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateBefore;
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateAfter;
    private String login;
    private String type;
    private String text;

}
