package Users;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeCount {

    LocalDateTime pamainosPradzia = LocalDateTime.of(2018, 12, 6, 16, 0, 0);


    public void  arLaiku(LocalDateTime timestamp1) {
        Duration duration = Duration.between(pamainosPradzia, timestamp1);
        if (duration.toMinutes() > 0) System.out.println("Is late for " + duration.toMinutes() + " min.");
        if (duration.toMinutes() < 0) System.out.println("Logged in too early. " + duration.toMinutes()*(-1) + " min until the shift begins." );
        if (duration.toMinutes() == 0) System.out.println("Right on time.");
    }
}