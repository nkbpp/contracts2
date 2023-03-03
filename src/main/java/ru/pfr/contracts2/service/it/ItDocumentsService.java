package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contractIT.entity.ItDocuments;
import ru.pfr.contracts2.repository.it.ItDocumentsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItDocumentsService {
    private final ItDocumentsRepository itDocumentsRepository;

    @Transactional
    public void save(ItDocuments itDocuments) {
        itDocumentsRepository.save(itDocuments);
    }

    @Transactional
    public void delete(Long id) {
        itDocumentsRepository.deleteById(id);
    }

    public List<ItDocuments> findAll() {
        return itDocumentsRepository.findAll();
    }

    public ItDocuments findById(Long id) {
        return itDocumentsRepository.findById(id).orElse(null);
    }


    public List<ItDocuments> findByContractIT(int id) {
        return itDocumentsRepository.findByContractIT(id);
    }
}
