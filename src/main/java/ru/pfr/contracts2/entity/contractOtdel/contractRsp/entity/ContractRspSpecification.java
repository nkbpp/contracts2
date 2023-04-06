package ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity;

import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.FilterContractItRsp;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.global.ConverterDate;

import java.util.Date;

public class ContractRspSpecification {

    public static Specification<ContractRsp> idotFilter(Integer idot) {
        if (idot == null || idot.equals(0)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return idotEquals(idot);
    }

    public static Specification<ContractRsp> idotEquals(Integer idot) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractRsp_.IDZIROT), idot);
    }

    public static Specification<ContractRsp> dateGKEquals(Date dateGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractRsp_.DATE_GK), dateGK);
    }

    public static Specification<ContractRsp> dateGKFilter(Date dateGK) {
        if (dateGK == null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return dateGKEquals(dateGK);
    }

    public static Specification<ContractRsp> statusGKEquals(StatusGk statusGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractRsp_.STATUS_GK), statusGK);
    }

    public static Specification<ContractRsp> statusGKFilter(StatusGk statusGK) {
        if (statusGK == null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        if (statusGK.equals(StatusGk.NOSTATUS)) {
            return statusGKisEmpty();
        }
        return statusGKEquals(statusGK);
    }

    public static Specification<ContractRsp> statusGKisNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get(ContractRsp_.STATUS_GK));
    }

    /**
     * Пустое или null
     */
    public static Specification<ContractRsp> statusGKisEmpty() {
        return Specification.where(
                statusGKisNull()
                        .or(statusGKEquals(StatusGk.NOSTATUS))
        );
    }

    public static Specification<ContractRsp> nomGKFilter(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return nomGKLike(nomGK);
    }

    public static Specification<ContractRsp> nomGKLike(String nomGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractRsp_.NOM_GK), "%" + nomGK + "%");
    }

    public static Specification<ContractRsp> kontragentLike(String kontragent) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractRsp_.KONTRAGENT), "%" + kontragent + "%");
    }

    public static Specification<ContractRsp> kontragentFilter(String kontragent) {
        if (kontragent == null || kontragent.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return kontragentLike(kontragent);
    }

    public static Specification<ContractRsp> filterContract(
            FilterContractItRsp filter
    ) {
        final Date dateGK2;
        if (filter.getDateGK() != null && !filter.getDateGK().equals("")) {
            dateGK2 = ConverterDate.stringToDate(filter.getDateGK().trim());
        } else {
            dateGK2 = null;
        }

        return Specification.where(
                (kontragentFilter(filter.getPoleFindByKontragent()))
                        .and(nomGKFilter(filter.getPoleFindByNomGK()))
                        .and(idotFilter(filter.getIdot()))
                        .and(dateGKFilter(dateGK2))
                        .and(statusGKFilter(
                                        (filter.getPoleStatusGK() == null ||
                                                filter.getPoleStatusGK().equals("")) ? null :
                                                StatusGk.customValueOf(filter.getPoleStatusGK())
                                )
                        )
        );
    }

}
