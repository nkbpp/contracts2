package ru.pfr.contracts2.entity.contracts.parent.dto;

public record StatDto(
        Integer size,
        Integer ispolneno,
        Integer notispolneno,
        Integer notispolnenosrok,
        Integer nodate,
        Integer prosrocheno
) {
}
