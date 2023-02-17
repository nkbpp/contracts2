package ru.pfr.contracts2.entity.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ROLE_ENUMTest {

    @Test
    void getOtdelIt() {
        ROLE_ENUM role_enum = ROLE_ENUM.ROLE_READ_IT;

        String otdel = role_enum.getOtdel();

        assertThat(otdel).isEqualTo("IT");
    }

    @Test
    void getOtdelRsp() {
        ROLE_ENUM role_enum = ROLE_ENUM.ROLE_UPDATE_RSP;

        String otdel = role_enum.getOtdel();

        assertThat(otdel).isEqualTo("RSP");
    }
}