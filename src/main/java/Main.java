import Users.SearchUser;
import Users.TimeCount;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {



        System.out.println("Enter the user ID number: ");
        Scanner insert = new Scanner(System.in);
        String userId = insert.next();
        LocalDateTime timestamp1 = LocalDateTime.now();

        SearchUser s = new SearchUser();
        String vardas = s.search(userId, users).getName();
        String pavarde = s.search(userId, users).getSurname();


        String timeOfLog = timestamp1.getHour() + "h " + timestamp1.getMinute() + "min " + timestamp1.getSecond() + "s";
        System.out.println("The user is " + vardas + " " + pavarde + ". " + "Time of log: " + timeOfLog + ".");

        TimeCount time = new TimeCount();
        time.arLaiku(timestamp1);

        }
    }






