package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    Random randomId = new Random();
    boolean successfulAddition = false;

    public void onModifyInhouseToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Machine ID");
    }

    public void onModifyOutsourcedToggle(ActionEvent actionEvent) {
        modifyPartToggleLabel.setText("Company Name");
    }

    //sends the selected parts info into the text fields
    public void sendInhousePart(InHouse part) {
        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));
        machineIdTextField.setText(String.valueOf(part.getMachineId()));
    }

    //send an outsourced parts data
    public void sendOutsourcedPart(Outsourced part) {
        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(part.getName());
        invTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));
        machineIdTextField.setText(part.getCompanyName());
    }

    public void sendData(Part part) {
        if (part instanceof InHouse) {
            modifyInhouseRadioButton.setSelected(true);
            sendInhousePart((InHouse) part);
        } else {
            modifyOutsourcedRadioButton.setSelected(true);
            sendOutsourcedPart((Outsourced) part);
            modifyPartToggleLabel.setText("Company Name");
        }
    }

    public void modifyInhousePart() {
        //the id will remain the same
        id = Integer.parseInt(idTextField.getText());

        //find the inhouse part by searching through the inventory by id
        InHouse part = (InHouse) lookupPart(id);

        System.out.println(part.getName());

        //then once you find the part you can use the setters to modify the part
        name = nameTextField.getText();
        System.out.println(name);
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        String machineIdString = machineIdTextField.getText();

        String error = "";
        try {
            part.setName(name);
            error = "Price";
            part.setPrice(Double.parseDouble(priceString));
            error = "Stock";
            part.setStock(Integer.parseInt(invString));
            error = "Min";
            part.setMin(Integer.parseInt(minString));
            error = "Max";
            part.setMax(Integer.parseInt(maxString));
            error = "Machine ID";
            part.setMachineId(Integer.parseInt(machineIdString));

            //check to see if min & max values are valid
            if (min > inv) {
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
                return;
            }
            successfulAddition = true;
            System.out.println("modifying inhouse part");
        }
        catch (NumberFormatException e) {
            System.out.println(error + " value must be a number!");
            return;
        }
    }
    public void modifyOutsourcedPart() {
        //the id will remain the same
        id = Integer.parseInt(idTextField.getText());

        //find the outsourced part by searching through the inventory by id
        Outsourced part = (Outsourced) lookupPart(id);

        System.out.println(part.getName());

        //then once you find the part you can use the setters to modify the part
        name = nameTextField.getText();
        System.out.println(name);
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();
        companyName = machineIdTextField.getText();

        String error = "";
        try {
            part.setName(name);
            error = "Price";
            part.setPrice(Double.parseDouble(priceString));
            error = "Stock";
            part.setStock(Integer.parseInt(invString));
            error = "Min";
            part.setMin(Integer.parseInt(minString));
            error = "Max";
            part.setMax(Integer.parseInt(maxString));
            error = "Company Name";
            part.setCompanyName(companyName);

            //check to see if min & max values are valid
            if (min > inv) {
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
                return;
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

        if (modifyInhouseRadioButton.isSelected()) {
            System.out.println("modifying an inhouse part");
            //insertInHousePart();
            modifyInhousePart();
            if (successfulAddition == false) {
                return;
            }
        }

        if (modifyOutsourcedRadioButton.isSelected()) {
            System.out.println("modifying an outsourced part");
            modifyOutsourcedPart();
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

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
