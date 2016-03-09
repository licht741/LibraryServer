package types;

import java.util.Date;


/*
 * Объекты-записи о получении пользователем книг
 */
public class Operation extends DatabaseObject {
    String user;
    Book book;
    Date receivedDate;
    Date deadline;

    public Operation() {}

    public Operation(String user, Book book, Date receivedDate, Date deadline) {
        this.user = user;
        this.book = book;
        this.receivedDate = receivedDate;
        this.deadline = deadline;
    }

    public Operation(int id, String user, Book book, Date receivedDate, Date deadline) {
        super(id);
        this.user = user;
        this.book = book;
        this.receivedDate = receivedDate;
        this.deadline = deadline;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
