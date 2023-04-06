package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

public enum StatusGk {

    EXECUTED("Исполнен"),
    CURRENT("Действующий"),
    TERMINATED("Расторгнут"),
    NOSTATUS("");

    private final String status;

    StatusGk(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusGk customValueOf(String value) {

        return switch (value) {
            case "Исполнен" -> StatusGk.EXECUTED;
            case "Действующий" -> StatusGk.CURRENT;
            case "Расторгнут" -> StatusGk.TERMINATED;
            default -> StatusGk.NOSTATUS;
        };

    }

}
