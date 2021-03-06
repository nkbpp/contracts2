package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.Notification;


import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public Optional<Notification> findById(Long l);

}
