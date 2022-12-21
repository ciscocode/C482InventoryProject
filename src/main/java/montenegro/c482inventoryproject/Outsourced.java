package montenegro.c482inventoryproject;

/**This class defines an Outsourced part.
 * This is a subclass of the Part class
 */
public class Outsourced extends Part {
    private String companyName;

    /**This method creates an Outsourced part
     * @param id The ID number for the part
     * @param name The name for the part
     * @param price The price of the part
     * @param stock The stock quantity
     * @param min The minumum stock
     * @param max The maximum stock
     * @param companyName The company name of the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**This method returns the Company Name for the part.
     * @return Returns the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**This method sets the Company Name of the part
     * @param companyName The company name for the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
