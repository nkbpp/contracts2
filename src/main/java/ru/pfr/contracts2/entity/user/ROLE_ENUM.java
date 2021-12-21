package ru.pfr.contracts2.entity.user;

public enum ROLE_ENUM {
    ROLE_READ ("READ"),
    ROLE_UPDATE ("UPDATE"),
    ROLE_ADMIN ("ADMIN"),
    ROLE_UPDATE_IT ("UPDATEIT"),
    ROLE_READ_IT ("READIT");

    private final String code;

    ROLE_ENUM(String code) {
        this.code = code;
    }

    //где то роль нужна с ROLE_
    public String getString(){ return "ROLE_" + code;}

    //где то без
    public String getStringNoRole(){ return code;}
}
