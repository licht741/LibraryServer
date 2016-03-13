package types;

/*
 * Обёртка над результатом авторизации
 * result - код результата авторизации
 *
 *      0 - Авторизация успешно пройдена
 *     -1 - Ошибка авторизации: Не найден пользователь с заданным логином и паролем
 *     -2 - Ошибка авторизации: Допуск пользователя заблокирован
 */
public class AuthWrap {
    int result;
    int userID;
    String userName;


    public AuthWrap() {}

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
