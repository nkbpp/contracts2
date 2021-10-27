package ru.pfr.contracts2.entity.user;

public enum ROLE_ENUM {
    ROLE_READ ("ROLE_READ"),
    ROLE_UPDATE ("ROLE_UPDATE"),
    ROLE_ADMIN ("ROLE_ADMIN");

    private final String code;
    ROLE_ENUM(String code) {
        this.code = code;
    }

    public String getString(){ return code;}
}
