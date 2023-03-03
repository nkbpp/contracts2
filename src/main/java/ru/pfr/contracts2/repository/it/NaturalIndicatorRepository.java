package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contractIT.entity.NaturalIndicator;

import java.util.List;

public interface NaturalIndicatorRepository extends JpaRepository<NaturalIndicator, Long> {

    @Query(
            value = "SELECT * FROM naturalindicator " +
                    "WHERE contractit_id = ?1",
            nativeQuery = true)
    public List<NaturalIndicator> findByContractIT(int l);

}
