package ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity;

import org.springframework.data.jpa.domain.Specification;

public class ContractAxoSpecification {

    public static Specification<ContractAxo> roleEquals(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractAxo_.ROLE), role);
    }

    public static Specification<ContractAxo> nomGKLike(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractAxo_.NOM_GK), "%" + nomGK + "%");
    }

    public static Specification<ContractAxo> kontragentLike(String kontragent) {
        if (kontragent == null || kontragent.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractAxo_.KONTRAGENT), "%" + kontragent + "%");
    }

    public static Specification<ContractAxo> filterContractAXO(
            String poleFindByNomGK,
            String poleFindByKontragent
    ) {
        return Specification.where(
                (kontragentLike(poleFindByKontragent))
                        .and(nomGKLike(poleFindByNomGK))
        );
    }

}
