package DataProvider;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Gates extends  EventData {

    public static void main(String[] args) {

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



        //Check attendance
        LocalDateTime shiftStart = LocalDateTime.of(2018, 12, 11, 9, 0, 0);
        LocalDateTime shiftEnd = LocalDateTime.of(2018, 12, 11, 17, 0, 0);


        EventData da = new EventData();

        AttendanceChecker j = new AttendanceChecker();
        j.attendance( da.createEvent(), shiftStart, shiftEnd);

    }

}
