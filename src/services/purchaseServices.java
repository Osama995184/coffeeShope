/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import CafeShopSystem.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Gemy - ISAG
 */
public class purchaseServices {

    alert al = new alert();

    public int getTodayInvoicesCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total_orders_today FROM invoices WHERE date(invoice_date) = date('now', 'localtime')";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total_orders_today");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل في جلب عدد فواتير اليوم", AlertType.ERROR);
        }

        return count;
    }
}
