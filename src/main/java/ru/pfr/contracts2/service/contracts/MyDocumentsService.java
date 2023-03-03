package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.entity.MyDocuments;
import ru.pfr.contracts2.repository.contracts.MyDocumentsRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyDocumentsService {
    private final MyDocumentsRepository myDocumentsRepository;


    public void save(MyDocuments myDocuments) {
        myDocumentsRepository.save(myDocuments);
    }

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
