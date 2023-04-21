package Utility;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeTraveller {

    public static Timestamp convertStringTimeDate2TimeStamp(String time, String date)        // not UTC
    {
        // code
        DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        LocalTime localTime = LocalTime.parse(time, hourMinFormatter);
        LocalDate localDate = LocalDate.parse(date,dateFormatter);
        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return Timestamp.valueOf(ldt);
    }

    public static LocalDateTime timeZoneFormatter(LocalDateTime dateTime, ZoneId zoneId)
    {
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        zdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
//        LocalDateTime test = zdt.toLocalDateTime();

        return zdt.toLocalDateTime();
    }

}
