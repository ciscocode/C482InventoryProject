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

public class modifyPartViewController {
    public RadioButton modifyInhouseRadioButton;
    public RadioButton modifyOutsourcedRadioButton;
    public Label modifyPartToggleLabel;

    public void onModifyInhouseToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Machine ID");
    }

    public void onModifyOutsourcedToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Company Name");
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
