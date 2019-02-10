package model;

import java.util.LinkedList;
import java.util.Iterator;
import java.text.*;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pizza {
    private Customer customer;
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    private int sold;
    private SimpleDoubleProperty price = new SimpleDoubleProperty();
    private SimpleStringProperty statusString = new SimpleStringProperty();

    public Pizza(Customer customer) {
        this.customer = customer;
        price.set(0);
        statusString.set(getStatusString());
        
        ingredients.addListener((Observable obs) -> {
            statusString.set(getStatusString());
        });
    }
    
    public final SimpleDoubleProperty priceProperty() { return price; }
    public final SimpleStringProperty statusString() { return statusString; }

    public final ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final Kitchen getKitchen() {
        return customer.getKitchen();
    }

    public boolean add(Ingredient ingredient) {
        price.set(price.doubleValue() + ingredient.getPrice());
        return ingredients.add(ingredient);
    }

    public boolean remove(Ingredient ingredient) {
        price.set(price.doubleValue() - ingredient.getPrice());
        return ingredients.remove(ingredient);
    }

    public void order() {
        customer.order(this);
    }

    public int getSold() {
        return sold;
    }

    public final String getStatusString() {
        LinkedList<Category> incompleteCategories = incompleteCategories();
        if (incompleteCategories().isEmpty())
            return "";
        else {
            String s = "Need ";
            for (int i = 0; i < incompleteCategories.size(); i++) {
                Category category = incompleteCategories.get(i);
                int min = category.getMin();
                int count = ingredientCount(category);
                int needed = min - count;
                if (i > 0)
                    s += (i < incompleteCategories.size() - 1) ? ", " : " and ";
                s += needed + " ";
                if (count > 0)
                    s += "more ";
                s += category.name(needed);
            }
            return s;
        }
    }

    private LinkedList<Category> incompleteCategories() {
        LinkedList<Category> matches = new LinkedList<Category>();
        for (Category category : getKitchen().getCategories())
            if (!meetsMinimumRequirement(category))
                matches.add(category);
        return matches;
    }

    public double getPrice() {
        double price = 0.0;
        for (Ingredient ingredient : ingredients)
            price += ingredient.getPrice();
        return price;
    }

    public boolean isFull(Category category) {
        return ingredientCount(category) >= category.getMax();
    }

    public boolean meetsMinimumRequirement(Category category) {
        return ingredientCount(category) >= category.getMin();
    }

    public boolean meetsMinimumRequirements() {
        for (Category category : getKitchen().getCategories())
            if (!meetsMinimumRequirement(category))
                return false;
        return true;
    }

    public int ingredientCount(Category category) {
        int count = 0;
        for (Ingredient ingredient : ingredients)
            if (ingredient.in(category))
                count++;
        return count;
    }

    public boolean canAdd(Ingredient ingredient) {
        return !contains(ingredient) && !isFull(ingredient.getCategory());
    }

    public boolean contains(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    private String ingredientName(Category category) {
        LinkedList<Ingredient> ingredients = ingredients(category);
        return ingredients.size() == 0 ? "no " + category : ingredients.getFirst().toString();
    }

    private String crust() {
        return ingredientName(Kitchen.CRUST);
    }

    private String sauce() {
        return ingredientName(Kitchen.SAUCE);
    }

    private String toppings() {
        LinkedList<Ingredient> toppings = ingredients(Kitchen.TOPPING);
        if (toppings.size() == 0)
            return "no " + Kitchen.TOPPING.name(0);
        else {
            Iterator<Ingredient> it = toppings.iterator();
            String s = it.next().getName();
            while (it.hasNext())
                s += ", " + it.next().getName();
            return s;
        }
    }

    private LinkedList<Ingredient> ingredients(Category category) {
        LinkedList<Ingredient> matchingIngredients = new LinkedList<Ingredient>();
        for (Ingredient ingredient : ingredients)
            if (ingredient.in(category))
                matchingIngredients.add(ingredient);
        return matchingIngredients;
    }

    // This method is package protected so that it can't be used outside of this package.
    // If you want to sell a pizza, you should instead use
    // 1. pizza.order();
    // 2. customer.submitOrder();
    void sell() {
        sold++;
        for (Ingredient ingredient : ingredients)
            ingredient.sell();
    }

    @Override
    public String toString() {
        return crust() + " pizza with " + toppings() + " and " + sauce() + ": $" + formatted(getPrice());
    }

    private String formatted(double n) {
        return new DecimalFormat("###,##0.00").format(n);
    }
}
