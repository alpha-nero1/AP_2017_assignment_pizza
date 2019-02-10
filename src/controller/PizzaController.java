package controller;

import au.edu.uts.ap.javafx.Controller;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.Ingredient;
import model.Customer;
import model.Pizza;
import view.PizzaView;

public class PizzaController extends Controller<Customer> {
    
    @FXML private Text changingTextPrompt;
    @FXML private Text totalText;
    @FXML private Button itemBackButton;
    @FXML private Button itemForwardButton;
    @FXML private Button createButton;
    @FXML private PizzaView pizzaView;
    @FXML private ListView<Ingredient> availableListView;
    @FXML private ListView<Ingredient> selectedListView;
    
    private ObservableList<Ingredient> availableIngredients = FXCollections.observableArrayList();
    private Pizza pizza; // init new pizza.
    
    // alphabetical sorting.
    Comparator<Ingredient> opComparator = (Ingredient o1, Ingredient o2) -> 
        o1.custToString().compareTo(o2.custToString());
     
    @FXML public void initialize() {
        pizza = new Pizza(model);
        pizzaView.setPizza(pizza);
        itemBackButton.setText("<-");
        availableIngredients.setAll(model.getKitchen().getIngredients().sorted(opComparator));
        
        availableListView.setItems(availableIngredients.sorted(opComparator));
        selectedListView.setItems(pizza.getIngredients().sorted(opComparator));
        
        //  bind statusString.        
        changingTextPrompt.textProperty().bind(pizza.statusString());
        
        //  bind total price.
        totalText.textProperty().bind(pizza.priceProperty().asString("$%.2f"));
        
        // listener on the error text.
        changingTextPrompt.textProperty().addListener((ob, newVal, oldVal) -> {
            createButton.setDisable(!isValidPizza());
        });
        
        // set button observers
        availableListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            itemForwardButton.setDisable(isValidIngredient());
        });
        
        selectedListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            itemBackButton.setDisable(getCurrentSelectedSelected() == null);
        });
    }
    
    private Ingredient getCurrentAvailableSelected() {
        return availableListView.getSelectionModel().getSelectedItem();
    }
    
    private Ingredient getCurrentSelectedSelected() {
        return selectedListView.getSelectionModel().getSelectedItem();
    }
    
    private boolean isValidPizza() {
        return changingTextPrompt.textProperty().getValue().isEmpty();
    }
    
    private boolean isValidIngredient() {
        // test if category is full and view is selected
        if (getCurrentAvailableSelected() == null) { return false; }
        return pizza.isFull(getCurrentAvailableSelected().getCategory());
    }
    
    @FXML private void hanldeCancelPressed() {
        stage.close();
    }
    
    @FXML private void hanldeCreatePressed() {
        pizza.order();
        stage.close();
    }
    
    // sort each individual list.
    private void sort() {
        try {
        FXCollections.sort(availableListView.getItems(), opComparator);
        FXCollections.sort(selectedListView.getItems(), opComparator);
        } catch (Exception e) {
            System.out.println(e); // for some reason this is causing exceptions
        } 
    }
    
    @FXML private void itemForwardButtonPressed() { // -> button
        pizza.add(getCurrentAvailableSelected());
        availableIngredients.remove(getCurrentAvailableSelected());
        this.sort();
    }
    
    @FXML private void itemBackButtonPressed() { // <- button
        Ingredient ing = getCurrentSelectedSelected();
        pizza.remove(ing);
        availableIngredients.add(ing);
        this.sort();
    }   
}