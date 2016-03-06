package types;

import java.util.Date;

public class PurchaseOrder extends DatabaseObject {
    int bookId;
    int shopId;
    int count;
    Date date;

    public PurchaseOrder(int bookId, int shopId, int count, Date date) {
        this.bookId = bookId;
        this.shopId = shopId;
        this.count = count;
        this.date = date;
    }

    public PurchaseOrder(int id, int bookId, int shopId, int count, Date date) {
        super(id);
        this.bookId = bookId;
        this.shopId = shopId;
        this.count = count;
        this.date = date;
    }

    public int getBookId() {
        return bookId;
    }

    public int getShopId() {
        return shopId;
    }

    public int getCount() {
        return count;
    }

    public Date getDate() {
        return date;
    }
}
