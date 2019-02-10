package model;

import java.text.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ingredient {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private Category category;
    private SimpleIntegerProperty sold = new SimpleIntegerProperty();
    private SimpleDoubleProperty totalOfSold = new SimpleDoubleProperty();

    public Ingredient(String name, double price, Category category) {
        this.name.set(name);
        this.price.set(price);
        this.category = category;
        sold.set(0);
    }
    
    public final SimpleStringProperty nameProperty() { return name; }
    public final SimpleDoubleProperty priceProperty() { return price; }
    public final SimpleIntegerProperty soldProperty() { return sold; }
    public final SimpleDoubleProperty totalOfSold() { return totalOfSold; }

    public void sell() {
        sold.set(sold.intValue() + 1);
        setIncome();
    }

    public boolean in(Category category) {
        return this.category == category;
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getSold() {
        return sold.get();
    }

    private void setIncome() {
        totalOfSold.set(sold.get() * price.get());
    }
    
    public double getIncome() {
        return sold.get() * price.get();
    }

    public Category getCategory() {
        return category;
    }
    
    // for sorting in create pizza menu.
    public String custToString() {
        return category + " " + name.get();
    }

    @Override
    public String toString() {
        return name.get() + " " + category;
    }

    private String formatted(double n) {
        return new DecimalFormat("###,##0.00").format(n);
    }
}
