package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.valid.StatusGKValid;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ContractItRspDto extends ContractDopDto {

    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKs; //действие ГК с
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKpo; //действие ГК по

    //@Pattern(regexp = "^(Действующий)|(Исполнен)|(Расторгнут)|(^$)$")
    @StatusGKValid
    private String statusGK; //Статус ГК

    private Integer idzirot; // id ответственного в зире
    private String nameot;//имя ответственного

    public ContractItRspDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double ostatoc, List<DopDocumentsDto> documents, MonthsDto months, LocalDateTime dateGKs, LocalDateTime dateGKpo, String statusGK, Integer idzirot, String nameot) {
        super(id, nomGK, kontragent, dateGK, sum, ostatoc, documents, months);
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.statusGK = statusGK;
        this.idzirot = idzirot;
        this.nameot = nameot;
    }
}
