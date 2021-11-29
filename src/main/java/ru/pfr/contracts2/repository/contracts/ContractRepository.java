package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contracts.Contract;


import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    public Optional<Contract> findById(Long l);

    public List<Contract> findAllByOrderByIdDesc();

    public List<Contract> findByNomGKAndKontragentInn(String nom, String inn);


    @Query(
            value = "SELECT * FROM contract " +
                    "WHERE (?1 is null or nomgk like ?1%) and ( contract.kontragent_id in " +
                    "(select id from kontragent where (?2 is null or inn like ?2%)))",
            nativeQuery = true)
    public List<Contract> findByNomGKAndKontragentInnScript(String nom, String inn);

    List<Contract> findAllByIspolneno(boolean b);
}
