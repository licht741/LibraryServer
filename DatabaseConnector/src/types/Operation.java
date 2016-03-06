package types;

import java.util.Date;

public class Operation extends DatabaseObject {
    int userId;
    int bookId;
    Date receivedDate;
    Date deadline;
    boolean isClosed;

    public Operation() {}

    public Operation(int userId, int bookId, Date receivedDate, Date deadline, boolean isClosed) {
        this.userId = userId;
        this.bookId = bookId;
        this.receivedDate = receivedDate;
        this.deadline = deadline;
        this.isClosed = isClosed;
    }

    public Operation(int id, int userId, int bookId, Date receivedDate, Date deadline, boolean isClosed) {
        super(id);
        this.userId = userId;
        this.bookId = bookId;
        this.receivedDate = receivedDate;
        this.deadline = deadline;
        this.isClosed = isClosed;
    }
}
