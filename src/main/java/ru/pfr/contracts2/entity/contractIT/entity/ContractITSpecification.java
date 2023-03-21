package ru.pfr.contracts2.entity.contractIT.entity;

import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.contractIT.dto.FilterContractIt;
import ru.pfr.contracts2.global.ConverterDate;

import java.util.Date;

public class ContractITSpecification {

    public static Specification<ContractIT> idotEquals(Integer idot) {
        if (idot == null || idot.equals(0)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.IDZIROT), idot);
    }

    public static Specification<ContractIT> dateGKEquals(Date dateGK) {
        if (dateGK == null)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.DATE_GK), dateGK);
    }

    public static Specification<ContractIT> statusGKEquals(String statusGK) {
        if (statusGK == null || statusGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.STATUS_GK), statusGK);
    }

    public static Specification<ContractIT> roleEquals(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractIT_.ROLE), role);
    }

    public static Specification<ContractIT> nomGKLike(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractIT_.NOM_GK), "%" + nomGK + "%");
    }

    public static Specification<ContractIT> kontragentLike(String kontragent) {
        if (kontragent == null || kontragent.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractIT_.KONTRAGENT), "%" + kontragent + "%");
    }

    public static Specification<ContractIT> filterContractIt(
            FilterContractIt filter//, String role
    ) {
        final Date dateGK2;
        if (filter.dateGK() != null && !filter.dateGK().equals("")) {
            dateGK2 = ConverterDate.stringToDate(filter.dateGK().trim());
        } else {
            dateGK2 = null;
        }
        /*return Specification.where(
                roleEquals(role)
                        .and(kontragentLike(filter.poleFindByKontragent()))
                        .and(nomGKLike(filter.poleFindByNomGK()))
                        .and(idotEquals(filter.idot()))
                        .and(dateGKEquals(dateGK2))
                        .and(statusGKEquals(filter.poleStatusGK()))
        );*/

        return Specification.where(
                (kontragentLike(filter.poleFindByKontragent()))
                        .and(nomGKLike(filter.poleFindByNomGK()))
                        .and(idotEquals(filter.idot()))
                        .and(dateGKEquals(dateGK2))
                        .and(statusGKEquals(filter.poleStatusGK()))
        );
    }

}
