package ru.pfr.contracts2.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.repository.user.RayonRepository;
import ru.pfr.contracts2.entity.user.Rayon;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RayonService {

    private final RayonRepository rayonRepository;

    public void save(Rayon rayon) {
        rayonRepository.save(rayon);
    }

    public List<Rayon> findAll() {
        return rayonRepository.findAll();
    }

    public List<Rayon> findAllMRU() {
        List<Rayon> r = new ArrayList<>();
        rayonRepository.findAll().forEach(rayon -> {
            if(rayon.getId().equals(1L) ||
                    rayon.getId().equals(3L) ||
                    rayon.getId().equals(4L) ||
                    rayon.getId().equals(5L) ||
                    rayon.getId().equals(6L) ||
                    rayon.getId().equals(7L) ||
                    rayon.getId().equals(8L) ||
                    rayon.getId().equals(10L) ||
                    rayon.getId().equals(2L) //НАДО БУДЕТ УБРАТЬ
            ) {
                r.add(rayon);
            }
        });
        return r;
    }

    public List<Rayon> findAllUpfr(String p1, String p2) {
        return rayonRepository.findByKodNotAndKodNot(p1, p2);
    }

    public Rayon findById(Long id) {
        return rayonRepository.findById(id).orElse(null);
    }

    public Rayon findByKod(String kod) {
        Rayon r = rayonRepository.findByKod(kod).orElse(null);
        if(r == null){
            r =  rayonRepository.findByKod("1000").orElse(null);
        }
        return r;
    }
}

