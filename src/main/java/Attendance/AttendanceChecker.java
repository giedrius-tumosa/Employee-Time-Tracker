package Attendance;

import DataProvider.UserEvent;
import Users.User;
import Users.UserLib;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    public enum UserStatus { AT_WORK, NOT_AT_WORK, LATE, LEFT_EARLY, NOT_SHIFT, LEFT_LATE, FORGOT, NOT_FORGOT, LOG_LATE, LOG_ON_TIME, LOG_EARLY, NO_VALUE }


    int userEventAmount = 0;
    boolean isEvenNumber = false;
    int shiftLength = 480; //shift duration in min


    public Map attendance(Map<String, List<UserEvent>> events, LocalDateTime shiftStart, LocalDateTime shiftEnd) {

        Map<User, List<UserStatus>> userStatusMap = new HashMap<>();

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

            User employeeObject = userData.search(entry.getKey(), new UserLib().users());
            userStatusMap.put(employeeObject, new ArrayList<>());
            userStatusMap.get(employeeObject).add(ifAtWork(isEvenNumber));
            userStatusMap.get(employeeObject).add(whereIsUser(personsLastLog, shiftStart, shiftEnd, isEvenNumber, timeOfReview));
            userStatusMap.get(employeeObject).add(ifLogOnTime(isEvenNumber, personsLastLog, shiftStart));
            userStatusMap.get(employeeObject).add(ifLoggedOut(durAtWork, shiftLength, isEvenNumber));


        }

        return userStatusMap;
    }


    //Checks if user is at work-logged in.
    UserStatus ifAtWork(boolean isEvenNumber) {

        UserStatus statusIfAtWork = null;

        if (!isEvenNumber) statusIfAtWork = UserStatus.AT_WORK;
        if (isEvenNumber)  statusIfAtWork = UserStatus.NOT_AT_WORK;

        return statusIfAtWork;
    }

    //Checks why user is not at work.
     UserStatus whereIsUser(LocalDateTime personsLastLog, LocalDateTime shiftStart, LocalDateTime shiftEnd, boolean isEvenNumber, LocalDateTime timeOfReview ){

        boolean isLate = personsLastLog.isBefore(shiftStart) && timeOfReview.isAfter(shiftStart) && isEvenNumber;
        boolean leftEarly = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftStart) && personsLastLog.isBefore(shiftEnd) || personsLastLog.isEqual(shiftStart) && isEvenNumber;
        boolean willStartWork = personsLastLog.isBefore(timeOfReview) && timeOfReview.isBefore(shiftStart) && isEvenNumber;
        boolean leftWorkLate = timeOfReview.isAfter(personsLastLog) && personsLastLog.isAfter(shiftEnd) && timeOfReview.isAfter(shiftEnd) && isEvenNumber;

        UserStatus statusWhereIsUser = UserStatus.NO_VALUE;

        if (isLate)         statusWhereIsUser = UserStatus.LATE;
        if (leftEarly)      statusWhereIsUser = UserStatus.LEFT_EARLY;
        if (willStartWork)  statusWhereIsUser = UserStatus.NOT_SHIFT;
        if (leftWorkLate)   statusWhereIsUser = UserStatus.LEFT_LATE;

        return statusWhereIsUser;
    }

    //Checks if user logged out correctly - on time.
    UserStatus ifLoggedOut(Duration durAtWork, int shiftLength, boolean isEvenNumber) {

        boolean forgotToLogOut = durAtWork.toMinutes() > shiftLength && !isEvenNumber;
        UserStatus statusIfForgot = UserStatus.NO_VALUE;

        if (forgotToLogOut)  statusIfForgot = UserStatus.FORGOT;
        if (!forgotToLogOut) statusIfForgot = UserStatus.NOT_FORGOT;

        return statusIfForgot;
    }

    //Checks why user logged in on time.
    UserStatus ifLogOnTime(boolean isEvenNumber, LocalDateTime personsLastLog, LocalDateTime shiftStart) {

        boolean loggedInLate = personsLastLog.isAfter(shiftStart) && !isEvenNumber;
        boolean loggedOnTime = personsLastLog.isEqual(shiftStart) && !isEvenNumber;
        boolean loggedInEarly = personsLastLog.isBefore(shiftStart) && !isEvenNumber;
        UserStatus statusIfOnTime = UserStatus.NO_VALUE;


        if (loggedInLate)       statusIfOnTime = UserStatus.LOG_LATE;
        if (loggedOnTime)       statusIfOnTime = UserStatus.LOG_ON_TIME;
        if (loggedInEarly)      statusIfOnTime = UserStatus.LOG_EARLY;

        return statusIfOnTime;
    }

    //Converts duration value to readable - hh:mm:ss
    String durationString(Duration duration){

        long durationHours = duration.toHours();
        long durationMinutes = duration.toMinutes() - duration.toHours()*60;
        long durationSeconds = duration.getSeconds() - duration.toMinutes()*60;

        return " " + durationHours + " h " + durationMinutes +" min " + durationSeconds + " s ";
    }

}
