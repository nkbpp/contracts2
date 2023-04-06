package ru.pfr.contracts2.entity.contracts.contractDK.entity;


import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.contracts.parent.entity.Kontragent;
import ru.pfr.contracts2.entity.contracts.parent.entity.Kontragent_;

import javax.persistence.criteria.Join;

public class ContractDkSpecification {

    public static Specification<ContractDk> ispolnenoIs(Boolean b) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractDk_.ISPOLNENO), b);
    }

    public static Specification<ContractDk> ispolnenoEquals(Boolean ispolneno) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractDk_.ISPOLNENO), ispolneno);
    }

    public static Specification<ContractDk> ispolnenoFilter(Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        if (poleFindByIspolneno && poleFindByNotIspolneno) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        } else if (!poleFindByIspolneno && poleFindByNotIspolneno) {
            return ispolnenoEquals(false);
        } else if (poleFindByIspolneno) {
            return ispolnenoEquals(true);
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(criteriaBuilder.literal(false));
    }

    public static Specification<ContractDk> nomGKFilter(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return nomGKLike(nomGK);
    }

    public static Specification<ContractDk> nomGKLike(String nomGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractDk_.NOM_GK), "%" + nomGK + "%");
    }


    public static Specification<ContractDk> innLike(String inn) {
        return (root, query, criteriaBuilder) -> {
            Join<ContractDk, Kontragent> groupJoin = root.join(ContractDk_.kontragent);
            return criteriaBuilder.like(criteriaBuilder.lower(groupJoin.get(Kontragent_.INN)), "%" + inn + "%");
        };

    }

    public static Specification<ContractDk> innFilter(String inn) {
        if (inn == null || inn.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }

        return innLike(inn);
    }

    public static Specification<ContractDk> filterContract(String nomGK, String inn, Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        return Specification.where(
                nomGKFilter(nomGK)
                        .and(ispolnenoFilter(poleFindByIspolneno, poleFindByNotIspolneno))
                        .and(innFilter(inn))
        );
    }


}
