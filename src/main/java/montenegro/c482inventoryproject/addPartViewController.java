package montenegro.c482inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;


public class addPartViewController {
    public RadioButton addInhouseRadioButton;
    public RadioButton addOutsourcedRadioButton;
    public Label addPartToggleLabel;
    public void onAddInhouseToggle(ActionEvent actionEvent) {
        addPartToggleLabel.setText("Machine ID");
    }

    public void onAddOutsourcedToggle(ActionEvent actionEvent) {
        addPartToggleLabel.setText("Company Name");
    }

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
