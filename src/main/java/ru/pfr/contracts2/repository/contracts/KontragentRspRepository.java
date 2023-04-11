package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;

import java.util.List;
import java.util.Optional;


public interface KontragentRspRepository extends JpaRepository<KontragentRsp, Long> {

    Optional<KontragentRsp> findById(Long l);

    @Query(
            value = "SELECT * FROM kontragentrsp " +
                    "WHERE (?1 is null or name like ?1%) " +
                    "and (?2 is null or inn like ?2%) order by id desc ",
            nativeQuery = true)
    List<KontragentRsp> findByNameAndInn(String name, String inn);

}
