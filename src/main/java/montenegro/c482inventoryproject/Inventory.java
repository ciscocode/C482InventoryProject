package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

     public static Part lookupPart(int partId) {
         ObservableList<Part> allParts = Inventory.getAllParts();

         for (Part part: allParts) {
             if (part.getId() == partId ) {
                 return part;
             }
         }
         return null;
    }
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product product: allProducts) {
            if (product.getId() == productId ) {
                return product;
            }
        }
        return null;
    }
    //note: search functions are not yet case sensitive
    public static ObservableList<Part> lookupPart(String partName) {
     //declare empty array
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        //gather all parts in inventory
        ObservableList<Part> allParts = Inventory.getAllParts();

        //this will loop through the inventory and add matches into our namedParts array
        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                namedParts.add(part);
            }
        }
        //return the namedParts array
        return namedParts;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        //declare empty array
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        //gather all products in inventory
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        //this will loop through the inventory and add matches into our namedProducts array
        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                namedProducts.add(product);
            }
        }
        //return the namedProducts array
        return namedProducts;
    }
    public static void updatePart(int index, Part selectedPart) {

    }
    public static void updatedProduct(int index, Product newProduct) {

    }
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    static {
        addTestData();
    }

    public static void addTestData() {
        InHouse motor = new InHouse(23,"hemi",2799,7,1,10,327);
        Inventory.addPart(motor);
        Outsourced turbo = new Outsourced(32, "turbo", 799, 25, 0, 50, "Autozone");
        Inventory.addPart(turbo);

        Product corvette = new Product(01, "Corvette", 65000, 547, 2, 600);
        Inventory.addProduct(corvette);
        Product tesla = new Product(02, "Tesla", 70000, 134, 2, 150);
        Inventory.addProduct(tesla);
    }
}
