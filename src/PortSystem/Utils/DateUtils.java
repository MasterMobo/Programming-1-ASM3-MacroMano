package PortSystem.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class DateUtils {
    public static LocalDate toLocalDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Could not process date: " +dateString + ". Expected date format: dd/MM/yyyy");
            return null;
        }
    }

    public static long daysBetween(LocalDate day1, LocalDate day2) {
        return day1.until(day2).getDays();
    }
}
