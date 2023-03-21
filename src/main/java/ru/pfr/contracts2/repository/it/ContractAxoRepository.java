package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;

import java.util.Optional;

public interface ContractAxoRepository extends JpaRepository<ContractAxo, Long>, JpaSpecificationExecutor<ContractAxo> {

    Optional<ContractAxo> findById(Long l);

}
