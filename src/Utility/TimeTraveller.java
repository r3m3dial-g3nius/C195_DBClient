package Utility;

import Controller.AppointmentScreenController;
import Controller.ModifyAppointmentScreenController;
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


    /**
     * This method evaluates if the requested appointment start/end times are during business hours (8:00AM - 10:00PM EST)
     * @param requestedStartLDT user requested appointment start time
     * @param requestedEndLDT user requested appointment end time
     * @return true if appointment start/end times are within business hours, false if not
     * @throws DateTimeException
     */
    public static boolean inBusinessHours(LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT) throws DateTimeException
    {

        //   ********************   WHICH ONE IS CORRECT?   *****************************************
        //   >>----->   establish user requested LDT in Zone ID system.default()   <-----<<
        requestedStartLDT = TimeTraveller.timeZoneFormatter(requestedStartLDT, ZoneId.systemDefault());
        requestedEndLDT = TimeTraveller.timeZoneFormatter(requestedEndLDT, ZoneId.systemDefault());

        //   >>----->   change user requested LDT to Zone ID America/New_York   <-----<<
        requestedStartLDT = TimeTraveller.timeZoneFormatter(requestedStartLDT, ZoneId.of("America/New_York"));
        requestedEndLDT = TimeTraveller.timeZoneFormatter(requestedEndLDT, ZoneId.of("America/New_York"));
        //   ********************   WHICH ONE IS CORRECT?   *****************************************





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

        //   >>----->   evaluate if start/end times are during business hours   <-----<<
        if ((requestedStartTime.isAfter(workdayStartTime)) && requestedStartTime.isBefore(workdayEndTime) &&
                (requestedEndTime.isAfter(workdayStartTime)) && (requestedEndTime.isBefore(workdayEndTime)))
        {
            return true;
        }

        return false;

    }


    //   >>----->   This is commented out. Per program instructor, appointments can be scheduled 7 days/week   <-----<<
    //   >>----->   It is commented out on AddAppointmentScreenController and ModifyScreenController as well.   <-----<<
    //   >>----->   I chose not to delete the code in case it was a traditional 5 day workweek.   <-----<<
//    /**
//     * This method evaluates if user requested appointment dates are during a Monday - Friday workweek
//     * @param requestedStartLDT user requested start date
//     * @param requestedEndLDT user requested end date
//     * @return true if start/end dates are Monday - Friday, false if not
//     */
//    public static boolean isMondayThruFriday(LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT)
//    {
//        DayOfWeek workWeekStartDay = DayOfWeek.MONDAY;
//        DayOfWeek workWeekEndDay = DayOfWeek.FRIDAY;
//
//        if ((workWeekEndDay.getValue() < requestedStartLDT.getDayOfWeek().getValue()) ||
//                (requestedStartLDT.getDayOfWeek().getValue() < workWeekStartDay.getValue()) ||
//                ((workWeekEndDay.getValue() < requestedEndLDT.getDayOfWeek().getValue()) ||
//                        (requestedEndLDT.getDayOfWeek().getValue() < workWeekStartDay.getValue())))
//        {
//            return false;
//        }
//        return true;
//    }


    /**
     * This method evaluates if user requested appointment date/time will overlap any existing appointments.
     * @param isNewAppointment true if this appointment is a new appointment to be added, false if modifying existing appointment
     * @param customer Customer in which appointment is being scheduled
     * @param requestedStartLDT user requested appointment start date/time
     * @param requestedEndLDT user requested appointment end date/time
     * @return returns 1 if overlap is found for user requested start date/time, returns 2 if overlap is found for user
     * requested end date/time, returns 3 if an appointment is already scheduled  between user requested start/end
     * date/time, returns 4 if no overlap or no appointments are found for customer
     */
    public static int isOverlappingTimes(boolean isNewAppointment, Customer customer, LocalDateTime requestedStartLDT, LocalDateTime requestedEndLDT)
    {
        ObservableList<Appointment> customerAppointments = customer.getCustomerAppointmentList();
        ObservableList<Appointment> updatedCustomerAppointments = FXCollections.observableArrayList();

        System.out.println(customer.getCustomerName());

        //   >>---------->   if modifying appointment, remove appointment to be modified from list   <----------<<
        if (customer.hasAppointments() && !isNewAppointment)
        {
            for (Appointment a : customerAppointments)
            {
                if (a.getAppointmentID() != ModifyAppointmentScreenController.selectedAppointment.getAppointmentID())
                {
                    updatedCustomerAppointments.add(a);
                }
            }
            customerAppointments = updatedCustomerAppointments;
        }

        if (customer.hasAppointments()) {


            System.out.println(customer.getCustomerName() + " has " + customerAppointments.size() + " appointments");

            for (Appointment a : customerAppointments)
            {
                // >>----->  get appointment start/end times   <-----<<
                LocalDateTime thisAppointmentStart = a.getAppointmentStart();
                LocalDateTime thisAppointmentEnd = a.getAppointmentEnd();

                System.out.println("This appointment start/end : " + thisAppointmentStart + " - " + thisAppointmentEnd);
                System.out.println("Requested start/end : " + requestedStartLDT + " - " + requestedEndLDT);

                // >>----->  check start times   <-----<<
                if (requestedStartLDT.equals(thisAppointmentStart) ||
//                        (requestedStartLDT.equals(thisAppointmentEnd)) ||         //   commented out if back to back appointments not allowed
                        (requestedStartLDT.isAfter(thisAppointmentStart) && requestedStartLDT.isBefore(thisAppointmentEnd)))
                {
                    System.out.println("This appointment start time/date conflicts with existing appointment ID " + a.getAppointmentID());
                    return 1;
                }
                // >>----->  check end times   <-----<<
                else if (requestedEndLDT.equals(thisAppointmentEnd) ||
//                        (requestedEndLDT.equals(thisAppointmentStart)) ||         //   commented out if back to back appointments not allowed
                        ((requestedEndLDT.isAfter(thisAppointmentStart) && requestedEndLDT.isBefore(thisAppointmentEnd))))
                {
                    System.out.println("This appointment end time/date conflicts with existing appointment ID " + a.getAppointmentID());
                    return 2;
                }
                // >>----->  check if requested times encompass an existing appointment  <-----<<
                else if (requestedStartLDT.isBefore(thisAppointmentStart) && requestedEndLDT.isAfter(thisAppointmentEnd))
                {
                    System.out.println("Appointment ID " + a.getAppointmentID() + " is during the requested start/end time.");
                    return 3;
                }

            }
            // >>----->  customer has appointments but no conflicts found   <-----<<
            System.out.println(customer.getCustomerName() + " has appointments today, but no conflicts detected.");
        }

        // >>----->  customer has no appointments   <-----<<
        else //if (!customer.hasAppointments())
        {
            System.out.println(customer.getCustomerName() + " has no appointments. Adding this appointment.");
        }
        return 4;
    }

}
