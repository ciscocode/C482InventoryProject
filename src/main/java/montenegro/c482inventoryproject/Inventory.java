package montenegro.c482inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class defines the Inventory made up of Parts and Products. */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**This method sets adds a part to the AllParts list in the Inventory
     * @param newPart The Part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**This method sets adds a product to the AllProducts list in the Inventory
     * @param newProduct The Product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**This method looks up a part within the inventory using the Part ID
     * @param partId The ID of the part
     * @return Returns the part in the inventory which matches the partID
     */
     public static Part lookupPart(int partId) {
         ObservableList<Part> allParts = Inventory.getAllParts();

         for (Part part: allParts) {
             if (part.getId() == partId ) {
                 return part;
             }
         }
         return null;
    }

    /**This method looks up a product within the inventory using the Product ID
     * @param productId The ID of the part
     * @return Returns the part in the inventory which matches the productID
     */
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

    /**This method looks up a part within the inventory using the name
     * @param partName the name of the part to search for
     * @return Returns the part in the inventory which matches the name
     */
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

    /**This method looks up a product within the inventory using the name
     * @param productName the name of the product to search for
     * @return Returns the product in the inventory which matches the name
     */
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

    /** This method updates a part at a selcted index
     * @param index The index of the part within the observable list
     * @param selectedPart The updated part to set into the index
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** This method updates a product at a selcted index
     * @param index The index of the part within the observable list
     * @param newProduct The updated product to set into the index
     */
    public static void updatedProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**This method deletes a selected part
     * @param selectedPart the part to be deleted
     * @return Returns true if a part was successfully deleted
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**This method deletes a selected product
     * @param selectedProduct the part to be deleted
     * @return Returns true if a product was successfully deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**This method returns a list of all the parts within the inventory
     * @return Returns the observable list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**This method returns a list of all the product within the inventory
     * @return Returns the observable list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    static {
        addTestData();
    }

    /**This method adds test data into the program. */
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
