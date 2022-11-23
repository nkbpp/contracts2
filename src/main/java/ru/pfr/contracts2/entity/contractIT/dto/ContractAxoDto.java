package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.date.CustomDateDeserializerRuAndEn;
import ru.pfr.contracts2.entity.annotations.date.CustomDateSerializerRu;
import ru.pfr.contracts2.entity.annotations.okrug.OkrugSerializer;

import java.util.Date;
import java.util.List;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractAxoDto {

    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGK; //дата ГК

    @JsonSerialize(using = OkrugSerializer.class)
    private Double sum; //сумма

    @JsonSerialize(using = OkrugSerializer.class)
    private Double month1;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month2;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month3;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month4;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month5;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month6;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month7;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month8;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month9;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month10;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month11;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month12;

    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток

    @JsonSerialize(using = OkrugSerializer.class)
    private Double factRashodMonth; //Средний расход в месяц фактический

    @JsonSerialize(using = OkrugSerializer.class)
    private Double ojidRashodMonth; //Средний расход в месяц ожидаемый

    private String documentu;

    private List<ItDocumentsDto> documents;
    private Double sumNaturalIndicators;
    private List<NaturalIndicatorDto> naturalIndicators;




/*    public String getNaturalIndicatorsStr() {
        String s = "";

        for (int i = 0; i < naturalIndicators.size(); i++) {
            s += (naturalIndicators.get(i).getSumOk() + ((i+1)!=naturalIndicators.size()?";":""));
        }

        return s;
    }

    public String getNaturalIndicatorsById(int i) {
        String s = "";

        try{
            s = naturalIndicators.get(i).getSumOk();
        }catch (Exception e){
            s=null;
        }

        return s;
    }

    public String getOstatocNaturalIndicator() {
        return MyNumbers.okrug(sumNaturalIndicators - naturalIndicators.stream()
                .mapToDouble(value -> value.getSum())
                .reduce((x,y) -> x + y).orElse(0));
    }

    public String getOjidRashodMonth() {
        return MyNumbers.okrug(sumNaturalIndicators / naturalIndicators.size());
    }

    public String getFactRashodMonth() {
        return MyNumbers.okrug(naturalIndicators.stream()
                        .mapToDouble(value -> value.getSum())
                        .sum() / naturalIndicators.size()
                );
    }*/

}
