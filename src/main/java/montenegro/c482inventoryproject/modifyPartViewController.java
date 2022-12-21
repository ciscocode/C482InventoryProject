package montenegro.c482inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/** This class modifies an existing part in the inventory.
 * <br> <br>
 * <b>RUNTIME ERROR</b> - I was having errors when modifying a part after switching it from InHouse to Outsourced and vice versa.
 * This occured because I attempted to use the setters to change the properties such as name, stock, min, max, etc.
 * However, my error occured because I forgot that the Machine ID and Company Name are two different data types.
 * To solve this I refactored my code so that I would take the inputs within the Modify Part form and create a new part.
 * I would then take this part and use my UpdatePart method from my Inventory class to replace the old part with my newly created part
 */
public class modifyPartViewController {
    public RadioButton modifyInhouseRadioButton;
    public RadioButton modifyOutsourcedRadioButton;
    public Label modifyPartToggleLabel;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TextField machineIdTextField;
    public TextField idTextField;
    int id;
    String name;
    int inv;
    double price;
    int max;
    int min;
    int machineId;
    String companyName;
    boolean successfulAddition = false;
    int index = 0;

    /**This method will set an input label as Machine ID.
     * @param actionEvent This method is called when the InHouse radio button is toggled
     */
    public void onModifyInhouseToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Machine ID");
    }
    /**This method will set an input label as Company Name.
     * @param actionEvent This method is called when the Outsourced radio button is toggled
     */
    public void onModifyOutsourcedToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Company Name");
    }

    /** This method collects a selected parts data to send into the Modify Part view.
     * @param partIndex The index of the part within the observable list
     * @param part The selected part to be modified
     */
    public void sendData(int partIndex, Part part) {
        index = partIndex;

        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modifyInhouseRadioButton.setSelected(true);
            machineIdTextField.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            modifyOutsourcedRadioButton.setSelected(true);
            machineIdTextField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            modifyPartToggleLabel.setText("Company Name");
        }
    }

    /** This method modifies the data for a selected part. */
    public void modifyPart() {
        id = Integer.parseInt(idTextField.getText());

        //then once you find the part you can use the setters to modify the part
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        String machineIdString = machineIdTextField.getText();

        if (modifyOutsourcedRadioButton.isSelected()) {
            companyName = machineIdTextField.getText();
            if (companyName.isBlank()) {
                Alert errorMessage = new Alert(Alert.AlertType.WARNING);
                errorMessage.setTitle("Warning");
                errorMessage.setContentText("The name is blank!");
                errorMessage.showAndWait();
                return;
            }
        }

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
            if (modifyInhouseRadioButton.isSelected()) {
                machineId = Integer.parseInt(machineIdString);
            }

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

            if (modifyInhouseRadioButton.isSelected()) {
                InHouse newPart = new InHouse(id,name,price,inv,min,max,machineId);
                Inventory.updatePart(index, newPart);
            } else {
                Outsourced newPart = new Outsourced(id,name,price,inv,min,max,companyName);
                Inventory.updatePart(index, newPart);
            }

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

    /**This method will save a part with its new modified traits.
     * @param actionEvent This method is called when the user clicks save
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        modifyPart();
        if (successfulAddition == false) {
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
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
}
