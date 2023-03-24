package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.numbers.CustomDoubleDeserializerNotNull;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
public class ContractMonthDto {

    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month1;

    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month2;

    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month3;

    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month4;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month5;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month6;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month7;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month8;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month9;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month10;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month11;
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double month12;

    public ContractMonthDto(Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12) {
        this.month1 = month1;
        this.month2 = month2;
        this.month3 = month3;
        this.month4 = month4;
        this.month5 = month5;
        this.month6 = month6;
        this.month7 = month7;
        this.month8 = month8;
        this.month9 = month9;
        this.month10 = month10;
        this.month11 = month11;
        this.month12 = month12;
    }
}
