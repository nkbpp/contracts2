package ru.pfr.contracts2.entity.contractOtdel.contractAxo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.numbers.CustomDoubleDeserializerNotNull;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NaturalIndicatorDto {

    private Long id;

    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double sum;

}
