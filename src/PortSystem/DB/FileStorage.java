package PortSystem.DB;

import java.io.*;

public class FileStorage {
    private static final String FILE_NAME = "db.obj";

    private static String getFileDir() {
        return FILE_NAME;
    }

    static boolean fileExists() {
        return new File(getFileDir()).exists();
    }

    static MasterDatabase read() {
        try (FileInputStream fi = new FileInputStream(getFileDir());
             ObjectInputStream oi = new ObjectInputStream(fi)) {

            // Read object
            return (MasterDatabase) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading database from file", e);
        }
    }

    static void write(MasterDatabase db) {
        try (FileOutputStream f = new FileOutputStream(getFileDir());
             ObjectOutputStream o = new ObjectOutputStream(f)) {

            // Write object
            o.writeObject(db);
        } catch (IOException e) {
            throw new RuntimeException("Error writing database to file", e);
        }
    }

}
