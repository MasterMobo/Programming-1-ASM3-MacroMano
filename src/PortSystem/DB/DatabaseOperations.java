package PortSystem.DB;

public interface DatabaseOperations<T> {
    T add(T item);
    T find(String id);
    T delete(String id);
    T createRecord(T item);
    T updateRecord(String id);
    boolean exists(String id);
    String showInfo(String id);
}
