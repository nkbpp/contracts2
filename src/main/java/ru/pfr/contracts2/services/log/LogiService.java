package ru.pfr.contracts2.services.log;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.log.dto.ParamLog;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.entity.log.entity.LogiSpecification;
import ru.pfr.contracts2.entity.log.entity.Logi_;
import ru.pfr.contracts2.repositories.log.LogiRepository;

import java.util.List;
import java.util.Optional;

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

    public List<Logi> findAll(Pageable pageable) {
        return logiRepository.findAll(pageable).getContent();
    }

    public List<Logi> findByUser(String user, String type, String text) {
        return logiRepository.findAll(LogiSpecification.findByUser(user, type, text));
        //return logiRepository.findByUser(user, type, text);
    }

    public Optional<Logi> findByUser(String login) {
        return logiRepository.findOne(
                Specification.where(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.equal(root.get(Logi_.USER), login)
                )
        );
    }

    public List<Logi> findByDateBetween(ParamLog paramLog) {
        return logiRepository.findAll(
                LogiSpecification.findByDateParam(paramLog)
        );
    }

}