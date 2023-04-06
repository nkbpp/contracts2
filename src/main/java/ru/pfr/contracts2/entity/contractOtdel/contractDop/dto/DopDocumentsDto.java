package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DopDocumentsDto {

    private Long id;
    private String nameFile;

}
