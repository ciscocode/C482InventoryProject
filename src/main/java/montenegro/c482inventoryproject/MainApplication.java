package montenegro.c482inventoryproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        mainStage.setTitle("Inventory Management System");
        mainStage.setScene(new Scene(root,1303, 494));
        mainStage.show();
    }

    public static void main(String[] args) {

       launch(args);
    }
}