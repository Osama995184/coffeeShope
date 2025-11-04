/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CafeShopSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author Osama
 */
public class Shift {

    private int id;
    private String username;
    private String closedBy;
    private double startingBalance;
    private double endingBalance;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    public Shift(int id, String username, String closedBy, double startingBalance, double endingBalance, LocalDateTime openingTime, LocalDateTime closingTime) {
        this.id = id;
        this.username = username;
        this.closedBy = closedBy;
        this.startingBalance = startingBalance;
        this.endingBalance = endingBalance;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    public String getOpeningTimeFormatted() {
        if (openingTime == null) {
            return "";
        }
        return openingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getClosingTimeFormatted() {
        if (closingTime == null) {
            return "";
        }
        return closingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
