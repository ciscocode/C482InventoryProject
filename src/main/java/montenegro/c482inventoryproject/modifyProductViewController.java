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
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static montenegro.c482inventoryproject.Inventory.lookupPart;
import static montenegro.c482inventoryproject.Inventory.lookupProduct;
import static montenegro.c482inventoryproject.MainController.displayNoMatchError;

/** This class modifies an existing Product in the Inventory.*/
public class modifyProductViewController implements Initializable {
    public TextField searchTextField;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TableView<Part> partTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn invCol;
    public TableColumn priceCol;
    public TableView<Part> associatedPartTable;
    public TextField idTextField;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableColumn associatedIdCol;
    public TableColumn associatedNameCol;
    public TableColumn associatedInvCol;
    public TableColumn associatedPriceCol;
    int id;
    String name;
    int inv;
    double price;
    int max;
    int min;
    boolean successfulModification = false;

    /** This method collects a selected products data to send into the Modify Product view.
     * @param product The selected product to be modified
     */
    public void sendData(Product product) {
        idTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        invTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        maxTextField.setText(String.valueOf(product.getMax()));
        minTextField.setText(String.valueOf(product.getMin()));

        for (Part part: product.getAllAssociatedParts()) {
            associatedParts.add(part);
        }
    }

    /**This initialize method creates table views when entering the Modify Product view. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    /**This method modifies an existing products data. */
    public void modifyProduct() {
        //the id will remain the same
        id = Integer.parseInt(idTextField.getText());

        //find the product by searching through the inventory by id
        Product product = lookupProduct(id);

        //turn all inputs into strings
        name = nameTextField.getText();
        String priceString = priceTextField.getText();
        String invString = invTextField.getText();
        String minString = minTextField.getText();
        String maxString = maxTextField.getText();

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
            product.setName(name);
            error = "Price";
            product.setPrice(Double.parseDouble(priceString));
            error = "Stock";
            product.setStock(Integer.parseInt(invString));
            error = "Min";
            product.setMin(Integer.parseInt(minString));
            error = "Max";
            product.setMax(Integer.parseInt(maxString));

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

            //then I must add any associated parts. I must loop because there may be multiple associated parts
            for (Part part: associatedParts) {
                if (part != associatedParts) {
                    product.addAssociatedPart(part);
                }
            }

            successfulModification = true;
        }
        catch (NumberFormatException e) {
            Alert errorMessage = new Alert(Alert.AlertType.WARNING);
            errorMessage.setTitle("Warning");
            errorMessage.setContentText(error + " value must be a number!");
            errorMessage.showAndWait();
            return;
        }
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

    /**This method allows us to search the part table for a part.
     * @param actionEvent Method is called when the user enters text into the search field and presses enter
     */
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

    /**This method allows the user to remove an associated part from a product.
     * @param actionEvent Method is called when the user clicks the Remove Associated Part button
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part part = associatedPartTable.getSelectionModel().getSelectedItem();
            associatedParts.remove(part);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**This method saves the modified product
     * @param actionEvent Method is called when the user clicks the save button
     */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException {
        //insert product into inventory
        modifyProduct();
        if (successfulModification == false) {
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**This method allows the user to add an associated part to a product.
     * @param actionEvent Method is called when the user clicks the add button
     */
    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        associatedPartTable.setItems(associatedParts);
    }
}
