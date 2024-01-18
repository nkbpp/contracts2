package ru.pfr.contracts2.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;


public interface KontragentRspRepository extends JpaRepository<KontragentRsp, Long>, JpaSpecificationExecutor<KontragentRsp> {

}
