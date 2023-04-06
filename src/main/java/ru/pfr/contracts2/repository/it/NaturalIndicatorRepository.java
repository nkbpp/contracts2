package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.NaturalIndicator;

import java.util.List;

public interface NaturalIndicatorRepository extends JpaRepository<NaturalIndicator, Long> {

    @Query(
            value = "SELECT * FROM naturalindicator " +
                    "WHERE contractit_id = ?1",
            nativeQuery = true)
    List<NaturalIndicator> findByContractIT(int l);

}
