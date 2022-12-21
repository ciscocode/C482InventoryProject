package montenegro.c482inventoryproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This class creates an Inventory System application which can keep track of parts and products.*/
public class MainApplication extends Application {
    /** This method loads the stage for the Main View of our Inventory System.*/
    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root,1303, 494));
        mainStage.show();
    }
    /** This is the main method. This launches our program*/
    public static void main(String[] args) {
       launch(args);
    }
}