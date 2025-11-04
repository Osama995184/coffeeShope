/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import CafeShopSystem.Returns;
import CafeShopSystem.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author OSOS
 */
public class productServices {

    alert al = new alert();
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

    public boolean insertInventoryData(InventoryData data) {
        String checkSql = "SELECT id, quantity, available FROM inventory WHERE model=? AND wholesalePrice=? AND realPrice=? AND type=?";
        String updateSql = "UPDATE inventory SET quantity = quantity + ?, available = available + ? WHERE id=?";
        String insertSql = "INSERT INTO inventory (model, wholesalePrice, realPrice, type, soldNo, available, count, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection()) {

            // check if record exists
            try (PreparedStatement checkPs = connect.prepareStatement(checkSql)) {
                checkPs.setString(1, data.getModel());
                checkPs.setDouble(2, data.getWholesalePrice());
                checkPs.setDouble(3, data.getRealPrice());
                checkPs.setString(4, data.getType());

                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    // record exists → update
                    int id = rs.getInt("id");
                    try (PreparedStatement updatePs = connect.prepareStatement(updateSql)) {
                        updatePs.setInt(1, data.getQuantity());
                        updatePs.setInt(2, data.getAvailableNo());
                        updatePs.setInt(3, id);
                        return updatePs.executeUpdate() > 0;
                    }
                } else {
                    // record not exists → insert
                    try (PreparedStatement insertPs = connect.prepareStatement(insertSql)) {
                        insertPs.setString(1, data.getModel());
                        insertPs.setDouble(2, data.getWholesalePrice());
                        insertPs.setDouble(3, data.getRealPrice());
                        insertPs.setString(4, data.getType());
                        insertPs.setInt(5, data.getSoldNo());
                        insertPs.setInt(6, data.getAvailableNo());
                        insertPs.setInt(7, data.getCount());
                        insertPs.setInt(8, data.getQuantity());
                        return insertPs.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateInventoryData(InventoryData data) {
        String sql = "UPDATE inventory SET model = ?, wholesalePrice = ?, realPrice = ?, type = ?, "
                + "soldNo = ?, available = ?, count = ?, quantity = ? WHERE id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, data.getModel());
            ps.setDouble(2, data.getWholesalePrice());
            ps.setDouble(3, data.getRealPrice());
            ps.setString(4, data.getType());
            ps.setInt(5, data.getSoldNo());
            ps.setInt(6, data.getAvailableNo());
            ps.setInt(7, data.getCount());
            ps.setInt(8, data.getQuantity());
            ps.setInt(9, data.getId()); // شرط التعديل على ID

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public ObservableList<Returns> getAllReturns() {
        ObservableList<Returns> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM returns ORDER BY return_date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Returns ret = new Returns();

                ret.setReturn_id(rs.getInt("return_id"));
                ret.setProduct_id(rs.getInt("product_id"));
                ret.setInvoice_id(rs.getInt("invoice_id"));
                ret.setQuantity_returned(rs.getInt("quantity_returned"));
                ret.setReturn_reason(rs.getString("return_reason"));
                ret.setReturn_date(rs.getString("return_date"));
                ret.setPrice_of_return(rs.getDouble("price_of_return"));
                ret.setModel_name(rs.getString("model_name"));

                listData.add(ret);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل في جلب بيانات المرتجعات", Alert.AlertType.ERROR);
        }

        return listData;
    }

}
