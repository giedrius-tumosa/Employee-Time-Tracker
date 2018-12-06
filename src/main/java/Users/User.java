package Users;

import java.sql.Time;

public class User {

    private String name;
    private String surname;
    private String userId;




    public User(String name, String surname, String userId) {

        this.name = name;
        this.surname = surname;
        this.userId = userId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
