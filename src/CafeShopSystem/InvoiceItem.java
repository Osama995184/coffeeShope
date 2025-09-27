/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CafeShopSystem;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class InvoiceItem {

    private String modelID;
    private String model;
    private String restTable;
    private String dataType;
    private double price_beforDis;
    private int quantity;
    private int status;
    private int invoiceId;
    private int salesId;
    private double totalP;
    private double totalPriceBeforeDis;
    private double price;
    private double priceForInvoice;
    private Timestamp date = Timestamp.valueOf(LocalDateTime.now());

    public InvoiceItem(String modelID, String model, int quantity, double totalP, double price, double price_beforDis, double totalPriceBeforeDis) {
        this.model = model;
        this.quantity = quantity;
        this.totalP = totalP;
        this.modelID = modelID;
        this.price = price;
        this.price_beforDis = price_beforDis;
        this.totalPriceBeforeDis = totalPriceBeforeDis;
    }

    public InvoiceItem(String modelID, String model, int quantity, double totalP, double price, double price_beforDis, double totalPriceBeforeDis, String dataType) {
        this.model = model;
        this.quantity = quantity;
        this.totalP = totalP;
        this.modelID = modelID;
        this.price = price;
        this.price_beforDis = price_beforDis;
        this.totalPriceBeforeDis = totalPriceBeforeDis;
        this.dataType = dataType;
    }

    public InvoiceItem() {
    }

    public String getModelID() {
        return modelID;
    }

    public String getModel() {
        return model;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalP() {
        return totalP;
    }

    public double getPrice() {
        return price;
    }

    public Timestamp getDate() {
        return date;
    }

    public double getTotalPriceBeforeDis() {
        return totalPriceBeforeDis;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalP(double totalP) {
        this.totalP = totalP;
    }

    public void setTotalPriceBeforeDis(double totalPriceBeforeDis) {
        this.totalPriceBeforeDis = totalPriceBeforeDis;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the restTable
     */
    public String getRestTable() {
        return restTable;
    }

    /**
     * @param restTable the restTable to set
     */
    public void setRestTable(String restTable) {
        this.restTable = restTable;
    }

    public double getPrice_beforDis() {
        return price_beforDis;
    }

    /**
     * @param modelID the modelID to set
     */
    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    /**
     * @param price_beforDis the price_beforDis to set
     */
    public void setPrice_beforDis(double price_beforDis) {
        this.price_beforDis = price_beforDis;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * @return the invoiceId
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the priceForInvoice
     */
    public double getPriceForInvoice() {
        return priceForInvoice;
    }

    /**
     * @param priceForInvoice the priceForInvoice to set
     */
    public void setPriceForInvoice(double priceForInvoice) {
        this.priceForInvoice = priceForInvoice;
    }

    /**
     * @return the salesId
     */
    public int getSalesId() {
        return salesId;
    }

    /**
     * @param salesId the salesId to set
     */
    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }
}
