package types;

/*
 * Объекты, содержащие контактную информацию о поставщиках книг
 */
public class Store extends DatabaseObject {
    String name;
    String phone;
    String address;

    public Store() {}

    public Store(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Store(int id, String name, String phone, String address) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getAddress() { return address; }

    public String getName() { return name; }

    public String getPhone() {
        return phone;
    }
}
