package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Random;

import static montenegro.c482inventoryproject.Inventory.lookupPart;

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

    public void onModifyInhouseToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Machine ID");
    }

    public void onModifyOutsourcedToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Company Name");
    }

    //sends the selected parts info into the text fields
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

    public void modifyPart() {
        id = Integer.parseInt(idTextField.getText());

        //then once you find the part you can use the setters to modify the part
        name = nameTextField.getText();
        System.out.println(name);
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
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
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
            System.out.println("modifying inhouse part");
        }
        catch (NumberFormatException e) {
            System.out.println(error + " value must be a number!");
            return;
        }
    }

    public void savePart(ActionEvent actionEvent) throws IOException {
        System.out.println("modifying part");
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

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
