package DataProvider;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    int userEventAmount = 0;
    boolean isEvenNumber = false;


   public void attendance (Map<String, List<UserEvent>> events, LocalDateTime shiftStart, LocalDateTime shiftEnd){

       for (Map.Entry<String, List<UserEvent>> entry : events.entrySet()) {

        userEventAmount = events.get("0001").size();
        isEvenNumber = userEventAmount%2 == 0;
        LocalDateTime personsLastTime = events.get("0001").get(userEventAmount - 1).time;



            Duration duration = Duration.between(shiftStart, personsLastTime); //TODO - evento laikas - kaip ji pasiimt? turetu ziureti  i paskutini zmogaus evento laika

            if (isEvenNumber) {

                if (duration.isNegative()) {
                    System.out.println("Alert message: " + entry.getKey() + " is late for work for " + ((-1)*duration.toHours() + "h "));
                } else if (duration.toHours() < 8) {
                    System.out.println("Alert message: " + entry.getKey() + " left work too early.");
                } else {
                    System.out.println(entry.getKey() + " will start work in " + duration.toHours() + "h.");
                }

            }

            if (!isEvenNumber) {

                if (duration.toHours() > 8 || duration.isZero()) {
                    System.out.println("Alert message: " + entry.getKey() +  " is at work for " + duration.toHours() + "h.");
                } else {
                    System.out.println(entry.getKey() + " is at work.");
                }

            }

   }


//   public void shiftAdherance (LocalTime shiftStart, LocalTime shiftEnd, LocalTime userLog) {
//
//       //kaip padaryti, jog palygintu shifto adherance?
//       Duration duration = Duration.between(shiftStart, userLog);
//
//       if (duration )


   }

}
