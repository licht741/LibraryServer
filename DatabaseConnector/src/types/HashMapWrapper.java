package types;

import java.util.HashMap;

/**
 * Created by Licht on 08.03.2016.
 */
public class HashMapWrapper {

    private HashMap<Book, Integer> basketMap;

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