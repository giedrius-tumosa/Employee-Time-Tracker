package Attendance;

import DataProvider.UserEvent;
import Users.UserLib;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    public enum UserStatus { AT_WORK, NOT_AT_WORK, LATE, LEFT_EARLY, NOT_SHIFT, LEFT_LATE, FORGOT, NOT_FORGOT, LOG_LATE, LOG_ON_TIME, LOG_EARLY }

    UserStatus status = null;
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


            UserLib userData = new UserLib();
            String userName = userData.search(entry.getKey(), new UserLib().users()).getName();
            String userSurname = userData.search(entry.getKey(), new UserLib().users()).getSurname();


           System.out.print(entry.getKey());
           System.out.print(" " + userName + " " + userSurname);

           ifAtWork(personsLastLog, shiftStart, shiftEnd,isEvenNumber, timeOfReview);
           ifLogOnTime( isEvenNumber,  personsLastLog,  shiftStart);
           ifLoggedOut(durAtWork, shiftLength, isEvenNumber);

           System.out.println(" ");

        }

    }

    //perdaryta logika i metodus

    UserStatus ifAtWork(LocalDateTime personsLastLog, LocalDateTime shiftStart, LocalDateTime shiftEnd, boolean isEvenNumber, LocalDateTime timeOfReview ) {

        boolean isLate = personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart) && isEvenNumber;
        boolean leftEarly = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart) && isEvenNumber;
        boolean willStartWork = personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart) && isEvenNumber;
        boolean leftWorkLate = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd) && isEvenNumber;

        UserStatus statusIfAtWork = null;

        if (!isEvenNumber) statusIfAtWork = UserStatus.AT_WORK;
        if (isEvenNumber)  statusIfAtWork = UserStatus.NOT_AT_WORK;

        return statusIfAtWork;
    }

     UserStatus whereIsUser(LocalDateTime personsLastLog, LocalDateTime shiftStart, LocalDateTime shiftEnd, boolean isEvenNumber, LocalDateTime timeOfReview ){

        boolean isLate = personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart) && isEvenNumber;
        boolean leftEarly = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart) && isEvenNumber;
        boolean willStartWork = personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart) && isEvenNumber;
        boolean leftWorkLate = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd) && isEvenNumber;

        UserStatus statusWhereIsUser = null;

        if (isLate)         statusWhereIsUser = UserStatus.LATE;
        if (leftEarly)      statusWhereIsUser = UserStatus.LEFT_EARLY;
        if (willStartWork)  statusWhereIsUser = UserStatus.NOT_SHIFT;
        if (leftWorkLate)   statusWhereIsUser = UserStatus.LEFT_LATE;

        return statusWhereIsUser;
    }


    UserStatus ifLoggedOut(Duration durAtWork, int shiftLength, boolean isEvenNumber) {

        boolean forgotToLogOut = durAtWork.toMinutes() > shiftLength && !isEvenNumber;
        UserStatus statusIfForgot = null;

        if (forgotToLogOut)  statusIfForgot = UserStatus.FORGOT;
        if (!forgotToLogOut) statusIfForgot = UserStatus.NOT_FORGOT;

        return statusIfForgot;
    }


    UserStatus ifLogOnTime(boolean isEvenNumber, LocalDateTime personsLastLog, LocalDateTime shiftStart) {

        boolean loggedInLate = personsLastLog.isAfter(shiftStart) && !isEvenNumber;
        boolean loggedOnTime = personsLastLog.isEqual(shiftStart) && !isEvenNumber;
        boolean loggedInEarly = personsLastLog.isBefore(shiftStart) && !isEvenNumber;
        UserStatus statusIfOnTime = null;


        if (loggedInLate)       statusIfOnTime = UserStatus.LOG_LATE;
        if (loggedOnTime)       statusIfOnTime = UserStatus.LOG_ON_TIME;
        if (loggedInEarly)      statusIfOnTime = UserStatus.LOG_EARLY;

        return statusIfOnTime;
    }

    String durationString(Duration duration){

        long durationHours = duration.toHours();
        long durationMinutes = duration.toMinutes() - duration.toHours()*60;
        long durationSeconds = duration.getSeconds() - duration.toMinutes()*60;

        return " " + durationHours + " h " + durationMinutes +" min " + durationSeconds + " s ";
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