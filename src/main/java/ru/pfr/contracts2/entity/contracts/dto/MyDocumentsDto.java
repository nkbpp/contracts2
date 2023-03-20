package ru.pfr.contracts2.entity.contracts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyDocumentsDto {

    private Long id;
    private String nameFile;

}
