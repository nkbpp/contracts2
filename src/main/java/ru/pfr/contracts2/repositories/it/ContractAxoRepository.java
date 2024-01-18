package ru.pfr.contracts2.repositories.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.ContractAxo;

public interface ContractAxoRepository extends JpaRepository<ContractAxo, Long>, JpaSpecificationExecutor<ContractAxo> {

}
