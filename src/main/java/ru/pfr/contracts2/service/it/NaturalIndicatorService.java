package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.NaturalIndicator;
import ru.pfr.contracts2.repository.it.NaturalIndicatorRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaturalIndicatorService {
    private final NaturalIndicatorRepository naturalIndicatorRepository;

    @Transactional
    public void save(NaturalIndicator naturalIndicator) {
        naturalIndicatorRepository.save(naturalIndicator);
    }

    @Transactional
    public void delete(Long id) {
        naturalIndicatorRepository.deleteById(id);
    }

    public List<NaturalIndicator> findAll() {
        return naturalIndicatorRepository.findAll();
    }

    public NaturalIndicator findById(Long id) {
        return naturalIndicatorRepository.findById(id).orElse(null);
    }

    public List<NaturalIndicator> findByContractIT(int id) {
        return naturalIndicatorRepository.findByContractIT(id);
    }
}
