package DataProvider;

import Attendance.AttendanceChecker;

import java.time.LocalDateTime;

public class Gates extends  EventData {

    public static void main(String[] args) {

        //Check attendance. Shift fm 9:00 to 17:00
        LocalDateTime shiftStart = LocalDateTime.of(2018, 12, 13, 9, 00, 0);
        LocalDateTime shiftEnd = LocalDateTime.of(2018, 12, 13, 17, 00, 0);

    EventData event = new EventData();

    AttendanceChecker checker = new AttendanceChecker();
    String reiksme = checker.attendance(event.createEvent(), shiftStart, shiftEnd).entrySet().toString();

    System.out.println(reiksme);

    }

}

//        EventData signal = new EventData();
//
//        //User input
//        System.out.println("Enter user:");
//        Scanner sc = new Scanner((System.in));
//        String userID = sc.next();
//
//
//        //User search
//        UserLib k = new UserLib();
//        k.search(userID, new UserLib().users());
//        System.out.println(k.search(userID, new UserLib().users()).getName());        //To check if searches ok.
//
//        signal.createEvent(k.search(userID, new UserLib().users()), LocalDateTime.now()); //TODO - sukurti eventa