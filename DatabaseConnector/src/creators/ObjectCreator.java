package creators;

import types.*;

import java.util.Date;

    /*
     * Создание объектов и задание их параметров
     * Для корректной работы SOAP используются конструкторы без параметров
     * Реализуется паттерн "Фабричный метод"
     */
public class ObjectCreator {
    public static Book createBook(int id, String title, String author, String pubHouse, int year) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishHouse(pubHouse);
        book.setYear(year);
        return book;
    }

    public static Book createBook(String title, String author, String pubHouse, int year) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishHouse(pubHouse);
        book.setYear(year);
        return book;
    }

    public static User createUser(int id, String login, String name, boolean isEnable) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setName(name);
        user.setEnable(isEnable);
        return user;
    }

    public static UserOrder createUserOrder(Book book, int count) {
        UserOrder userOrder = new UserOrder();
        userOrder.setBook(book);
        userOrder.setCount(count);
        return userOrder;
    }

    public static PurchaseOrder createPurchaseOrder(Book book, String shop, int bCount, Date date) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setBook(book);
        purchaseOrder.setShop(shop);
        purchaseOrder.setCount(bCount);
        purchaseOrder.setDate(date);
        return purchaseOrder;
    }

    public static Operation createOperation(String userName, Book book, Date recDate, Date deadline) {
        Operation operation = new Operation();
        operation.setUser(userName);
        operation.setBook(book);
        operation.setReceivedDate(recDate);
        operation.setDeadline(deadline);
        return operation;
    }

    public static Operation createOperation(int operationID, String userName, Book book, Date recDate, Date deadline) {
        Operation operation = new Operation();
        operation.setId(operationID);
        operation.setUser(userName);
        operation.setBook(book);
        operation.setReceivedDate(recDate);
        operation.setDeadline(deadline);
        return operation;
    }

    public static Store createStore(int storeID, String storeName, String storePhone, String storeAddress) {
        Store store = new Store();

        store.setId(storeID);
        store.setName(storeName);
        store.setPhone(storePhone);
        store.setAddress(storeAddress);

        return store;
    }
}