package ru.pfr.contracts2.global;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyNumbersTest {

    @Test
    public void okrug() {
        assertEquals("10,16",MyNumbers.okrug(10.1569F));
        assertEquals("10,16",MyNumbers.okrug(10.1569D));
        assertEquals("10,99",MyNumbers.okrug(10.9912D));
        assertEquals("11,00",MyNumbers.okrug(10.999D));
    }

}
