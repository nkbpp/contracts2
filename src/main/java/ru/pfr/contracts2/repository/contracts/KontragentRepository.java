package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.contracts2.entity.contracts.entity.Kontragent;

import java.util.List;
import java.util.Optional;


public interface KontragentRepository extends JpaRepository<Kontragent, Long> {

    Optional<Kontragent> findById(Long l);

    List<Kontragent> findAllByOrderByIdDesc();

    @Query(
            value = "SELECT * FROM kontragent " +
                    "WHERE (?1 is null or name like ?1%) " +
                    "and (?2 is null or inn like ?2%) order by id desc ",
            nativeQuery = true)
    List<Kontragent> findByNameAndInn(String name, String inn);

}
