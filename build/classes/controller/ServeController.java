package controller;
import model.Customer;
import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Pizza;
import javafx.scene.control.Button;

public class ServeController extends Controller<Customer> {
    @FXML private Text nameText;
    @FXML private Text phoneText;
    @FXML private Text totalText;
    @FXML private Button submitButton;
    @FXML private Button selectButton;
    @FXML private ComboBox<Pizza> pastPizzasComboBox;
    @FXML private ListView<Pizza> currentOrderListView;
    
    @FXML public void initialize() throws Exception {
        
        // Set items for combo box and list veiw.
        currentOrderListView.setItems(model.getOrder());
        pastPizzasComboBox.setItems(model.getOrdered());
        
        // Add listeners onto combo and list view.
        currentOrderListView.getItems().addListener((Observable ob) -> submitButton.setDisable(orderListIsEmpty()));
        pastPizzasComboBox.valueProperty().addListener((Observable ob) -> {
            selectButton.setDisable(getPizzaComboBoxSelection() == null);
        });

        // Bind text properties.
        nameText.textProperty().bind(model.nameProperty());
        phoneText.textProperty().bind(model.phoneProperty());  
        totalText.textProperty().bind(model.orderPrice().asString("$%.2f"));
    }
    
    private Pizza getPizzaComboBoxSelection() {
        return pastPizzasComboBox.getSelectionModel().getSelectedItem();
    }
    
    private boolean orderListIsEmpty() {
        return (model.getOrder().size() < 1);
    }
    
    @FXML private void handleSelectPizzaPressed(ActionEvent a) {
        getPizzaComboBoxSelection().order();
        pastPizzasComboBox.getSelectionModel().clearSelection();
    }
    
    @FXML private void handleCreatePizzaPressed(ActionEvent a) throws Exception { // create
        try {
            ViewLoader.showStage(model, "/view/pizza.fxml" , "Create pizza", new Stage());
        } catch (Exception e) {
            System.out.print("** Info: Failure to load pizza.fxml");
        }
    }
    
    @FXML private void handleCancelPressed(ActionEvent a) { // cancel
        model.cancelOrder();
        stage.close();
    }
    
    @FXML private void handleSubmitPressed(ActionEvent a) { // submit
        model.submitOrder();
        stage.close();
    }   
}