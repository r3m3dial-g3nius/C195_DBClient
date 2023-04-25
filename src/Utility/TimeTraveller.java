package Utility;

import DAO.DBCustomers;
import Models.Appointment;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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





















    public static boolean inBusinessHours(LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT) throws DateTimeException
    {
        //  -----------------   FIXME   ----------------------


        //   >>----->   establish user requested LDT in Zone ID system.default()   <-----<<
        requestedStartLDT = TimeTraveller.timeZoneFormatter(requestedStartLDT, ZoneId.systemDefault());
        requestedEndLDT = TimeTraveller.timeZoneFormatter(requestedEndLDT, ZoneId.systemDefault());

        //   >>----->   extract local time values   <-----<<
        LocalTime requestedStartTime = requestedStartLDT.toLocalTime();
        LocalTime requestedEndTime = requestedEndLDT.toLocalTime();

        //   >>----->   establish business hours in Zone ID "America/New_York"   <-----<<
        LocalTime workdayStartTime = LocalTime.of(8, 0);
        LocalDateTime workdayStartTimeLDT = LocalDateTime.of(LocalDate.now(), workdayStartTime);
        workdayStartTimeLDT = TimeTraveller.timeZoneFormatter(workdayStartTimeLDT, ZoneId.of("America/New_York"));
        workdayStartTime = workdayStartTimeLDT.toLocalTime();

        LocalTime workdayEndTime = LocalTime.of(22,0);
        LocalDateTime workdayEndTimeLDT = LocalDateTime.of(LocalDate.now(), workdayEndTime);
        workdayEndTimeLDT = TimeTraveller.timeZoneFormatter(workdayEndTimeLDT, ZoneId.of("America/New_York"));
        workdayEndTime = workdayEndTimeLDT.toLocalTime();

        if ((requestedStartTime.isBefore(workdayStartTime)) ||
                requestedStartTime.isAfter(workdayEndTime) ||
                (requestedEndTime.isBefore(workdayStartTime)) ||
                (requestedEndTime.isAfter(workdayEndTime)))
        {
            return false;
        }

        return true;
        //  -----------------   FIXME   ----------------------

    }


    //  -----------  don't need this
    public static boolean isMondayThruFriday(LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT)
    {
        DayOfWeek workWeekStartDay = DayOfWeek.MONDAY;
        DayOfWeek workWeekEndDay = DayOfWeek.FRIDAY;

        if ((workWeekEndDay.getValue() < requestedStartLDT.getDayOfWeek().getValue()) ||
                (requestedStartLDT.getDayOfWeek().getValue() < workWeekStartDay.getValue()) ||
                ((workWeekEndDay.getValue() < requestedEndLDT.getDayOfWeek().getValue()) ||
                        (requestedEndLDT.getDayOfWeek().getValue() < workWeekStartDay.getValue())))
        {
            return false;
        }
        return true;
    }

    public static int isOverlappingTimes(Customer customer, LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT)
    {
        //  -----------------   FIXME   ----------------------
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        System.out.println(customer.getCustomerName());

        if (customer.hasAppointments()) {
            customerAppointments = customer.getCustomerAppointmentList();
            System.out.println(customer.getCustomerName() + " has " + customerAppointments.size() + " appointments");

            for (Appointment a : customerAppointments)
            {
                // >>----->  get appointment start/end times   <-----<<
                LocalDateTime thisAppointmentStart = a.getAppointmentStart();
                LocalDateTime thisAppointmentEnd = a.getAppointmentEnd();

                System.out.println("This appointment start/end : " + thisAppointmentStart + " - " + thisAppointmentEnd);
                System.out.println("Requested start/end : " + requestedStartLDT + " - " + requestedEndLDT);

//                if (requestedStartLDT.equals(thisAppointmentStart) ||
//                        requestedEndLDT.equals(thisAppointmentEnd) ||
//                        (requestedStartLDT.isAfter(thisAppointmentStart) && requestedStartLDT.isBefore(thisAppointmentEnd))
//                        || ((requestedEndLDT.isAfter(thisAppointmentStart) && requestedEndLDT.isBefore(thisAppointmentEnd))))
//                {
//                    System.out.println("This appointment conflicts with existing appointment ID" + a.getAppointmentID());
//                    return true;
//                }

                if (requestedStartLDT.equals(thisAppointmentStart) ||
//                        (requestedStartLDT.equals(thisAppointmentEnd)) ||
                        (requestedStartLDT.isAfter(thisAppointmentStart) && requestedStartLDT.isBefore(thisAppointmentEnd)))
                {
                    System.out.println("This appointment start time/date conflicts with existing appointment ID " + a.getAppointmentID());
                    return 1;
                }

                else if (requestedEndLDT.equals(thisAppointmentEnd) ||
                        (requestedEndLDT.equals(thisAppointmentStart)) ||
                        ((requestedEndLDT.isAfter(thisAppointmentStart) && requestedEndLDT.isBefore(thisAppointmentEnd))))
                {
                    System.out.println("This appointment end time/date conflicts with existing appointment ID " + a.getAppointmentID());
                    return 2;
                }

                else if (requestedStartLDT.isBefore(thisAppointmentStart) && requestedEndLDT.isAfter(thisAppointmentEnd))
                {
                    System.out.println("Appointment ID " + a.getAppointmentID() + " is during the requested start/end time.");
                    return 3;
                }

            }
            System.out.println(customer.getCustomerName() + " has appointments today, but no conflicts detected.");
            return 4;
        }

        else    //  customer has no appointments
        {
            System.out.println(customer.getCustomerName() + " has no appointments. Adding this appointment.");
            return 3;
        }
    }

}
