package ru.pfr.contracts2.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.admin.Adminparam;

public interface AdminparamRepository extends JpaRepository<Adminparam, Long> {
}
