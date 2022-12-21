package montenegro.c482inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Random;

import java.io.IOException;

/** This class adds a part to the users inventory.*/
public class addPartViewController {
    public RadioButton addInhouseRadioButton;
    public RadioButton addOutsourcedRadioButton;
    public Label addPartToggleLabel;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TextField machineIdTextField;
    public Button saveButton;

    int id;
    String name;
    int inv;
    double price;
    int max;
    int min;
    int machineId;
    String companyName;
    Random randomId = new Random();
    boolean successfulAddition = false;

    /**This method will create an InHouse Part. */
    public void insertInHousePart() {
        //generate a random ID for our part
        id = randomId.nextInt(99);

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        String machineIdString = machineIdTextField.getText();

        //check for invalid data
        if (name.isBlank()) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText("The name is blank!");
            errorMessage.showAndWait();
            return;
        }

        String error = "";
        try {
            error = "Price";
            price = Double.parseDouble(priceString);
            error = "Stock";
            inv = Integer.parseInt(invString);
            error = "Min";
            min = Integer.parseInt(minString);
            error = "Max";
            max = Integer.parseInt(maxString);
            error = "Machine ID";
            machineId = Integer.parseInt(machineIdString);

            //check to see if min & max values are valid
            if (min > inv) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Warning");
                errorMessage.setContentText("Min is higher than available stock!");
                errorMessage.showAndWait();
                return;
            }

            if (inv > max) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Warning");
                errorMessage.setContentText("Stock must be less than or equal to max!");
                errorMessage.showAndWait();
                return;
            }

            InHouse part = new InHouse(id,name,price,inv,min,max,machineId);
            Inventory.addPart(part);
            successfulAddition = true;
        }
        catch (NumberFormatException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText(error + " value must be a number!");
            errorMessage.showAndWait();
            return;
        }
    }

    /**This method will create an Outsourced Part. */
    public void insertOutsourcedPart() {
        //generate a random ID
        id = randomId.nextInt(99);

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        companyName = machineIdTextField.getText();

        //check for invalid data
        if (name.isBlank()) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText("The name is blank!");
            errorMessage.showAndWait();
            return;
        }

        if (companyName.isBlank()) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText("The company name is blank!");
            errorMessage.showAndWait();
            return;
        }

        String error = "";
        try {
            error = "Price";
            price = Double.parseDouble(priceString);
            error = "Stock";
            inv = Integer.parseInt(invString);
            error = "Min";
            min = Integer.parseInt(minString);
            error = "Max";
            max = Integer.parseInt(maxString);

            //check to see if min & max values are valid
            if (min > inv) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Warning");
                errorMessage.setContentText("Min is higher than available stock!");
                errorMessage.showAndWait();
                return;
            }

            if (inv > max) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Warning");
                errorMessage.setContentText("Stock must be less than or equal to max!");
                errorMessage.showAndWait();
                return;
            }

            Outsourced part = new Outsourced(id,name,price,inv,min,max,companyName);
            Inventory.addPart(part);
            successfulAddition = true;
        }
        catch (NumberFormatException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText(error + " value must be a number!");
            errorMessage.showAndWait();
            return;
        }
    }

    /**This method will set an input label as Machine ID.
     * @param actionEvent This method is called when the InHouse radio button is toggled
     */
    public void onAddInhouseToggle(ActionEvent actionEvent) {
        addPartToggleLabel.setText("Machine ID");
    }

    /**This method will set an input label as Company ID.
     * @param actionEvent This method is called when the Outsourced radio button is toggled
     */
    public void onAddOutsourcedToggle(ActionEvent actionEvent) {
        addPartToggleLabel.setText("Company Name");
    }

    /**This method will send the user back to the main screen view.
     * @param actionEvent Method is called when the user clicks the cancel button
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**This method will save the users part.
     * It will save an InHouse or Outsourced part depending on which radio button is selected
     * @param actionEvent Method is called when the user clicks on the save button
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        if (addInhouseRadioButton.isSelected()) {
            insertInHousePart();
            if (successfulAddition == false) {
                return;
            }
        }

        if (addOutsourcedRadioButton.isSelected()) {
            insertOutsourcedPart();
            if (successfulAddition == false) {
                return;
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
