package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contractIT.entity.ItDocuments;

import java.util.List;

public interface ItDocumentsRepository extends JpaRepository<ItDocuments, Long> {

    @Query(
            value = "SELECT * FROM it_documents " +
                    "WHERE contractit_id = ?1",
            nativeQuery = true)
    public List<ItDocuments> findByContractIT(int l);

}
