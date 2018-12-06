package Users;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCount {

    public void  arLaiku(LocalDateTime timestamp1) {

        LocalDateTime pamainosPradzia = LocalDateTime.of(2018, 12, 6, 9, 0, 0);

        Duration duration = Duration.between(pamainosPradzia, timestamp1);

        if (duration.toMinutes() > 0) {
            System.out.println("Is late for " + duration.toMinutes() + " min");
        }


    }
}