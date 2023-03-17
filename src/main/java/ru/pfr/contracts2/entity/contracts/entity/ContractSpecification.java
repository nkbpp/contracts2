package ru.pfr.contracts2.entity.contracts.entity;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ContractSpecification {

    public static Specification<Contract> ispolnenoEquals(Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        if (poleFindByIspolneno && poleFindByNotIspolneno) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        } else if (!poleFindByIspolneno && poleFindByNotIspolneno) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("ispolneno"), false);
        } else if (poleFindByIspolneno) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("ispolneno"), true);
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(criteriaBuilder.literal(false));
    }

    public static Specification<Contract> nomGKLike(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nomGK"), "%" + nomGK + "%");
    }

    public static Specification<Contract> innLike(String inn) {
        if (inn == null || inn.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }

        return (root, query, criteriaBuilder) -> {
            Join<Contract, Kontragent> groupJoin = root.join("kontragent");
            return criteriaBuilder.like(criteriaBuilder.lower(groupJoin.get("inn")), "%" + inn + "%");
        };

    }

    public static Specification<Contract> filterContract(String nomGK, String inn, Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        return Specification.where(
                nomGKLike(nomGK)
                        .and(ispolnenoEquals(poleFindByIspolneno, poleFindByNotIspolneno))
                        .and(innLike(inn))
        );
    }

}
