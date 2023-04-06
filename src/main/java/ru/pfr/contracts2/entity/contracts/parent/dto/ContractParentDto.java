package ru.pfr.contracts2.entity.contracts.parent.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateSerializerRu;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;
import ru.pfr.contracts2.entity.user.UserDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ContractParentDto {

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

    public ContractParentDto(Long id, LocalDate receipt_date, String plat_post, KontragentDto kontragent, String nomGK, LocalDate dateGK, String predmet_contract, VidObespDto vidObesp, Double sum, LocalDate date_ispolnenija_GK, Integer col_days, LocalDate raschet_date, List<NotificationDto> notifications, Boolean ispolneno, String nomerZajavkiNaVozvrat, LocalDate dateZajavkiNaVozvrat, List<MyDocumentsDto> myDocuments, UserDto user, Long daysOst, Long procent) {
        this.id = id;
        this.receipt_date = receipt_date;
        this.plat_post = plat_post;
        this.kontragent = kontragent;
        this.nomGK = nomGK;
        this.dateGK = dateGK;
        this.predmet_contract = predmet_contract;
        this.vidObesp = vidObesp;
        this.sum = sum;
        this.date_ispolnenija_GK = date_ispolnenija_GK;
        this.col_days = col_days;
        this.raschet_date = raschet_date;
        this.notifications = notifications;
        this.ispolneno = ispolneno;
        this.nomerZajavkiNaVozvrat = nomerZajavkiNaVozvrat;
        this.dateZajavkiNaVozvrat = dateZajavkiNaVozvrat;
        this.myDocuments = myDocuments;
        this.user = user;
        this.daysOst = daysOst;
        this.procent = procent;
    }

}
