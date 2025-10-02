package CafeShopSystem;

public class Sales {

    private int sales_id;
    private int invoice_id;
    private int model_id;
    private int quantity;
    private double total_price;
    private String date;
    private String model_name;
    private String type;
    private String inventoryType;

    public Sales() {
    }

    public Sales(int sales_id, int invoice_id, int model_id, int quantity, double total_price, String date, String model_name, String type, String inventoryType) {
        this.sales_id = sales_id;
        this.invoice_id = invoice_id;
        this.model_id = model_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.date = date;
        this.model_name = model_name;
        this.type = type;
        this.inventoryType = inventoryType;
    }

    public int getSales_id() {
        return sales_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getDate() {
        return date;
    }

    public String getModel_name() {
        return model_name;
    }

    public String getType() {
        return type;
    }

    /**
     * @param sales_id the sales_id to set
     */
    public void setSales_id(int sales_id) {
        this.sales_id = sales_id;
    }

    /**
     * @param invoice_id the invoice_id to set
     */
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     * @param model_id the model_id to set
     */
    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @param model_name the model_name to set
     */
    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the inventoryType
     */
    public String getInventoryType() {
        return inventoryType;
    }

    /**
     * @param inventoryType the inventoryType to set
     */
    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }
}
