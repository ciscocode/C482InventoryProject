package montenegro.c482inventoryproject;

/**This class defines an InHouse part.
 * This is a subclass of the Part class
 */
public class InHouse extends Part {
    private int machineId;

    /**This method creates an Inhouse part.
     * @param id The ID number for the part
     * @param name The name for the part
     * @param price The price of the part
     * @param stock The stock quantity
     * @param min The minumum stock
     * @param max The maximum stock
     * @param machineId The Machine ID for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**This method returns the Machine ID for the part.
     * @return Returns the machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**This method sets the Machine ID for the part
     * @param machineId The Machine ID for the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
