import java.time.LocalDateTime;
import java.util.*;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("Jonas", "Petrauskas", "0001" ));
        users.add(new User("Petras", "Naujalis", "0002"));
        users.add(new User("Klementina", "Lasauskiene", "0003"));
        users.add(new User("Elze", "Jankauskaite", "0004"));
        users.add(new User("Saule", "Uzun", "0005"));
        users.add(new User("Haris", "Poteris", "0006"));
        users.add(new User("Steve", "Rodgers", "0007"));


        System.out.println("Enter the user ID number: ");
        Scanner insert = new Scanner(System.in);
        String userId = insert.next();
        LocalDateTime timestamp1 = LocalDateTime.now();

        SearchUser s = new SearchUser();
        String vardas = s.search(userId, users).getName();
        String pavarde = s.search(userId, users).getSurname();
        String timeOfLog = timestamp1.getHour() + "h " + timestamp1.getMinute() + "min " + timestamp1.getSecond() + "s";

        System.out.println("The user is " + vardas + " " + pavarde + ". " + "Time of log: " + timeOfLog);

        }
    }






