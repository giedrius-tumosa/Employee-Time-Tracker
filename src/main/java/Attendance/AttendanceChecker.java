package Attendance;

import DataProvider.UserEvent;
import Users.UserLib;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    int userEventAmount = 0;
    boolean isEvenNumber = false;
    int shiftLength = 480; //shift duration in min


    public void attendance(Map<String, List<UserEvent>> events, LocalDateTime shiftStart, LocalDateTime shiftEnd) {

        for (Map.Entry<String, List<UserEvent>> entry : events.entrySet()) {


            userEventAmount = events.get(entry.getKey()).size();
            isEvenNumber = userEventAmount % 2 == 0;
            LocalDateTime personsLastLog = events.get(entry.getKey()).get(userEventAmount - 1).time;

            LocalDateTime timeOfReview = LocalDateTime.now(); //review real time


            Duration durTillStart = Duration.between(personsLastLog, shiftStart);   //duration between user's last log and today's shift start time
            Duration durTillReview = Duration.between(shiftStart, timeOfReview);    //duration between today's shift start time and time of review
            Duration durTillEnd = Duration.between(personsLastLog, shiftEnd);       //duration between today's shift end time and user's last log
            Duration durAtWork = Duration.between(personsLastLog, timeOfReview);


            UserLib k = new UserLib();
            String userName = k.search(entry.getKey(), new UserLib().users()).getName();
            String userSurname = k.search(entry.getKey(), new UserLib().users()).getSurname();


           System.out.print(entry.getKey());
           System.out.print(" " + userName + " " + userSurname);
           whereIsUser(personsLastLog, shiftStart, shiftEnd,isEvenNumber, timeOfReview);
           ifLogOnTime( isEvenNumber,  personsLastLog,  shiftStart);
           forgotToLogOut(durAtWork, shiftLength, isEvenNumber);

           System.out.println(" ");

        }

    }


    public static void whereIsUser(LocalDateTime personsLastLog, LocalDateTime shiftStart, LocalDateTime shiftEnd, boolean isEvenNumber, LocalDateTime timeOfReview ) {

        boolean isLate = personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart) && isEvenNumber;
        boolean leftEarly = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart) && isEvenNumber;
        boolean willStartWork = personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart) && isEvenNumber;
        boolean leftWorkLate = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd) && isEvenNumber;

        if (!isEvenNumber) System.out.print(" At work.");
        if (isEvenNumber) {
            System.out.print(" Not at work.");

            if (isLate) System.out.print(" Late.");
            if (leftEarly) System.out.print(" Left work early.");
            if (willStartWork) System.out.print(" Shift not started.");
            if (leftWorkLate) System.out.print(" Left work late.");
        }

    }


    public static void forgotToLogOut(Duration durAtWork, int shiftLength, boolean isEvenNumber) {

        if (durAtWork.toMinutes() > shiftLength && !isEvenNumber) {
            System.out.print(" Forgot to log out.");
        }

    }

    public static void ifLogOnTime(boolean isEvenNumber, LocalDateTime personsLastLog, LocalDateTime shiftStart) {

        if (personsLastLog.isAfter(shiftStart) && !isEvenNumber) System.out.print(" Logged in late.");
        if (personsLastLog.isEqual(shiftStart) && !isEvenNumber) System.out.print(" Logged on time.");
        if (personsLastLog.isBefore(shiftStart) && !isEvenNumber) System.out.print(" Logged in early.");

    }


}
//   SENA LOGIKA:
//            if (isEvenNumber) { // user is logged out
//
//                if (personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart)) {    //is late
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is late for " + durTillReview.toMinutes() + " min.");
//                } else if (timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart)) {  //left too early
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "left work " + durTillEnd.toMinutes() + " min too early.");
//                } else if (personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart)) {  // it is before shift start and shows how long till shift
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "will start work in " + (-1 * durTillReview.toMinutes()) + " min.");
//                } else if ((timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd))) {  //it is after shift end and user logged out after shift end
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "left work " + (-1 * durTillEnd.toMinutes()) + " min too late.");
//                }
//            }

//            if (!isEvenNumber) { //user is logged in
//
//                if (personsLastLog.isBefore(shiftStart) && durAtWork.toMinutes() > shiftLength) { //forgot to log out
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Forgot to log out.");
//                } else if (personsLastLog.isBefore(shiftStart) && durAtWork.toMinutes() <= shiftLength) { //logged in too early
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Logged in too early.");
//                } else if (personsLastLog.isEqual(shiftStart) && durAtWork.toMinutes() <= shiftLength) {//logged in on time
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Logged in on time.");
//                } else if (personsLastLog.isEqual(shiftStart) && durAtWork.toMinutes() > shiftLength) {
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Logged in on time. Forgot to log out.");
//                } else if (personsLastLog.isAfter(shiftStart) && durAtWork.toMinutes() <= shiftLength) {
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Logged in late.");
//                } else if (personsLastLog.isAfter(shiftStart) && durAtWork.toMinutes() > shiftLength) {
//                    System.out.println(entry.getKey() + " " + userName + " " + userSurname + " - " + "is at work for " + durAtWork.toMinutes() + " min. Logged in late. Forgot to log out.");
//
//                }
//
//            }