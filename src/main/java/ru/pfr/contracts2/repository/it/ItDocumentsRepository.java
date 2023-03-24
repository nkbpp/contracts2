package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contractIT.entity.DopDocuments;

import java.util.List;

public interface ItDocumentsRepository extends JpaRepository<DopDocuments, Long> {

    @Query(
            value = "SELECT * FROM it_documents WHERE contractit_id = ?1",
            nativeQuery = true)
    List<DopDocuments> myFindByContractIT(int l);

}
