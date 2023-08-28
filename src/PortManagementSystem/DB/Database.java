package PortManagementSystem.DB;

import java.util.HashMap;

import static PortManagementSystem.Utils.DBUtils.randKey;

public class Database<T extends DatabaseRecord> {
    protected static final int KEY_LENGTH = 6;
    public HashMap<String, T> data;

    private final String ID_HEADER;

    public Database(String idHeader) {
        this.data = new HashMap<>();
        ID_HEADER = idHeader;
    }

    private String generateRandId() {
        return ID_HEADER + "-" + randKey(6);
    }
    public String generateValidId() {
        String id = generateRandId();
        while (!validId(id)) {
            id = generateRandId();
        }
        return id;
    };


    public boolean validId(String id) {
        return data.get(id) == null;
    }

    public T createRecord(T item) {
        String id = generateValidId();
        item.setId(id);
        data.put(id, item);
        System.out.println(id);
        return item;
    }

    public T readRecord(String id) {
        return data.get(id);
    }

//     TODO: do not update id
    public T updateRecord(String id, T item) {
        data.put(id, item);
        return readRecord(id);
    }

    public T deleteRecord(String id) {
        return data.remove(id);
    }

}
