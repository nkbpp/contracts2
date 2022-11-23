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
public class ContractITDto {

    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGK; //дата ГК
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGKs; //действие ГК с
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGKpo; //действие ГК по
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
    private String statusGK; //Статус ГК
    private Integer idzirot; // id ответственного в зире
    private String nameot;//имя ответственного
    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток
    private List<ItDocumentsDto> documents;





/*    public String getOstatoc() {
        return MyNumbers.okrug(sum - (month1+month2+month3+month4+month5+month6+month7+month8+month9+month10+month11+month12));
    }

    public Double getOstatocDouble() {
        return sum - (month1+month2+month3+month4+month5+month6+month7+month8+month9+month10+month11+month12);
    }*/






/*    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NaturalIndicator> naturalIndicators = new ArrayList<>();


    public ContractITDto(String nomGK, String kontragent, String statusGK,
                         Date dateGK, Date dateGKs, Date dateGKpo, Double sum, Double month1, Double month2,
                         Double month3, Double month4, Double month5, Double month6, Double month7,
                         Double month8, Double month9, Double month10, Double month11, Double month12,
                         Double sumNaturalIndicators, List<NaturalIndicator> naturalIndicators,
                         String documentu, List<ItDocuments> itDocuments, User user, String role,
                         Integer idzirot, String nameot) {
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.statusGK = statusGK;
        this.dateGK = dateGK;
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.idzirot = idzirot;
        this.nameot = nameot;
        this.sum = sum;
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
        this.documentu = documentu;
        this.user = user;

        this.sumNaturalIndicators = sumNaturalIndicators;
        setAllNaturalIndicators(naturalIndicators);

        setAllDocuments(itDocuments);

        this.role = role;

        date_create = new Date();
    }

    public void addDocuments(ItDocuments myDoc) {
        this.itDocuments.add(myDoc);
        myDoc.setContractIT(this);
    }

    public void setAllDocuments(List<ItDocuments> myDocs) {
*//*        while (itDocuments.size()>0){
            removeDocuments(itDocuments.get(0));
        }*//*
        for (ItDocuments d :
                myDocs) {
            addDocuments(d);
        }
    }

    public void removeDocuments(ItDocuments myDoc) {
        this.itDocuments.remove(myDoc);
        myDoc.setContractIT(null);
    }


    //addNaturalIndicators
    public void addNaturalIndicators(NaturalIndicator naturalIndicator) {
        this.naturalIndicators.add(naturalIndicator);
        naturalIndicator.setContractIT(this);
    }

    public void setAllNaturalIndicators(List<NaturalIndicator> naturalIndicators) {
        while (this.naturalIndicators.size()>0){ //удаляем все что было
            removeNaturalIndicators(this.naturalIndicators.get(0));
        }
        for (NaturalIndicator d :
                naturalIndicators) {
            addNaturalIndicators(d);
        }
    }

    public void removeNaturalIndicators(NaturalIndicator naturalIndicator) {
        this.naturalIndicators.remove(naturalIndicator);
        naturalIndicator.setContractIT(null);
    }

    public String getSumNaturalIndicatorsStr() {
        return MyNumbers.okrug(sumNaturalIndicators);
    }

    public String getNaturalIndicatorsStr() {
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
