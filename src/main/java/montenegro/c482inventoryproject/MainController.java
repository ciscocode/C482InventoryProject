package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    //this will bring users back to the main view. This will be used when a user clicks "cancel" when using one of the add/modify part or product features
//    public void toMainScreen(ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
//        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene((Parent) root, 1303, 494);
//        stage.setTitle("Inventory Management System");
//        stage.setScene(scene);
//        stage.show();
//    }

//    public void onAddInhouseToggle(ActionEvent actionEvent) {
//        addPartToggleLabel.setText("Machine ID");
//    }
//
//    public void onAddOutsourcedToggle(ActionEvent actionEvent) {
//        addPartToggleLabel.setText("Company Name");
//    }

//    public void onModifyInhouseToggle(ActionEvent actionEvent) {
//        modifyPartToggleLabel.setText("Machine ID");
//    }
//
//    public void onModifyOutsourcedToggle(ActionEvent actionEvent) {
//        modifyPartToggleLabel.setText("Company Name");
//    }
}