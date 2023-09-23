package PortSystem.DB;

import PortSystem.User.User;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

import static PortSystem.Utils.DBUtils.randKey;

public abstract class Database<T extends DatabaseRecord> implements DatabaseOperations<T>, Serializable {
    // Generalized class to store objects (referred to as records) of any type
    // Each record of the database has a unique id
    // IMPORTANT: Objects to be stored in the database MUST implement the DatabaseRecord interface

    private final String ID_HEADER; // The first character(s) of the id
    private static final int KEY_LENGTH = 6;  // Length of the random generated key (excluding the ID_HEADER and separator)
    protected HashMap<String, T> data; // This is where the records are stored. The HashMap maps the id to the object reference.
    protected MasterDatabase mdb;

    public Database(MasterDatabase mdb, String idHeader) {
        this.mdb = mdb;
        this.data = new HashMap<>();
        ID_HEADER = idHeader;
    }

    private String generateRandomId() {
        return ID_HEADER + randKey(KEY_LENGTH);
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

    @Override
    public T add(T item) {
        // Generates a valid id, attach it to the object, then add a new record
        // Returns the newly created record
        String id = generateValidId();
        item.setId(id);
        data.put(id, item);
        mdb.save();
        return item;
    }

    @Override
    public T find(String id) {
        // Finds the record by id
        // Returns the found record, or null if no record found
        T res = data.get(id);
        if (res == null) {
            DisplayUtils.printErrorMessage("No record found for ID: " + id);
        }
        return res;
    }

    @Override
    public T delete(String id) {
        // Deletes the record of the given id.
        // Returns the deleted record, or null if no record found.
        T record = find(id);
        if (record == null) return null;
        record = data.remove(id);
        DisplayUtils.printSystemMessage("Deleted record: " + record + ConsoleColors.RESET);
        mdb.save();
        return record;
    }

    @Override
    public boolean exists(String id) {
        return data.containsKey(id);
    }

    @Override
    public abstract void showInfo(String id);

    @Override
    public T createRecord(T item) {
        // Adds record to DB and returns it
        // Returns null if something went wrong
        // Some objects needs further processing before adding to DB, override this method accordingly (see child classes for example)
        add(item);
        DisplayUtils.printSystemMessage("Created record: " + item);
        return item;
    }

    @Override
    public T updateRecord(String id) {
        // Start update sequence
        // Meant to be overridden for further processing (see child class for example)
        // REMEMBER TO SAVE MASTER DB WHEN FINISHED
        T record = find(id);
        if (record == null) return null;

        DisplayUtils.printSystemMessage("Updating record: " + record );
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "**WARNING**: Manually updating records could lead to data inaccuracy" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "Type '.' to keep the same value for each field" + ConsoleColors.RESET);
        return record;
    }

    public void display() {
        data.forEach((key, val) -> {
            System.out.println(val);
        });
    }

}
