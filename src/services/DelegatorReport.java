/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Date;

/**
 *
 * @author Gemy - ISAG
 */
public class DelegatorReport {
    private int id;
    private String name;
    private String phone;
    private String statusType;
    private Integer status;
    private int deliveryCount;
    private double totalDelivery;
    private int orderNumber;
    private double totalInvoice;
    private Date date;
    private double target;

    public DelegatorReport() {
    }

    public DelegatorReport(int id, String name, String phone, int status, int deliveryCount, double totalDelivery, double target) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.deliveryCount = deliveryCount;
        this.totalDelivery = totalDelivery;
        this.target = target;
    }
    
    public DelegatorReport(int orderNumber, String name, String statusType, double totalDelivery, double totalInvoice, Date date) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.statusType = statusType;
        this.totalDelivery = totalDelivery;
        this.totalInvoice = totalInvoice;
        this.date = date;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the deliveryCount
     */
    public int getDeliveryCount() {
        return deliveryCount;
    }

    /**
     * @param deliveryCount the deliveryCount to set
     */
    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    /**
     * @return the totalDelivery
     */
    public double getTotalDelivery() {
        return totalDelivery;
    }

    /**
     * @param totalDelivery the totalDelivery to set
     */
    public void setTotalDelivery(double totalDelivery) {
        this.totalDelivery = totalDelivery;
    }

    /**
     * @return the target
     */
    public double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * @return the statusType
     */
    public String getStatusType() {
        return statusType;
    }

    /**
     * @param statusType the statusType to set
     */
    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    /**
     * @return the orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the totalInvoice
     */
    public double getTotalInvoice() {
        return totalInvoice;
    }

    /**
     * @param totalInvoice the totalInvoice to set
     */
    public void setTotalInvoice(double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
