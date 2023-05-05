package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.NaturalIndicator;

public interface NaturalIndicatorRepository extends JpaRepository<NaturalIndicator, Long>, JpaSpecificationExecutor<NaturalIndicator> {

}
