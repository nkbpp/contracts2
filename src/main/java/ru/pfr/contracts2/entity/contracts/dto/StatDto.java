package ru.pfr.contracts2.entity.contracts.dto;

public record StatDto(
        Integer size,
        Integer ispolneno,
        Integer notispolneno,
        Integer notispolnenosrok,
        Integer nodate,
        Integer prosrocheno
) {
}
