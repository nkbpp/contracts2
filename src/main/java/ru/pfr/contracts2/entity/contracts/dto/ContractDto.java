package ru.pfr.contracts2.entity.contracts.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateSerializerRu;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;
import ru.pfr.contracts2.entity.user.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ContractDto {

    private Long id;
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate receipt_date; //дата поступления
    private String plat_post; //Платежное поручение
    private KontragentDto kontragent;
    private String nomGK; //номер ГК
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dateGK; //дата ГК
    private String predmet_contract; //краткое содержание предмета контракта
    private VidObespDto vidObesp; //вид обеспечения
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sum; //сумма
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate date_ispolnenija_GK; //дата исполнения ГК
    private Integer col_days; //Условия возврата ГК (количество дней от исполнения)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate raschet_date; //Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)
    @Builder.Default
    private List<NotificationDto> notifications = new ArrayList<>();//кого оповестить
    private Boolean ispolneno; //Отметка об исполнении
    private String nomerZajavkiNaVozvrat; //Номер заявки на возврат
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dateZajavkiNaVozvrat; //Номер заявки на возврат
    @Builder.Default
    private List<MyDocumentsDto> myDocuments = new ArrayList<>();
    private UserDto user; //кто создал контракт
    private Long daysOst;
    private Long procent;

}
