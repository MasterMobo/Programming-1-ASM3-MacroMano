public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}


class SystemAdmin extends User {
    public SystemAdmin(String username, String password) {
        super(username, password);
    }

}


class PortManager extends User {
    private String portName;
    public PortManager(String username, String password, String portName) {
        super(username, password);
        this.portName = portName;
    }
}