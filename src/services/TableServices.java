/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import CafeShopSystem.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OSOS
 */
public class TableServices {

    public static final int EMPTY = 0;
    public static final int OCCUPIED = 1;

    public List<TableData> getAllTables() {
        List<TableData> list = new ArrayList<>();
        String sql = "SELECT id, name, status FROM restaurant_tables";
        try (Connection conn = database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TableData(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TableData> getTables() {
        List<TableData> list = new ArrayList<>();
        String sql = "SELECT id, name FROM restaurant_tables";
        try (Connection conn = database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TableData(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<TableData> getAllTablesByStatus() {
        List<TableData> list = new ArrayList<>();
        String sql = "SELECT id, name, status FROM restaurant_tables Where status = 0 ";
        try (Connection conn = database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TableData(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addTable(String name) {
        String sql = "INSERT INTO restaurant_tables(name, status) VALUES(?, ?)";
        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, EMPTY);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTableName(int id, String newName) {
        String sql = "UPDATE restaurant_tables SET name = ? WHERE id = ?";
        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTable(int id) {
        String sql = "DELETE FROM restaurant_tables WHERE id = ?";
        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTableStatus(String table, int status) {
        String sql = "UPDATE restaurant_tables SET status = ? WHERE name = ?";
        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setString(2, table);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setTableEmpty(String table) {
        return updateTableStatus(table, EMPTY);
    }

    public boolean setTableOccupied(String table) {
        return updateTableStatus(table, OCCUPIED);
    }

}
