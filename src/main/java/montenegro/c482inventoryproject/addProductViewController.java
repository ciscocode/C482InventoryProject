package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static montenegro.c482inventoryproject.Inventory.lookupPart;
import static montenegro.c482inventoryproject.MainController.displayNoMatchError;

public class addProductViewController implements Initializable {
    public TextField searchTextField;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TableView<Part> partTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn invCol;
    public TableColumn priceCol;
    public TableView<Part> associatedPartTable;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableColumn associatedIdCol;
    public TableColumn associatedNameCol;
    public TableColumn associatedInvCol;
    public TableColumn associatedPriceCol;
    public TextField minTextField;
    public Button onSaveProduct;
    int id;
    String name;
    int inv;
    double price;
    int max;
    int min;
    Random randomId = new Random();
    boolean successfulAddition = false;

    public void insertProduct() {
        id = randomId.nextInt(99); //just for this test. must edit later so that id is randomized or incremented

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();

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

            //check to see if min & max values are valid
            if (min > inv) {
                System.out.println("Min is higher than available stock!");
                return;
            }

            if (inv > max) {
                System.out.println("Stock must be less than or equal to max!");
                return;
            }

            Product product = new Product(id,name,price,inv,min,max);
            Inventory.addProduct(product);
            successfulAddition = true;
            System.out.println("inserting product");
        }
        catch (NumberFormatException e) {
            System.out.println(error + " value must be a number!");
            return;
        }
    }
    //could be improved with live update while I search
    public void searchEventHandler(ActionEvent actionEvent) {
        String query = searchTextField.getText();

        //search by name first
        ObservableList<Part> parts = lookupPart(query);

        //if no name matches are found then check if the string contains an id which matches
        if (parts.size() == 0) {
            if(query.matches("\\d*") == false){
                displayNoMatchError();
                return;
            }
            int id = Integer.parseInt(query);
            Part part = lookupPart(id);
            if (part != null) {
                parts.add(part);
            }
        }
        //if the query still results in no matches, then return the error message
        if (parts.size() == 0) {
            displayNoMatchError();
        }

        //update the parts table by passing in the new list returned in the search
        partTable.setItems(parts);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partTable.setItems(Inventory.getAllParts());

        associatedIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        associatedInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        associatedPartTable.setItems(associatedParts);
    }

    public void onSaveProduct(ActionEvent actionEvent) throws IOException {
        System.out.println("saving product");
        insertProduct();
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

    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        associatedPartTable.setItems(associatedParts);
    }


    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        associatedParts.remove(part);
        associatedPartTable.setItems(associatedParts);
    }
}
