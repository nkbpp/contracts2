package ru.pfr.contracts2.services.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.parent.entity.Notification;
import ru.pfr.contracts2.repositories.contracts.NotificationRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

}
