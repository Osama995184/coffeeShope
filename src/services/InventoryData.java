/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author OSOS
 */
public class InventoryData {
    
    private int id, soldNo, availableNo;
    private int quantity, count;
    private String model, type;
    private Double wholesalePrice, realPrice;

    public InventoryData() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the soldNo
     */
    public int getSoldNo() {
        return soldNo;
    }

    /**
     * @param soldNo the soldNo to set
     */
    public void setSoldNo(int soldNo) {
        this.soldNo = soldNo;
    }

    /**
     * @return the availableNo
     */
    public int getAvailableNo() {
        return availableNo;
    }

    /**
     * @param availableNo the availableNo to set
     */
    public void setAvailableNo(int availableNo) {
        this.availableNo = availableNo;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the wholesalePrice
     */
    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    /**
     * @param wholesalePrice the wholesalePrice to set
     */
    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    /**
     * @return the realPrice
     */
    public Double getRealPrice() {
        return realPrice;
    }

    /**
     * @param realPrice the realPrice to set
     */
    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    
    
}
