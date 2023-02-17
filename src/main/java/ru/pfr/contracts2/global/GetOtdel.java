package ru.pfr.contracts2.global;

import org.springframework.security.core.Authentication;

public class GetOtdel {

    public static String get(Authentication authentication) {

        for (var str :
                authentication.getAuthorities()) {
            String otdel = str.toString()
                    .replace("_", "")
                    .replace("ROLEREAD", "")
                    .replace("ROLEUPDATE", "");
            if (otdel.length() > 0) {
                return otdel;
            }
        }
        return "";
    }
}
