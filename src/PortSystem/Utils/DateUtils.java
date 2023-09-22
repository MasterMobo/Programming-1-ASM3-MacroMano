package PortSystem.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static LocalDate toLocalDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(ConsoleColors.YELLOW + "Could not process date: " +dateString + ". Expected date format: dd/MM/yyyy" + ConsoleColors.RESET);
            return null;
        }
    }

    public static LocalDate dayBeforeToday(int days) {
        LocalDate now = LocalDate.now();
        return now.minusDays(days);
    }

    public static LocalDate dayAfterToday(int days) {
        LocalDate now = LocalDate.now();
        return now.plusDays(days);
    }

    public static long daysBetween(LocalDate day1, LocalDate day2) {
        return day1.until(day2).getDays();
    }
}
