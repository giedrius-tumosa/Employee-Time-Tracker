package DataProvider;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceChecker {

    int userEventAmount = 0;
    boolean isEvenNumber = false;


   public void attendance (Map<String, List<UserEvent>> events, LocalDateTime shiftStart, LocalDateTime shiftEnd){

       for (Map.Entry<String, List<UserEvent>> entry : events.entrySet()) {

        userEventAmount = entry.getValue().size();
        isEvenNumber = userEventAmount%2 == 0;

           Duration duration = Duration.between(shiftStart, eventoLaikas); //evento laikas - kaip ji pasiimt? turetu ziureti  i paskutini zmogaus evento laika

        if (isEvenNumber) {

            if (duration.isNegative()) {
                System.out.println("Alert message: " + entry.getKey() + "is late for work.");
            }else{
                System.out.println(entry.getKey() + "will start work in " + duration.toHours() + " h.");
            }

        }

        if (!isEvenNumber) {

            if (duration.toHours() > 8 || duration.isZero()) {
                System.out.println("Alert message: " + entry.getKey() + "is at work for " + duration.toHours() + "h.");
            }else{
                System.out.println(entry.getKey() + "is at work.");
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
