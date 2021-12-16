package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.VidObesp;


import java.util.List;
import java.util.Optional;


public interface VidObespRepository extends JpaRepository<VidObesp, Long> {

    List<VidObesp> findAllByOrderByIdDesc();

    Optional<VidObesp> findById(Long l);

}
