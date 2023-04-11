package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;

import java.util.Optional;

public interface ContractDkRepository extends JpaRepository<ContractDk, Long>, JpaSpecificationExecutor<ContractDk> {

    Optional<ContractDk> findById(Long l);

}
