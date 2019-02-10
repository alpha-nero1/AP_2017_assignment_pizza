import javafx.stage.*;
import au.edu.uts.ap.javafx.ViewLoader;
import model.Pizzeria;
import javafx.application.Application;

public class PizzeriaApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewLoader.showStage(new Pizzeria(), "/view/pizzeria.fxml", "Main menu", stage);
    }
}
