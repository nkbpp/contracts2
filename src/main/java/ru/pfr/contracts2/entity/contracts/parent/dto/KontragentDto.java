package ru.pfr.contracts2.entity.contracts.parent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KontragentDto {

    private Long id;
    private String name;
    private String inn;
    private String nameInn;

}
