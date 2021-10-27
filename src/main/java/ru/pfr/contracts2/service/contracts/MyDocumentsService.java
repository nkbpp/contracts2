package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.repository.contracts.MyDocumentsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyDocumentsService {
    private final MyDocumentsRepository myDocumentsRepository;

    @Transactional
    public void save(MyDocuments myDocuments) {
        myDocumentsRepository.save(myDocuments);
    }

    @Transactional
    public void delete(Long id) {
        myDocumentsRepository.deleteById(id);
    }

    public List<MyDocuments> findAll() {
        return myDocumentsRepository.findAll();
    }

    public MyDocuments findById(Long id) {
        return myDocumentsRepository.findById(id).orElse(null);
    }
}
