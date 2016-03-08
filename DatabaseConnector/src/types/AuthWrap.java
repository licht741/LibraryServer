package types;


public class AuthWrap {
    public int result;
    public int userID;
    public String userName;

    public AuthWrap(int result, int userID, String userName) {
        this.result = result;
        this.userID = userID;
        this.userName = userName;
    }
}
