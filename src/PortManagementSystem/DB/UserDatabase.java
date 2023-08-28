package PortManagementSystem.DB;

import PortManagementSystem.User.User;

public class UserDatabase extends Database<User>{
    public UserDatabase() {
        super("");
    }

    @Override
    public User createRecord(User user) {
        if (data.containsKey(user.getUsername())) {
            System.out.println("Username already exists");
            return null;
        }
        data.put(user.getUsername(), user);
        return user;
    }


}
