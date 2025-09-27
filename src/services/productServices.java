/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import CafeShopSystem.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author OSOS
 */
public class productServices {

    // 1- إرجاع كل البيانات
    public ObservableList<InventoryData> getAllData(String typeFilter) {
        ObservableList<InventoryData> listData = FXCollections.observableArrayList();
        String sql;

        if (typeFilter != null) {
            sql = "SELECT * FROM inventory WHERE type LIKE ? ORDER BY model COLLATE NOCASE";
        } else {
            sql = "SELECT * FROM inventory ORDER BY model COLLATE NOCASE";
        }

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            if (typeFilter != null) {
                ps.setString(1, typeFilter);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InventoryData invData = new InventoryData();

                invData.setId(rs.getInt("id"));
                invData.setModel(rs.getString("model"));
                invData.setWholesalePrice(rs.getDouble("wholesalePrice"));
                invData.setRealPrice(rs.getDouble("realPrice"));
                invData.setType(rs.getString("type"));
                invData.setSoldNo(rs.getInt("soldNo"));
                invData.setAvailableNo(rs.getInt("available"));
                invData.setCount(rs.getInt("count"));
                invData.setQuantity(rs.getInt("quantity"));

                listData.add(invData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }

    public InventoryData getInventoryDataByID(int productID) {
        String sql = "SELECT * FROM inventory WHERE id = ?";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    InventoryData invData = new InventoryData();
                    invData.setId(rs.getInt("id"));
                    invData.setModel(rs.getString("model"));
                    invData.setWholesalePrice(rs.getDouble("wholesalePrice"));
                    invData.setRealPrice(rs.getDouble("realPrice"));
                    invData.setType(rs.getString("type"));
                    invData.setSoldNo(rs.getInt("soldNo"));
                    invData.setAvailableNo(rs.getInt("available"));
                    invData.setCount(rs.getInt("count"));
                    invData.setQuantity(rs.getInt("quantity"));
                    return invData;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertOrUpdateData(InventoryData data) {
        String sql = "INSERT INTO inventory (model, wholesalePrice, realPrice, type, soldNo, available, count, quantity) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "ON CONFLICT(model) DO UPDATE SET "
                + "wholesalePrice = excluded.wholesalePrice, "
                + "realPrice = excluded.realPrice, "
                + "type = excluded.type, "
                + "soldNo = soldNo + excluded.soldNo, "
                + "available = available + excluded.available, "
                + "count = count + excluded.count, "
                + "quantity = quantity + excluded.quantity";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, data.getModel());
            ps.setDouble(2, data.getWholesalePrice());
            ps.setDouble(3, data.getRealPrice());
            ps.setString(4, data.getType());
            ps.setInt(5, data.getSoldNo());
            ps.setInt(6, data.getAvailableNo());
            ps.setInt(7, data.getCount());
            ps.setInt(8, data.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 4- حذف صف
    public boolean deleteData(int id) {
        String sql = "DELETE FROM inventory WHERE id=?";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
