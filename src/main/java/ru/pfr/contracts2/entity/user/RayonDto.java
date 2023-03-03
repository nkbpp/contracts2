package ru.pfr.contracts2.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RayonDto {

    private Long id;
    private String namerayon;
    private String kod;
    private String regnummru;
    private String inn;
    private String kpp;
    private String kategorija;
    //дата постановки на учет
    private String data_p_u;
    //дата снятия с учета
    private String data_s_u;
}
