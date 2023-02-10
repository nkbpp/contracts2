package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.Notification;
import ru.pfr.contracts2.repository.contracts.NotificationRepository;

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

/*    @Transactional
    public void deleteAll(List<Notification> notifications) {
        for (int i = 0; i < notifications.size(); i++) {
            delete(notifications.get(i).getId());
        }
    }*/

    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
