package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

public record StatisticDto(
        Integer size,
        Integer executed, //исполнено
        Integer current, //действует
        Integer terminated, //рассторгнут
        Integer noStatus //без статуса
) {
}
