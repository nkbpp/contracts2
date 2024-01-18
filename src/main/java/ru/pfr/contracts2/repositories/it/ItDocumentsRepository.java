package ru.pfr.contracts2.repositories.it;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;

public interface ItDocumentsRepository extends JpaRepository<DopDocuments, Long> {

}
