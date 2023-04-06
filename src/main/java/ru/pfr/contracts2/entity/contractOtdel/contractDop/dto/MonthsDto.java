package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

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
public class MonthsDto {
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month1;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month2;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month3;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month4;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month5;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month6;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month7;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month8;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month9;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month10;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month11;
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month12;

}
