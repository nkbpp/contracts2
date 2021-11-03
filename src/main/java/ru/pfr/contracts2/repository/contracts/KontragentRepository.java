package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.Kontragent;

import java.util.Optional;


public interface KontragentRepository extends JpaRepository<Kontragent, Long> {

    Optional<Kontragent> findById(Long l);

}
