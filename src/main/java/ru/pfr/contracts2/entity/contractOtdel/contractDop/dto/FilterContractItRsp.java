package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

import lombok.*;
import ru.pfr.contracts2.entity.annotations.valid.StatusGKValid;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterContractItRsp {

    private String poleFindByNomGK;
    private String poleFindByKontragent;
    private String dateGK;

    @StatusGKValid //todo починить
    @Pattern(regexp = "^(Действующий)|(Исполнен)|(Расторгнут)|(Без статуса)|(^$)$")
    private String poleStatusGK;

    private Integer idot;
    @Builder.Default
    private Integer sortk = 1;
    @Builder.Default
    private Integer sortd = 1;

}
