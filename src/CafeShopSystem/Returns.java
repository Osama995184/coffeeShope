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
}
