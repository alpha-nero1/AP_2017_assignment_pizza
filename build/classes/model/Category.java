package model;

import javafx.beans.property.SimpleStringProperty;

public class Category {
    private SimpleStringProperty name = new SimpleStringProperty();
    private int min;
    private int max;

    public Category(String name, int min, int max) {
        this.name.set(name);
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name.get();
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getMinName() {
        return min + " " + name(min);
    }

    public String name(int n) {
        return n == 1 ? name.get() : (name.get() + "s");
    }

    @Override
    public String toString() {
        return name.get();
    }
}
