package PortManagementSystem.DB;

import PortManagementSystem.User.User;

public class UserDatabase extends Database<User>{
    // Specialized class to store User records
    public UserDatabase() {
        super("");
    }

    @Override
    public User add(User user) {
        // User do not have as randomly generated id, but the id will be username instead
        if (!isValidId(user.getUsername())) {
            System.out.println("Username already exists");
            return null;
        }
        data.put(user.getUsername(), user);
        return user;
    }

}
