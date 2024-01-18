package ru.pfr.contracts2.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.Kontragent;

public interface KontragentRepository extends JpaRepository<Kontragent, Long>, JpaSpecificationExecutor<Kontragent> {

}
