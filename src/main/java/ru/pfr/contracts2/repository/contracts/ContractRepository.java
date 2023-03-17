package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contracts.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

    Optional<Contract> findById(Long l);

    List<Contract> findAllByOrderByIdDesc();

    @Query(
            value = "SELECT * FROM contract " +
                    "WHERE (?1 is null or nomgk like ?1%) and ( contract.kontragent_id in " +
                    "(select id from kontragent where (?2 is null or inn like ?2%))) " +
                    "order by id desc",
            nativeQuery = true)
    List<Contract> findByNomGKAndKontragentInnScript(String nom, String inn, Pageable pageable);

    List<Contract> findAllByIspolneno(boolean b);
}
