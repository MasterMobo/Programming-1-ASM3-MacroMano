import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;

public class Main {
    public static void main(String[] args) {
        MasterDatabase db = MasterDatabase.initDB();
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        Port p2 = new Port("HN", 122.2, 12.3, 647, true);

        db.ports.add(p1);
        db.ports.add(p2);
        System.out.println(db.ports.data.size());
    }
}