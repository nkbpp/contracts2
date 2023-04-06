package ru.pfr.contracts2.entity.contracts.entity;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;

public class ContractSpecification {


    public static Specification<Contract> ispolnenoIs(Boolean b) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Contract_.ISPOLNENO), b);
    }

    public static Specification<Contract> ispolnenoEquals(Boolean ispolneno) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Contract_.ISPOLNENO), ispolneno);
    }

    public static Specification<Contract> ispolnenoFilter(Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
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

    public static Specification<Contract> nomGKFilter(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return nomGKLike(nomGK);
    }

    public static Specification<Contract> nomGKLike(String nomGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Contract_.NOM_GK), "%" + nomGK + "%");
    }


    public static Specification<Contract> innLike(String inn) {
        return (root, query, criteriaBuilder) -> {
            Join<Contract, Kontragent> groupJoin = root.join(Contract_.kontragent);
            return criteriaBuilder.like(criteriaBuilder.lower(groupJoin.get(Kontragent_.INN)), "%" + inn + "%");
        };

    }

    public static Specification<Contract> innFilter(String inn) {
        if (inn == null || inn.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }

        return innLike(inn);
    }

    public static Specification<Contract> filterContract(String nomGK, String inn, Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        return Specification.where(
                nomGKFilter(nomGK)
                        .and(ispolnenoFilter(poleFindByIspolneno, poleFindByNotIspolneno))
                        .and(innFilter(inn))
        );
    }


}
