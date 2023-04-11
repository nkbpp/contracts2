package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.VidObespRsp;

import java.util.List;
import java.util.Optional;


public interface VidObespRspRepository extends JpaRepository<VidObespRsp, Long> {

    List<VidObespRsp> findAllByOrderByIdDesc();

    Optional<VidObespRsp> findById(Long l);

}
