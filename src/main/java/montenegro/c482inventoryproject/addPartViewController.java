package montenegro.c482inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Random;

import java.io.IOException;


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

    public void insertInHousePart() {
        id = randomId.nextInt(99); //just for this test. must edit later so that id is randomized or incremented

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        String machineIdString = machineIdTextField.getText();

        //check for invalid data
        if (name.isBlank()) {
            System.out.println("Name is blank");
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
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
                return;
            }

            InHouse part = new InHouse(id,name,price,inv,min,max,machineId);
            Inventory.addPart(part);
            successfulAddition = true;
            System.out.println("inserting inhouse part");
        }
        catch (NumberFormatException e) {
            System.out.println(error + " value must be a number!");
            return;
        }
    }

    public void insertOutsourcedPart() {
        id = randomId.nextInt(99); //just for this test. must edit later so that id is randomized or incremented

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        companyName = machineIdTextField.getText();

        //check for invalid data
        if (name.isBlank()) {
            System.out.println("Name is blank");
            return;
        }

        if (companyName.isBlank()) {
            System.out.println("Company Name is blank");
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
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
                return;
            }

            Outsourced part = new Outsourced(id,name,price,inv,min,max,companyName);
            Inventory.addPart(part);
            successfulAddition = true;
            System.out.println("inserting outsourced part");
        }
        catch (NumberFormatException e) {
            System.out.println(error + " value must be a number!");
            return;
        }
    }

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

    //saves part and then returns to main screen
    public void savePart(ActionEvent actionEvent) throws IOException {
        System.out.println("saving part");

        if (addInhouseRadioButton.isSelected()) {
            System.out.println("lol this is inhouse");
            insertInHousePart();
            if (successfulAddition == false) {
                return;
            }
        }

        if (addOutsourcedRadioButton.isSelected()) {
            System.out.println("hehe this is outsourced");
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
