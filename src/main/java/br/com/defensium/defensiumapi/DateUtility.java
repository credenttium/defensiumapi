package br.com.defensium.defensiumapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    private static final String FORMATO_01 = "dd/MM/yyyy HH:mm:ss";

    public static String getDataAtualFormatada(String formato) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Formato: dd/MM/yyyy HH:mm:ss
     */
    public static String getFormato01() {
        return FORMATO_01;
    }

}
