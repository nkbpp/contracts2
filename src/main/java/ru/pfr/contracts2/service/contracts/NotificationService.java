package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.entity.contracts.Notification;
import ru.pfr.contracts2.repository.contracts.NotificationRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

    final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
