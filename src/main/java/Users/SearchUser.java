package Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class SearchUser extends User{

    public User search (String userID, List<User> users){

        for (User user : users) {
            if (user.getUserId().equals(userID)) {
                return user;
            }
        }
        System.out.println("The user with ID number '" + userID + "' does not exist.");
        System.exit(0);
        return null;

    }


    }




