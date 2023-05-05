package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.parent.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
