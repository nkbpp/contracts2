package ru.pfr.contracts2.entity.contracts.contractRsp.entity;


import org.springframework.data.jpa.domain.Specification;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.Kontragent;

import javax.persistence.criteria.Join;

public class ContractRspSpecification {

    public static Specification<ContractRsp> ispolnenoIs(Boolean b) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractRsp_.ISPOLNENO), b);
    }

    public static Specification<ContractRsp> ispolnenoEquals(Boolean ispolneno) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(ContractRsp_.ISPOLNENO), ispolneno);
    }

    public static Specification<ContractRsp> ispolnenoFilter(Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
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

    public static Specification<ContractRsp> nomGKFilter(String nomGK) {
        if (nomGK == null || nomGK.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }
        return nomGKLike(nomGK);
    }

    public static Specification<ContractRsp> nomGKLike(String nomGK) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(ContractRsp_.NOM_GK), "%" + nomGK + "%");
    }


    public static Specification<ContractRsp> innLike(String inn) {
        return (root, query, criteriaBuilder) -> {
            Join<ContractRsp, Kontragent> groupJoin = root.join(ContractRsp_.KONTRAGENT_RSP);
            return criteriaBuilder.like(criteriaBuilder.lower(groupJoin.get(KontragentRsp_.INN)), "%" + inn + "%");
        };
    }

    public static Specification<ContractRsp> innFilter(String inn) {
        if (inn == null || inn.equals("")) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.isTrue(criteriaBuilder.literal(true)); //всегда истинно criteriaBuilder.and()
            //criteriaBuilder.isFalse(criteriaBuilder.literal(true)); //всегда ложно criteriaBuilder.or()
        }

        return innLike(inn);
    }

    public static Specification<ContractRsp> filterContract(String nomGK, String inn, Boolean poleFindByIspolneno, Boolean poleFindByNotIspolneno) {
        return Specification.where(
                nomGKFilter(nomGK)
                        .and(ispolnenoFilter(poleFindByIspolneno, poleFindByNotIspolneno))
                        .and(innFilter(inn))
        );
    }


}
