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
import java.util.ResourceBundle;

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

    //part search function
    public void partSearchEventHandler(ActionEvent actionEvent) {
        String query = partTextField.getText();

        //search by name first
        ObservableList<Part> parts = searchByPartName(query);

        //if no name matches are found then check if the string contains an id which matches
            if (parts.size() == 0) {
                if(query.matches("\\d*") == false){
                    displayNoMatchError();
                    return;
                }
                int id = Integer.parseInt(query);
                Part part = searchByPartID(id);
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
    private ObservableList<Part> searchByPartName (String partialName) {
        //declare empty array
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        //gather all parts in inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //this will loop through the inventory and add matches into our namedParts array
        for (Part part: allParts) {
            if (part.getName().contains(partialName)) {
                namedParts.add(part);
            }
        }
        //return the namedParts array
        return namedParts;
    }

    private Part searchByPartID (int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();

//        for (int i=0; i<allParts.size(); i++) {
//            Part part = allParts.get(i);
//            if (part.getId() == id) {
//                return part;
//            }
//        }

        for (Part part: allParts) {
            if (part.getId() == id ) {
                return part;
            }
        }
        return null;
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
        Parent root = FXMLLoader.load(getClass().getResource("modify-part-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 640, 620);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
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
}