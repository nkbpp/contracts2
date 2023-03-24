package ru.pfr.contracts2.entity.contractIT.dto;

public record StatItDto(
        Integer size,
        Integer executed, //исполнено
        Integer current, //действует
        Integer terminated, //рассторгнут
        Integer noStatus //без статуса
) {
}
