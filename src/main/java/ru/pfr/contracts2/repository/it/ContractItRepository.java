package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contractIT.ContractIT;

import java.util.List;
import java.util.Optional;

public interface ContractItRepository extends JpaRepository<ContractIT, Long> {

    Optional<ContractIT> findById(Long l);

    List<ContractIT> findAllByOrderByIdDesc();

    List<ContractIT> findAllByRoleOrderByIdDesc(String role);

    @Query(
            value = "SELECT * FROM contractit " +
                    "WHERE (?1 is null or nomgk like %?1%)" +
                    "and (?2 is null or kontragent like %?2%) " +
                    "and (role = ?3) " +
                    "order by id desc",
            nativeQuery = true)
    List<ContractIT> findByNomGKAndKontragent(String nomgk, String komtragent, String role);

    @Query(
            value = "SELECT * FROM contractit " +
                    "WHERE role = ?1 " +
                    "order by id desc",
            nativeQuery = true)
    List<ContractIT> findAllByRole(String role);

    @Query(
            value = "SELECT * FROM contractit " +
                    "WHERE id in ?1 desc",
            nativeQuery = true)
    List<ContractIT> findByIds(List<Integer> list);

}
