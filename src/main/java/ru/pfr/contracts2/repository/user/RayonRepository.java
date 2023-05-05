package ru.pfr.contracts2.repository.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pfr.contracts2.entity.user.Rayon;

public interface RayonRepository extends JpaRepository<Rayon, Long>, JpaSpecificationExecutor<Rayon> {

}
