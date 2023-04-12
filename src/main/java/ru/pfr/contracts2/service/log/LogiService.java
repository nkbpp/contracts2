package ru.pfr.contracts2.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.repository.log.LogiRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogiService {

    private final LogiRepository logiRepository;

    @Transactional
    public void save(Logi logi) {
        logiRepository.save(logi);
    }

    @Transactional
    public void clear() {
        logiRepository.deleteAll();
    }

    public List<Logi> findAll() {
        return logiRepository.findAll();
    }

    public List<Logi> findByDateBetween(Date d1, Date d2, String user, Long l, String text) {
        return logiRepository.findByDateParam(d1, d2, user, l, text);
    }

    public List<Logi> findByDateBetween(String user, Long l, String text) {
        return logiRepository.findByUser(user, l, text);
    }

    public Logi findByUser(String login) {
        return logiRepository.findByUser(login).orElse(null);
    }
}