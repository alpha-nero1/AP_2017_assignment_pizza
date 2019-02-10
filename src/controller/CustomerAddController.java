package controller;
import model.Pizzeria;
import au.edu.uts.ap.javafx.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CustomerAddController extends Controller<Pizzeria> {
    
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private Text errorText;
    
    private SimpleStringProperty errorMsg = new SimpleStringProperty(" ");
    
    @FXML public void initialize() throws Exception {
        
        try {
            nameField.textProperty().addListener((obs, oldVal, newVal) -> {
                addButton.setDisable(!isValidCustomerEntry());
            });
            
            phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
                addButton.setDisable(!isValidCustomerEntry());
            });
        } catch (Exception e) {
                System.out.println(e);
        }
        errorText.textProperty().bind(errorMsg);
    }
    
    private String getName() { return nameField.getText(); }
    private String getPhone() { return phoneField.getText(); }
    
    private boolean isValidCustomerEntry() {
        // there is text in both trays.
        return (getName().length() > 0 && getPhone().length() > 0);
    }
    
    @FXML private void handleAddPressed(ActionEvent a) throws Exception { // we will always have valid values.
        try {
            model.addCustomer(getPhone(), getName());
            stage.close();
        } catch (Exception e) {
            System.out.println("Customer already exists, displaying error message.");
            // customer exists, display message.
            errorMsg.set("Customer already exists");
        } 
    }
    
    @FXML private void handleCancelPressed(ActionEvent a) {
        stage.close();  // just close the screen. 
    }
    
}
