package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.Pizzeria;
import model.Ingredient;

public class ReportController extends Controller<Pizzeria> {
    
    @FXML private TableView<Ingredient> reportTableView;
    @FXML private Text totalText;
    @FXML private TableColumn<Ingredient, String> ingredientColumn;
    @FXML private TableColumn<Ingredient, String> priceColumn;
    @FXML private TableColumn<Ingredient, String> soldColumn;
    @FXML private TableColumn<Ingredient, String> incomeColumn;
    
    @FXML public void initialize() {
        // set the items for the entire report table.
        reportTableView.setItems((ObservableList<Ingredient>) model.getKitchen().getIngredients());  
        
        // set cell value factory for each column
        ingredientColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty()); 
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString("$%.2f"));   
        soldColumn.setCellValueFactory(cellData -> cellData.getValue().soldProperty().asString());
        incomeColumn.setCellValueFactory(cellData -> (cellData.getValue().totalOfSold()).asString("$%.2f"));
        
        // bind the total text.
        totalText.textProperty().bind(model.getKitchen().totalIncomeProperty().asString("$%.2f"));
    }
    
    @FXML private void handleCancelPressed(ActionEvent a) {
        stage.close();
    }
}