package ru.pfr.contracts2.entity.contractIT.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterContractIt {

    private String poleFindByNomGK;
    private String poleFindByKontragent;
    private String dateGK;
    private String poleStatusGK;
    private Integer idot;
    @Builder.Default
    private Integer sortk = 1;
    @Builder.Default
    private Integer sortd = 1;

}
