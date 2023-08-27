package PortManagementSystem.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateUtils {
    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }
}
