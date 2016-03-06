package types;

public class UserOrder extends DatabaseObject {
    int bookId;
    int count;
    boolean isClosed;

    public UserOrder(int bookId, int count, boolean isClosed) {
        this.bookId = bookId;
        this.count = count;
        this.isClosed = isClosed;
    }

    public UserOrder(int id, int bookId, int count, boolean isClosed) {
        super(id);
        this.bookId = bookId;
        this.count = count;
        this.isClosed = isClosed;
    }

    public int getBookId() {
        return bookId;
    }

    public int getCount() {
        return count;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
