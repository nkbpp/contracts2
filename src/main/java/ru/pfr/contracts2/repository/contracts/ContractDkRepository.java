package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;

import java.util.List;
import java.util.Optional;

public interface ContractDkRepository extends JpaRepository<ContractDk, Long>, JpaSpecificationExecutor<ContractDk> {

    Optional<ContractDk> findById(Long l);

    List<ContractDk> findAllByOrderByIdDesc();

    @Query(
            value = "SELECT * FROM contract " +
                    "WHERE (?1 is null or nomgk like ?1%) and ( contract.kontragent_id in " +
                    "(select id from kontragent where (?2 is null or inn like ?2%))) " +
                    "order by id desc",
            nativeQuery = true)
    List<ContractDk> findByNomGKAndKontragentInnScript(String nom, String inn, Pageable pageable);

    List<ContractDk> findAllByIspolneno(boolean b);
}
