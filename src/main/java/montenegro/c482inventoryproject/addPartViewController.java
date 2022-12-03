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


    //String query = partTextField.getText();
    //Integer.parseInt(query)

//    String name = nameTextField.getText();
//    int inv = Integer.parseInt(invTextField.getText());
//    double price = Integer.parseInt(priceTextField.getText());
//    int max = Integer.parseInt(maxTextField.getText());
//    int min = Integer.parseInt(minTextField.getText());
//    int machineId = Integer.parseInt(machineIdTextField.getText());

  int id;
    String name;
    int inv;
    double price;
    int max;
    int min;
    int machineId;
    Random randomId = new Random();


    //Inventory.addPart()
   //This will take the data from the text fields and use it to insert a part into the inventory
    public void insertPart(int id, String name, double price, int inv, int min, int max, int machineId) {
        InHouse part = new InHouse(id,name,price,inv,min,max,machineId);
        part.setId(randomId.nextInt(99)); //just for this test. must edit later so that id is randomized or incremented

        part.setName(nameTextField.getText());
        part.setPrice(Integer.parseInt(priceTextField.getText()));
        part.setStock(Integer.parseInt(invTextField.getText()));
        part.setMin(Integer.parseInt(minTextField.getText()));
        part.setMax(Integer.parseInt(maxTextField.getText()));
        part.setMachineId(Integer.parseInt(machineIdTextField.getText()));

//        part.setName("cat");
//        part.setPrice(4);
//        part.setStock(5);
//        part.setMin(7);
//        part.setMax(9);
//        part.setMachineId(9);

        Inventory.addPart(part);

        System.out.println("saving part");
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
        insertPart(id, name, price, inv, min, max, machineId);
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root, 1303, 494);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
        //InHouse motor = new InHouse(23,"hemi",2799,7,1,10,327);
        //Inventory.addPart(motor);
        //sample with inhouse option selected. Must modify this later so I can select either option
    }
}
