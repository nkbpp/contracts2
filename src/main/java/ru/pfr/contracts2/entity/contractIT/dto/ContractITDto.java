package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;


import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ContractITDto extends ContractDopDto {

    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKs; //действие ГК с
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKpo; //действие ГК по
    private String statusGK; //Статус ГК
    private Integer idzirot; // id ответственного в зире
    private String nameot;//имя ответственного

    @Builder
    public ContractITDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12, Double ostatoc, List<ItDocumentsDto> documents, LocalDateTime dateGKs, LocalDateTime dateGKpo, String statusGK, Integer idzirot, String nameot) {
        super(id, nomGK, kontragent, dateGK, sum, month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12, ostatoc, documents);
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.statusGK = statusGK;
        this.idzirot = idzirot;
        this.nameot = nameot;
    }
}
