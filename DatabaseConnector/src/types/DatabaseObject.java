package types;


public class DatabaseObject {
    private int id;

    public DatabaseObject() {}

    public DatabaseObject(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }


}
