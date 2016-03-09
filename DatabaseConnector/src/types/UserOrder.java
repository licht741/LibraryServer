package types;

/*
 * Заказы пользователей библиотеки
 */
public class UserOrder extends DatabaseObject {
    Book book;
    int count;

    public UserOrder() {}

    /*
     * @param book Заказываемая книга должна иметься в базе данных библиотеки
     */
    public UserOrder(Book book, int count) {
        this.book = book;
        this.count = count;
    }

    public UserOrder(int id, Book book, int count) {
        super(id);
        this.book = book;
        this.count = count;
    }

    public Book getBook() {
        return book;
    }

    public int getCount() {
        return count;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
