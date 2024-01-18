package ru.pfr.contracts2.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.user.Rayon;
import ru.pfr.contracts2.entity.user.Rayon_;
import ru.pfr.contracts2.repositories.user.RayonRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Rayon> findById(Long id) {
        return rayonRepository.findById(id);
    }

    public Optional<Rayon> findByKod(String kod) {
        return rayonRepository.findOne(
                Specification.where(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.equal(root.get(Rayon_.KOD), kod)
                )
        );
    }
}

