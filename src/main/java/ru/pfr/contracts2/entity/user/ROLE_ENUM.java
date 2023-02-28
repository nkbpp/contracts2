package ru.pfr.contracts2.entity.user;

public enum ROLE_ENUM {
    ROLE_READ("READ"),
    ROLE_UPDATE("UPDATE"),
    ROLE_ADMIN("ADMIN"),
    ROLE_UPDATE_IT("UPDATEIT"),
    ROLE_READ_IT("READIT"),
    ROLE_UPDATE_AXO("UPDATEAXO"),
    ROLE_READ_AXO("READAXO"),
    ROLE_READ_RSP("READRSP"),
    ROLE_UPDATE_RSP("UPDATERSP");

    private final String code;

    ROLE_ENUM(String code) {
        this.code = code;
    }

    //где то роль нужна с ROLE_
    public String getString() {
        return "ROLE_" + code;
    }

    //где то без
    public String getStringNoRole() {
        return code;
    }

    public String getOtdel() {
        return code.replace("READ", "").replace("UPDATE", "");
    }

    public static ROLE_ENUM customValueOf(String value) {
        if (value.matches(".*UPDATE.+") || value.matches(".*READ.+")) {
            value = value
                    .replaceAll("UPDATE", "UPDATE_")
                    .replaceAll("READ", "READ_");
        }
        return ROLE_ENUM.valueOf(value);
    }
}
