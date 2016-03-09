package types;

 /*
  *  Базовый класс для всех объектов из базы данных
  *
  */
public class DatabaseObject {
    private int id;

    public DatabaseObject() {}

    public DatabaseObject(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id; }

    @Override
    public String toString() {
        return String.valueOf(id);
    }


}
