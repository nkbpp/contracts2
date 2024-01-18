package ru.pfr.contracts2.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.services.user.RayonService;

@Component
@RequiredArgsConstructor
public class RayonMapper {
    private final RayonService rayonService;

    public RayonDto toDto(Rayon obj) {
        return RayonDto.builder()
                .id(obj.getId())
                .namerayon(obj.getNamerayon())
                .kod(obj.getKod())
                .inn(obj.getInn())
                .kpp(obj.getKpp())
                .data_p_u(obj.getData_p_u())
                .data_s_u(obj.getData_s_u())
                .kategorija(obj.getKategorija())
                .regnummru(obj.getRegnummru())
                .build();
    }

    public Rayon fromDto(RayonDto dto) {
        if (dto.getId() != null) {
            return rayonService.findById(dto.getId()).orElse(null);
        }
        return Rayon.builder()
                .id(dto.getId())
                .namerayon(dto.getNamerayon())
                .kod(dto.getKod())
                .inn(dto.getInn())
                .kpp(dto.getKpp())
                .data_p_u(dto.getData_p_u())
                .data_s_u(dto.getData_s_u())
                .kategorija(dto.getKategorija())
                .regnummru(dto.getRegnummru())
                .build();
    }
}
