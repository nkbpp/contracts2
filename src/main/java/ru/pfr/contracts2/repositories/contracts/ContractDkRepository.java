package ru.pfr.contracts2.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;


public interface ContractDkRepository extends JpaRepository<ContractDk, Long>, JpaSpecificationExecutor<ContractDk> {
}
