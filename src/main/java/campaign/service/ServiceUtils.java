package campaign.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ServiceUtils {

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDate convertToLocalDate(Instant instantToConvert) {
        return instantToConvert
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTime(Instant instantToConvert) {
        return instantToConvert
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }
}
