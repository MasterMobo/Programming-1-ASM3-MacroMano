package PortSystem.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateUtils {
    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    public static long daysBetween(LocalDate day1, LocalDate day2) {
        return day1.until(day2).getDays();
    }
}
