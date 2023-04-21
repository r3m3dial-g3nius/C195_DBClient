package Utility;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * This class handles time conversion duties
 */
public class TimeTraveller {

    /**
     * converts String values of time and date into properly formatted Timestamp date/time
     *
     * @param time String value of time
     * @param date String value of date
     * @return Timestamp value date/time
     */
    public static Timestamp convertStringTimeDate2TimeStamp(String time, String date)        // not UTC
    {
        DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        LocalTime localTime = LocalTime.parse(time, hourMinFormatter);
        LocalDate localDate = LocalDate.parse(date,dateFormatter);
        LocalDateTime ldt = LocalDateTime.of(localDate, localTime);

        return Timestamp.valueOf(ldt);
    }

    /**
     * formats dateTime with desired time zone (zoneId)
     *
     * @param dateTime LocalDateTime value to format w time zone
     * @param zoneId desired time zone
     * @return LocalDateTime value with desired time zone
     */
    public static LocalDateTime timeZoneFormatter(LocalDateTime dateTime, ZoneId zoneId)
    {
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        zdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
//        LocalDateTime test = zdt.toLocalDateTime();

        return zdt.toLocalDateTime();
    }

}
