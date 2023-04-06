package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;
import ru.pfr.contracts2.repository.it.ItDocumentsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItDocumentsService {
    private final ItDocumentsRepository itDocumentsRepository;

    @Transactional
    public void save(DopDocuments dopDocuments) {
        itDocumentsRepository.save(dopDocuments);
    }

    @Transactional
    public void delete(Long id) {
        itDocumentsRepository.deleteById(id);
    }

    public List<DopDocuments> findAll() {
        return itDocumentsRepository.findAll();
    }

    public DopDocuments findById(Long id) {
        return itDocumentsRepository.findById(id).orElse(null);
    }


    public List<DopDocuments> findByContractIT(int id) {
        return itDocumentsRepository.myFindByContractIT(id);
    }
}
