package ru.pfr.contracts2.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.parent.entity.MyDocuments;

public interface MyDocumentsRepository extends JpaRepository<MyDocuments, Long> {

}
