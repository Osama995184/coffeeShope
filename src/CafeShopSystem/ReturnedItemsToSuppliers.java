/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CafeShopSystem;

/**
 *
 * @author Osama
 */
public class ReturnedItemsToSuppliers {

    private int id, item_id, mNumberavailable, mNumberReturn, invoicesID;
    private String model, mcolor, msize, type, suppliers;
    private Double wholesalePrice;

    public ReturnedItemsToSuppliers(int id, int item_id, String model, String mcolor, String msize, int mNumberavailable, int mNumberReturn, Double wholesalePrice, String type, String suppliers, int invoicesID) {
        this.id = id;
        this.item_id = item_id;
        this.model = model;
        this.mcolor = mcolor;
        this.msize = msize;
        this.mNumberavailable = mNumberavailable;
        this.mNumberReturn = mNumberReturn;
        this.wholesalePrice = wholesalePrice;
        this.type = type;
        this.suppliers = suppliers;
        this.invoicesID = invoicesID;
    }

    public int getId() {
        return id;
    }

    public int getMNumberavailable() {
        return mNumberavailable;
    }

    public String getModel() {
        return model;
    }

    public String getMcolor() {
        return mcolor;
    }

    public String getMsize() {
        return msize;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public String getType() {
        return type;
    }

    public int getInvoicesID() {
        return invoicesID;
    }

    public String getSuppliers() {
        return suppliers;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getMNumberReturn() {
        return mNumberReturn;
    }
}
