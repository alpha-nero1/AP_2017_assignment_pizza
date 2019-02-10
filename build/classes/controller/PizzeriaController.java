package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Pizzeria;
import model.Customer;
import javafx.stage.*;

public class PizzeriaController extends Controller<Pizzeria> {
    
    @FXML private Button addCustomerButton;
    @FXML private Button serveCustomerButton;
    @FXML private Button viewReportButton;
    @FXML private ListView<Customer> customerListView;

    @FXML public void initialize() {
        // set customers.
        customerListView.setItems(model.getCustomers());
        
        // add listener for selection.
        customerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            serveCustomerButton.setDisable(getChosenCustomer() == null);
        });
    }
    
    private Customer getChosenCustomer() {
        return customerListView.getSelectionModel().getSelectedItem();
    }
    
    @FXML public void handleAddCustomer(ActionEvent a) throws Exception {
        ViewLoader.showStage(model , "/view/customer_add.fxml", "Add customer", new Stage());
    }
    
    @FXML public void handleServeCustomer(ActionEvent a) throws Exception {
        ViewLoader.showStage(getChosenCustomer() , "/view/serve.fxml", "Serve customer", new Stage());
    }
    
    @FXML public void handleReport(ActionEvent a) throws Exception {
        ViewLoader.showStage(model , "/view/report.fxml", "Income report", new Stage());
    }
}