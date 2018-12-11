package DataProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventData {


    Map<String, List<UserEvent>> events = new HashMap<>();

    public void createEvent (User userID, LocalDateTime time){
        events.put(userID.getUserId(), new ArrayList<>()); // TODO - paziureti, kaip ideti i mapsus lista
    }


    public Map<String, List<UserEvent>> getEvents() {
        return events;


}

}