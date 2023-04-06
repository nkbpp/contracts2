package ru.pfr.contracts2.repository.budgetClassification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.BudgetClassification;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetClassificationRepository extends JpaRepository<BudgetClassification, Long>, JpaSpecificationExecutor<BudgetClassification> {

    Optional<BudgetClassification> findById(Long id);

    List<BudgetClassification> findByRole(String role);

    void deleteById(Long id);

}
