package types;


public class User extends DatabaseObject {
    String name;
    String login;
    boolean isEnable;

    public User() {}
    public User(String login, String name, boolean isEnable) {
        this.name = name;
        this.login = login;
        this.isEnable = isEnable;
    }

    public String getName() {
        return name;
    }

    public String getLogin() { return login; }

    public boolean isEnable() {
        return isEnable;
    }

    @Override
    public String toString() {
        return name;
    }
}
