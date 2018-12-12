package Users;

import Users.User;

import java.util.ArrayList;
import java.util.List;

public class UserLib {

    private List<User> users = new ArrayList<>();

    {

        users.add(new User("Peter", "Parker", "0001"));
        users.add(new User("Black", "Widow", "0002"));
        users.add(new User("Stephen", "Strange", "0003"));
        users.add(new User("Nick", "Fury", "0004"));
        users.add(new User("Carrol", "Danvers", "0005"));
        users.add(new User("Harry", "Potter", "0006"));
        users.add(new User("Steve", "Rodgers", "0007"));

    }

    public  User search(String userID, List<User> users) {
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