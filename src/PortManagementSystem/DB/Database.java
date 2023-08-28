package PortManagementSystem.DB;

import java.util.HashMap;

import static PortManagementSystem.Utils.DBUtils.randKey;

public class Database<T extends DatabaseRecord> {
    // Generalized class to store objects (referred to as records) of any type
    // Each record of the database has a unique id
    // IMPORTANT: Objects to be stored in the database MUST implement the DatabaseRecord interface

    private final String ID_HEADER; // The first character(s) of the id
    protected static final int KEY_LENGTH = 6;  // Length of the random generated key (excluding the ID_HEADER and separator)
    public HashMap<String, T> data; // This is where the records are stored. The HashMap maps the id to the object reference.

    public Database(String idHeader) {
        this.data = new HashMap<>();
        ID_HEADER = idHeader;
    }

    private String generateRandomId() {
        return ID_HEADER + "-" + randKey(6);
    }

    protected boolean isValidId(String id) {
        // Returns true if id is available (not taken), false otherwise
        return data.get(id) == null;
    }

    // TODO: limit check on the while loop?
    private String generateValidId() {
        // Randomly generates id until it is available
        String id = generateRandomId();
        while (!isValidId(id)) {
            id = generateRandomId();
        }
        return id;

    };

    public T add(T item) {
        // Generates a valid id, attach it to the object, then add a new record
        // Returns the newly created record
        String id = generateValidId();
        item.setId(id);
        data.put(id, item);
        return item;
    }

    public T find(String id) {
        // Finds the record by id
        // Returns the found record, or null if no record found
        return data.get(id);
    }

    public T update(String id, T item) {
        // Updates the record of the given id.
        // Returns the newly updated record, or null if no record found.
        T record = find(id);
        if (record == null) return null;

        record = item;
        record.setId(id);
        return record;
    }

    public T delete(String id) {
        // Deletes the record of the given id.
        // Returns the deleted record, or null if no record found.
        return data.remove(id);
    }

}
