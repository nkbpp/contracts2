package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.Contract;


import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    public Optional<Contract> findById(Long l);

}
