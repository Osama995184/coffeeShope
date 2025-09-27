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

    public Sales(int sales_id, int invoice_id, int model_id, int quantity, double total_price, String date, String model_name, String type) {
        this.sales_id = sales_id;
        this.invoice_id = invoice_id;
        this.model_id = model_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.date = date;
        this.model_name = model_name;
        this.type = type;
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
}
