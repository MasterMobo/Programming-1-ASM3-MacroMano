package PortManagementSystem.DB;

import PortManagementSystem.User.User;

public class UserDatabase extends Database<User>{
    public UserDatabase() {
        super("");
    }

    @Override
    public User createRecord(User user) {
        data.put(user.getUsername(), user);
        return user;
    }


}
