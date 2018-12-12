package Attendance;

import DataProvider.UserEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    int userEventAmount = 0;
    boolean isEvenNumber = false;
    int fullDay = 480; //shift duration in min


   public void attendance (Map<String, List<UserEvent>> events, LocalDateTime shiftStart, LocalDateTime shiftEnd){

       for (Map.Entry<String, List<UserEvent>> entry : events.entrySet()) {

        userEventAmount = events.get("0001").size();
        isEvenNumber = userEventAmount%2 == 0;
        LocalDateTime personsLastLog = events.get("0001").get(userEventAmount - 1).time;

        LocalDateTime timeOfReview = LocalDateTime.now(); //review real time


            Duration durTillStart = Duration.between(personsLastLog, shiftStart);   //duration between user's last log and today's shift start time
            Duration durTillReview = Duration.between(shiftStart, timeOfReview);    //duration between today's shift start time and time of review
            Duration durTillEnd = Duration.between(personsLastLog, shiftEnd);       //duration between today's shift end time and user's last log
            Duration durAtWork = Duration.between(personsLastLog, timeOfReview);

            if (isEvenNumber) { // user is logged out

                if ( personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart)) {    //is late
                    System.out.println("Alert message: " + entry.getKey() + " is late for work for " + durTillReview.toMinutes() + " min.");
                } else if (timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart)) {  //left too early
                    System.out.println("Alert message: " + entry.getKey() + " left work " + durTillEnd.toMinutes() + " min too early.");
                } else if (personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart)){  // it is before shift start and shows how long till shift
                    System.out.println(entry.getKey() + " will start work in " + (-1*durTillReview.toMinutes()) + " min.");
                } else if ((timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd))) {  //it is after shift end and user logged out after shift end
                    System.out.println("Alert message: " + entry.getKey() + " left work " + (-1*durTillEnd.toMinutes()) + " min too late.");
                }
            }

            if (!isEvenNumber) { //user is logged in

                if (personsLastLog.isBefore(shiftStart) && durAtWork.toMinutes() > fullDay) { //forgot to log out
                    System.out.println("Alert message: " + entry.getKey() +  " is at work for " + durAtWork.toMinutes() + " min. Forgot to log out.");
                } else if (personsLastLog.isBefore(shiftStart) && durAtWork.toMinutes() <= fullDay){ //logged in too early
                    System.out.println("Alert message: " + entry.getKey() + " is at work for " + durAtWork.toMinutes() + " min. Logged in too early.");
                } else if (personsLastLog.isEqual(shiftStart) && durAtWork.toMinutes() <= fullDay){
                    System.out.println("Alert message: " + entry.getKey() + " is at work for " + durAtWork.toMinutes() + " min. Logged in on time.");
                }else if (personsLastLog.isEqual(shiftStart) && durAtWork.toMinutes() > fullDay){
                    System.out.println("Alert message: " + entry.getKey() + " is at work for " + durAtWork.toMinutes() + " min. Logged in on time. Forgot to log out.");
                }else if (personsLastLog.isAfter(shiftStart) && durAtWork.toMinutes() <= fullDay){
                    System.out.println("Alert message: " + entry.getKey() + " is at work for " + durAtWork.toMinutes() + " min. Logged in late.");
                }else if (personsLastLog.isAfter(shiftStart) && durAtWork.toMinutes() > fullDay){
                    System.out.println("Alert message: " + entry.getKey() + " is at work for " + durAtWork.toMinutes() + " min. Logged in late. Forgot to log out.");

            }

   }



   }


}}
