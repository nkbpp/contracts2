package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.entity.Notification;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findById(Long l);

}
