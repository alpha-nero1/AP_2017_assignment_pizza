package model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class Customer {
    private Kitchen kitchen;

    private ObservableList<Pizza> ordered = FXCollections.observableArrayList();
    private ObservableList<Pizza> order = FXCollections.observableArrayList();
    
    public SimpleStringProperty nameProperty = new SimpleStringProperty();
    public SimpleStringProperty phoneProperty = new SimpleStringProperty();
    public SimpleDoubleProperty orderPrice = new SimpleDoubleProperty();
 
    public Customer(Kitchen kitchen, String phone, String name) {
        this.kitchen = kitchen;
        this.phoneProperty.set(phone);
        this.nameProperty.set(name);
        order.addListener((Observable obs) -> { // listener changing price
            orderPrice.set(getOrderPrice());
        });
    }
    
    public final SimpleStringProperty nameProperty() { return nameProperty; }
    public final SimpleStringProperty phoneProperty() { return phoneProperty; }
    public final SimpleDoubleProperty orderPrice() { return orderPrice; } // incremented whenever order method called.
    
    public void cancelOrder() {
        order.clear();
    }

    public void submitOrder() {
        if (order.size() > 0) {
            for (Pizza pizza : order) {
                pizza.sell();
                if (ordered.contains(pizza))
                    bubble(pizza);
                else
                    ordered.add(pizza);
            }
            order.clear();
            kitchen.setTotalIncome(); // set our total income property
        }
    }

    public double getOrderPrice() {
        double sum = 0.0;
        for (Pizza pizza : order)
            sum += pizza.getPrice();
        return sum;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public ObservableList<Pizza> getOrdered() {
        return ordered;
    }

    public ObservableList<Pizza> getOrder() {
        return order;
    }

    public String getPhone() {
        return phoneProperty.get();
    }

    public String getName() {
        return nameProperty.get();
    }

    public Pizza createPizza() {
        return new Pizza(this);
    }

    private void bubble(Pizza pizza) {
        for (int i = ordered.indexOf(pizza);
                i > 0 && ordered.get(i).getSold() > ordered.get(i-1).getSold();
                i--) {
            Pizza temp = ordered.get(i - 1);
            ordered.set(i - 1, ordered.get(i));
            ordered.set(i, temp);
        }
    }

    // This method is package protected so that it can't be used outside this package.
    // If you want to order a pizza, you should instead use pizza.order();
    void order(Pizza pizza) {
        order.add(pizza);
    }

    public boolean matches(String phone) {
        return this.getPhone().equals(phone);
    }

    @Override
    public String toString() {
        return getName() + ": " + getPhone();
    }
}