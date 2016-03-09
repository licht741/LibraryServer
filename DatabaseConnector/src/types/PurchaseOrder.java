package types;

import java.util.Date;

/*
 * Класс, описывающий приобретение библиотекой книг
 * Рассматривается только получение книг - предполагается, что оплата производится сторонней организацией
 */
public class PurchaseOrder extends DatabaseObject {
    Book book;
    String shop;
    int count;
    Date date;

    public PurchaseOrder() {}

    public PurchaseOrder(Book book, String shop, int count, Date date) {
        this.book = book;
        this.shop = shop;
        this.count = count;
        this.date = date;
    }

    public PurchaseOrder(int id, Book book, String shop, int count, Date date) {
        super(id);
        this.book = book;
        this.shop = shop;
        this.count = count;
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
