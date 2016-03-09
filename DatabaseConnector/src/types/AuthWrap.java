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
    public int result;
    public int userID;
    public String userName;

    public AuthWrap(int result, int userID, String userName) {
        this.result = result;
        this.userID = userID;
        this.userName = userName;
    }
    public AuthWrap() {}
}
