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
import java.util.ResourceBundle;

import static montenegro.c482inventoryproject.Inventory.lookupPart;

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

    //This function will be used to display an error messsage when a user enters a search term which does not find a match
    public static void displayNoMatchError() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle("Error");
        errorMessage.setContentText("No matches were found!");
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
        //modifyController.sendInhousePart((InHouse) thePartTable.getSelectionModel().getSelectedItem()); //must find a fix for outsourced
        modifyController.sendData(thePartTable.getSelectionModel().getSelectedItem());
        //Parent root = FXMLLoader.load(getClass().getResource("modify-part-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = modifyPartLoader.getRoot();
        //Scene scene = new Scene((Parent) root, 640, 620);
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
        Parent root = FXMLLoader.load(getClass().getResource("modify-product-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1150, 700);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    //this closes out the program
    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }
}