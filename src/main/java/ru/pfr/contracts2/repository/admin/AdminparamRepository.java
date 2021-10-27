package ru.pfr.contracts2.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.admin.Adminparam;

import java.util.Optional;

public interface AdminparamRepository extends JpaRepository<Adminparam, Long> {
    public Optional<Adminparam> findById(Long l);
}
