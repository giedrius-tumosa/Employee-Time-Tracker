package DataProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventData {




//Demo data for logic of AttendanceChecker

    public  Map createEvent (){  //User userID, LocalDateTime time

        Map<String, List<UserEvent>> events = new HashMap<>();
        events.put("0001", new ArrayList<>());
        events.get("0001").add(new UserEvent(LocalDateTime.of(2018, 12, 11, 9, 0, 0)));
        events.get("0001").add(new UserEvent(LocalDateTime.of(2018, 12, 11, 17, 0, 0)));
        events.get("0001").add(new UserEvent(LocalDateTime.of(2018, 12, 11, 9, 0, 0)));


        return events;

        }


}

