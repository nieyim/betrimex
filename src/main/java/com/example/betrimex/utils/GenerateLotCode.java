package com.example.betrimex.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateLotCode {
    public static String generateRedCardLot(long totalLot) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("ddMM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");

        String dayMonth = now.format(dayMonthFormatter);
        String year = now.format(yearFormatter);

        String indexFormatted = String.format("%02d", totalLot);

        return String.format("LTD_%s_%s_%s", dayMonth, year, indexFormatted);
    }

}
