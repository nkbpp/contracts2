package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contractIT.ContractIT;

import java.util.List;
import java.util.Optional;

public interface ContractItRepository extends JpaRepository<ContractIT, Long> {

    Optional<ContractIT> findById(Long l);

    List<ContractIT> findAllByOrderByIdDesc();

/*    @Query(
            value = "SELECT * FROM contract " +
                    "WHERE (?1 is null or nomgk like ?1%) and ( contract.kontragent_id in " +
                    "(select id from kontragent where (?2 is null or inn like ?2%))) " +
                    "order by id desc",
            nativeQuery = true)
    public List<Contract> findByNomGKAndKontragentInnScript(String nom, String inn);*/

}
