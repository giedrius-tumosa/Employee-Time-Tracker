package DataProvider;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;

public class Gates extends  EventData {

    public static void main(String[] args) {

        EventData signal = new EventData();

        System.out.println();

        Scanner sc = new Scanner((System.in));
        String userID = sc.next();


        signal.createEvent(userID, LocalDateTime.now()); //TODO - sukurti eventa

        LocalDateTime shiftStart = LocalDateTime.of(2018, 12, 9, 9, 0, 0);
        LocalDateTime shiftEnd = LocalDateTime.of(2018, 12, 9, 17, 0, 0);

        AttendanceChecker j = new AttendanceChecker();
        j.attendance(new EventData().getEvents(), shiftStart, shiftEnd);

    }

}
