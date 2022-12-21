package montenegro.c482inventoryproject;

import javafx.application.Platform;
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
import java.util.ResourceBundle;

import static montenegro.c482inventoryproject.Inventory.lookupPart;
import static montenegro.c482inventoryproject.Inventory.lookupProduct;

public class MainController implements Initializable {
    //remember to add buttons also

    //sample part data
    public TableView<Part> thePartTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partStockCol;
    public TableColumn partPriceCol;

    //public static ObservableList<Part> partList = FXCollections.observableArrayList();
    public TableView<Product> theProductTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productStockCol;
    public TableColumn productPriceCol;
    public TextField productTextField;
    public TextField partTextField;
    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        thePartTable.setItems(Inventory.getAllParts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        theProductTable.setItems(Inventory.getAllProducts());
    }

    public static void deleteSelection() {

    }
    //part search function
    //could be improved with live update while I search
    public void partSearchEventHandler(ActionEvent actionEvent) {
        String query = partTextField.getText();

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
        thePartTable.setItems(parts);
    }

    public void productSearchEventHandler(ActionEvent actionEvent) {
        String query = productTextField.getText();

        //search by name first
        ObservableList<Product> products = lookupProduct(query);

        //if no name matches are found then check if the string contains an id which matches
        if (products.size() == 0) {
            if(query.matches("\\d*") == false){
                displayNoMatchError();
                return;
            }
            int id = Integer.parseInt(query);
            Product product = lookupProduct(id);
            if (product != null) {
                products.add(product);
            }
        }
        //if the query still results in no matches, then return the error message
        if (products.size() == 0) {
            displayNoMatchError();
        }

        //update the product table by passing in the new list returned in the search
        theProductTable.setItems(products);
    }

    //This function will be used to display an error messsage when a user enters a search term which does not find a match
    public static void displayNoMatchError() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle("Error");
        errorMessage.setContentText("No matches were found!");
        errorMessage.showAndWait();
    }

    public static void displayNoSelectionError() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle("Error");
        errorMessage.setContentText("Please make a selection from the table");
        errorMessage.showAndWait();
    }

    //this loads the add part view
    public void addPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-part-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 640, 620);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    //this loads the modify part view with the default in house option selected
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader modifyPartLoader = new FXMLLoader();
        modifyPartLoader.setLocation(getClass().getResource("modify-part-view.fxml"));
        modifyPartLoader.load();
        modifyPartViewController modifyController = modifyPartLoader.getController();
        if (thePartTable.getSelectionModel().getSelectedItem() == null) {
            displayNoSelectionError();
            return;
        }
        modifyController.sendData(thePartTable.getSelectionModel().getSelectedIndex(),thePartTable.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = modifyPartLoader.getRoot();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //This loads the view of the add product form
    public void addProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-product-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1150, 700);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    //This loads the view of the modify product form
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader modifyProductLoader = new FXMLLoader();
        modifyProductLoader.setLocation(getClass().getResource("modify-product-view.fxml"));
        modifyProductLoader.load();
        modifyProductViewController modifyController = modifyProductLoader.getController();
        if (theProductTable.getSelectionModel().getSelectedItem() == null) {
            displayNoSelectionError();
            return;
        }
        modifyController.sendData(theProductTable.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = modifyProductLoader.getRoot();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //this closes out the program
    public void exitProgram(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void onDeletePart(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part part = thePartTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        }
    }

    public void onDeleteProduct(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product product = theProductTable.getSelectionModel().getSelectedItem();
            if (product.getAllAssociatedParts().size() > 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setContentText("You can not delete a Product with associated parts!");
                error.showAndWait();
                return;
            }
            Inventory.deleteProduct(product);
        }
    }
}