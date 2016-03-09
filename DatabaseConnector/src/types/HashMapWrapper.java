package types;

import java.util.HashMap;

/*
 * Обёртка над типом HashMap<Book, Integer>
 * Используется для передачи информации о наличии книг в библиотеке
 */
public class HashMapWrapper {

    private HashMap<Book, Integer> basketMap;
    HashMapWrapper() {}

    public HashMapWrapper(HashMap<Book, Integer> basketMap) {
        this.setBasketMap(basketMap);
    }

    public HashMap<Book, Integer> getBasketMap() {
        return basketMap;
    }

    public void setBasketMap(HashMap<Book, Integer> basketMap) {
        this.basketMap = basketMap;
    }
}