package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.ContractAxo;

import java.util.Optional;

public interface ContractAxoRepository extends JpaRepository<ContractAxo, Long>, JpaSpecificationExecutor<ContractAxo> {

    Optional<ContractAxo> findById(Long l);

}
