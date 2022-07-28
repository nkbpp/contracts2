package ru.pfr.contracts2.global;

import java.text.DecimalFormat;

public class MyNumbers {

    public static String okrug(Float f) {
        return f != null ? new DecimalFormat("#0.00").format(f).replace(",",".") : null;
    }

    public static String okrug(Double f) {
        return f != null ? new DecimalFormat("#0.00").format(f).replace(",",".") : null;
    }

}
