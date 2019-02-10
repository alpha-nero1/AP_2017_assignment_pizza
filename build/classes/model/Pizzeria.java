package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Pizzeria {
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private Kitchen kitchen = new Kitchen();

    public Pizzeria() {
        try {
            // Preload some customers. Don't remove this!
            Customer kelly = addCustomer("95432123", "Kelly Lee");
            Customer tim = addCustomer("92345678", "Tim Williams");
            Pizza pizza = kelly.createPizza();
            pizza.add(kitchen.getIngredients().get(0)); // Thin crust
            pizza.add(kitchen.getIngredients().get(2)); // Tomato sauce
            pizza.add(kitchen.getIngredients().get(4)); // Capsicum topping
            pizza.add(kitchen.getIngredients().get(6)); // Jalapenos topping
            pizza.order();
            kelly.submitOrder();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer addCustomer(String phone, String name) throws Exception {
        if (customer(phone) != null) {
            // throw exception if found.
            throw new Exception("An existing customer has that phone number"); 
        }
        else {
            Customer customer = new Customer(kitchen, phone, name);
            customers.add(customer);
            return customer;
        }
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public final Kitchen getKitchen() {
        return kitchen;
    }

    public final ObservableList<Customer> getCustomers() {
        return customers;
    }

    public Customer customer(String phone) {
        for (Customer customer : customers)
            if (customer.matches(phone))
                return customer;
        return null;
    }
}