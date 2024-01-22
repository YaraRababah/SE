
package javaapplication15;
 import java.util.ArrayList;
 import java.util.List;

public class BookInventory {
    private static final BookInventory instance = new BookInventory();
    private List<InventoryObserver> observers = new ArrayList<>();
    private BookInventory() {
    }
    public static BookInventory getInstance() {
        return instance;
    }
    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
    }
    public void updateObservers() {
        for (InventoryObserver observer : observers) {
            observer.update();
        }
    }
    }  

