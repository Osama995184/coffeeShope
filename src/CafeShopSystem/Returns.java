/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CafeShopSystem;

public class Returns {

    private int return_id;
    private int product_id;
    private int invoice_id;
    private int quantity_returned;
    private String return_reason;
    private String return_date;
    private double price_of_return;
    private String model_name;

    public Returns() {
    }

    public Returns(int return_id, int product_id, int invoice_id, int quantity_returned, String return_reason, String return_date, double price_of_return, String model_name) {
        this.return_id = return_id;
        this.product_id = product_id;
        this.invoice_id = invoice_id;
        this.quantity_returned = quantity_returned;
        this.return_reason = return_reason;
        this.return_date = return_date;
        this.price_of_return = price_of_return;
        this.model_name = model_name;
    }

    public int getReturn_id() {
        return return_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getQuantity_returned() {
        return quantity_returned;
    }

    public String getReturn_reason() {
        return return_reason;
    }

    public String getReturn_date() {
        return return_date;
    }

    public double getPrice_of_return() {
        return price_of_return;
    }

    public String getModel_name() {
        return model_name;
    }

    /**
     * @param return_id the return_id to set
     */
    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * @param invoice_id the invoice_id to set
     */
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     * @param quantity_returned the quantity_returned to set
     */
    public void setQuantity_returned(int quantity_returned) {
        this.quantity_returned = quantity_returned;
    }

    /**
     * @param return_reason the return_reason to set
     */
    public void setReturn_reason(String return_reason) {
        this.return_reason = return_reason;
    }

    /**
     * @param return_date the return_date to set
     */
    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    /**
     * @param price_of_return the price_of_return to set
     */
    public void setPrice_of_return(double price_of_return) {
        this.price_of_return = price_of_return;
    }

    /**
     * @param model_name the model_name to set
     */
    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
}
