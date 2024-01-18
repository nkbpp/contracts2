package ru.pfr.contracts2.repositories.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;

public interface ContractItRepository extends JpaRepository<ContractIT, Long>, JpaSpecificationExecutor<ContractIT> {
}
