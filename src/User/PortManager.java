package User;

class PortManager extends User {
    private String portName;
    public PortManager(String username, String password, String portName) {
        super(username, password);
        this.portName = portName;
    }
}
