package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity.ContractRsp;

import java.util.Optional;

public interface ContractRspRepository extends JpaRepository<ContractRsp, Long>, JpaSpecificationExecutor<ContractRsp> {

    Optional<ContractRsp> findById(Long l);

}
