package DataProvider;

import DataProvider.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserLib {

    private List<User> users = new ArrayList<>();

    {

        users.add(new User("Jonas", "Petrauskas", "0001"));
        users.add(new User("Petras", "Naujalis", "0002"));
        users.add(new User("Klementina", "Lasauskiene", "0003"));
        users.add(new User("Elze", "Jankauskaite", "0004"));
        users.add(new User("Saule", "Uzun", "0005"));
        users.add(new User("Haris", "Poteris", "0006"));
        users.add(new User("Steve", "Rodgers", "0007"));

    }

    public static User search(String userID, List<User> users) {
        for (User user : users) {
            if (user.getUserId().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public List<User> users () {
        return users;



}}