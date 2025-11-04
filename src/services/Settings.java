/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author OSOS
 */
public class Settings {

    private int id;
    private Double tax;
    private Double services;

    public Settings() {
    }

    public Settings(int id, double tax, double services) {
        this.id = id;
        this.tax = tax;
        this.services = services;
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
     * @return the tax
     */
    public Double getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(Double tax) {
        this.tax = tax;
    }

    /**
     * @return the services
     */
    public Double getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(Double services) {
        this.services = services;
    }

}
