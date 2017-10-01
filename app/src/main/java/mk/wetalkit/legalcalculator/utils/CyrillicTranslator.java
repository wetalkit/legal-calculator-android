package mk.wetalkit.legalcalculator.utils;

/**
 * Created by nikolaminoski on 10/2/17.
 */

public class CyrillicTranslator {

    public static String transliterate(String message) {
        try {
            char[] abcCyr = {' ', 'а', 'б', 'в', 'г', 'д', 'ѓ', 'е', 'ё', 'ж', 'з', 'и', 'й', 'ј', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'џ', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Ѓ', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'Ј', 'К', 'Л', 'Љ', 'М', 'Н', 'Њ', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Џ', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'љ', 'm', 'n', 'њ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            String[] abcLat = {" ", "a", "b", "v", "g", "d", "gj", "e", "e", "zh", "z", "i", "y", "J", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "dz", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "GJ", "E", "E", "Zh", "Z", "I", "Y", "J", "K", "L", "LJ", "M", "N", "NJ", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "DZ", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "lj", "m", "n", "nj", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            if(abcCyr.length != abcLat.length)
                throw new Exception("Incorrect mapping.");

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < message.length(); i++) {
                for (int x = 0; x < abcCyr.length; x++)
                    if (message.charAt(i) == abcCyr[x]) {
                        builder.append(abcLat[x]);
                    }
            }
            return builder.toString();
        } catch (Exception e) {
            return "n/a";
        }
    }
}
