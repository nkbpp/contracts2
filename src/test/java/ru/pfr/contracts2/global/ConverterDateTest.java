package ru.pfr.contracts2.global;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterDateTest {

    private Date date;

    @Before
    public void setUp() {
        System.out.println("Code executes before each test method");

        String date2 = "13.08.2020";
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            date = format.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void datetostring_yyyyMMdd() { //американский формат
        String actual = ConverterDate.datetostring_yyyyMMdd(date);
        assertEquals("2020-08-13",actual);
    }

    @Test
    public void datetostring_ddMMyyyy() { //американский формат
        String actual = ConverterDate.datetostring_ddMMyyyy(date);
        assertEquals("13.08.2020",actual);
    }

    @Test
    public void stringToDate() {
        assertEquals(date,ConverterDate.stringToDate("13.08.2020"));
        assertEquals(date,ConverterDate.stringToDate("2020-08-13"));
        assertNull(ConverterDate.stringToDate("1"));
    }

/*    @Test (expected = ParseException.class)
    public void stringToDateErr() {
        ConverterDate.stringToDate("1");
    }*/

    //прибавить к дате
    @Test
    public void plusDay() {
        assertEquals("13.09.2020",ConverterDate.datetostring_ddMMyyyy(ConverterDate.plusDay(date,31)));
    }

    @Test
    public void plusMonth() {
        assertEquals("13.02.2021",ConverterDate.datetostring_ddMMyyyy(ConverterDate.plusMonth(date,6)));
    }

    @Test
    public void plusYear() {
        assertEquals("13.08.2022",ConverterDate.datetostring_ddMMyyyy(ConverterDate.plusYear(date,2)));
    }


}
