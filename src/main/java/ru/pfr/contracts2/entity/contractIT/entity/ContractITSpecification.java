package ru.pfr.contracts2.entity.contractIT.entity;

import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.contractIT.dto.FilterContractIt;
import ru.pfr.contracts2.global.ConverterDate;

import java.util.Date;

public class ContractITSpecification {

    public static Specification<ContractIT> idotFilter(Integer idot) {
        if (idot == null || idot.equals(0)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return idotEquals(idot);
    }

    public static Specification<ContractIT> idotEquals(Integer idot) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.IDZIROT), idot);
    }

    public static Specification<ContractIT> dateGKEquals(Date dateGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.DATE_GK), dateGK);
    }

    public static Specification<ContractIT> dateGKFilter(Date dateGK) {
        if (dateGK == null) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return dateGKEquals(dateGK);
    }

    public static Specification<ContractIT> statusGKEquals(String statusGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.STATUS_GK), statusGK);
    }

    public static Specification<ContractIT> statusGKFilter(String statusGK) {
        if (statusGK == null || statusGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        if (statusGK.equals("Без статуса")) {
            return statusGKisEmpty();
        }
        return statusGKEquals(statusGK);
    }

    public static Specification<ContractIT> statusGKisNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get(ContractIT_.STATUS_GK));
    }

    /**
     * Пустое или null
     */
    public static Specification<ContractIT> statusGKisEmpty() {
        return Specification.where(
                statusGKisNull()
                        .or(statusGKEquals(""))
        );
    }

    public static Specification<ContractIT> nomGKFilter(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return nomGKLike(nomGK);
    }

    public static Specification<ContractIT> nomGKLike(String nomGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractIT_.NOM_GK), "%" + nomGK + "%");
    }

    public static Specification<ContractIT> kontragentLike(String kontragent) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractIT_.KONTRAGENT), "%" + kontragent + "%");
    }

    public static Specification<ContractIT> kontragentFilter(String kontragent) {
        if (kontragent == null || kontragent.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return kontragentLike(kontragent);
    }

    public static Specification<ContractIT> filterContractIt(
            FilterContractIt filter
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
                        .and(statusGKFilter(filter.getPoleStatusGK()))
        );
    }

}
