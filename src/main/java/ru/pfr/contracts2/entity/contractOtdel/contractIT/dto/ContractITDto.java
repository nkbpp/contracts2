package ru.pfr.contracts2.entity.contractOtdel.contractIT.dto;


import lombok.*;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractItRspDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.DopDocumentsDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.MonthsDto;


import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ContractITDto extends ContractItRspDto {

    @PositiveOrZero
    @Max(value = 31)
    private Integer dayNotification; //когда присылать уведомления

    @Builder
    public ContractITDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double ostatoc, List<DopDocumentsDto> documents, MonthsDto months, LocalDateTime dateGKs, LocalDateTime dateGKpo, String statusGK, Integer idzirot, String nameot, Integer dayNotification) {
        super(id, nomGK, kontragent, dateGK, sum, ostatoc, documents, months, dateGKs, dateGKpo, statusGK, idzirot, nameot);
        this.dayNotification = dayNotification;
    }

}
