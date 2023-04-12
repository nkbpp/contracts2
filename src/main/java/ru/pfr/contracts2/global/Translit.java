package ru.pfr.contracts2.global;

public class Translit {
    public static String cyr2lat(char ch) {
        return switch (ch) {
            case 'А' -> "A";
            case 'Б' -> "B";
            case 'В' -> "V";
            case 'Г' -> "G";
            case 'Д' -> "D";
            case 'Е' -> "E";
            case 'Ё' -> "JE";
            case 'Ж' -> "ZH";
            case 'З' -> "Z";
            case 'И' -> "I";
            case 'Й' -> "Y";
            case 'К' -> "K";
            case 'Л' -> "L";
            case 'М' -> "M";
            case 'Н' -> "N";
            case 'О' -> "O";
            case 'П' -> "P";
            case 'Р' -> "R";
            case 'С' -> "S";
            case 'Т' -> "T";
            case 'У' -> "U";
            case 'Ф' -> "F";
            case 'Х' -> "KH";
            case 'Ц' -> "C";
            case 'Ч' -> "CH";
            case 'Ш' -> "SH";
            case 'Щ' -> "JSH";
            case 'Ъ' -> "HH";
            case 'Ы' -> "IH";
            case 'Ь' -> "JH";
            case 'Э' -> "EH";
            case 'Ю' -> "JU";
            case 'Я' -> "JA";
            case 'а' -> "a";
            case 'б' -> "b";
            case 'в' -> "v";
            case 'г' -> "g";
            case 'д' -> "d";
            case 'е' -> "e";
            case 'ё' -> "je";
            case 'ж' -> "zh";
            case 'з' -> "z";
            case 'и' -> "i";
            case 'й' -> "y";
            case 'к' -> "k";
            case 'л' -> "l";
            case 'м' -> "m";
            case 'н' -> "n";
            case 'о' -> "o";
            case 'п' -> "p";
            case 'р' -> "r";
            case 'с' -> "s";
            case 'т' -> "t";
            case 'у' -> "u";
            case 'ф' -> "f";
            case 'х' -> "kh";
            case 'ц' -> "c";
            case 'ч' -> "ch";
            case 'ш' -> "sh";
            case 'щ' -> "jsh";
            case 'ъ' -> "hh";
            case 'ы' -> "ih";
            case 'ь' -> "jh";
            case 'э' -> "eh";
            case 'ю' -> "ju";
            case 'я' -> "ja";
            case '№' -> "#";
            default -> String.valueOf(ch);
        };
    }

    public static String cyr2lat(String s) {
        StringBuilder sb = new StringBuilder(s.length() * 2);
        for (char ch : s.toCharArray()) {
            sb.append(cyr2lat(ch));
        }
        return sb.toString();
    }

}
