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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author OSOS
 */
public class delegatorServices {

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;

    public boolean insertDelegator(Delegator delegator) {
        String sql = "INSERT INTO delegator (code, name, remark, target, status, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, delegator.getCode());
            ps.setString(2, delegator.getName());
            ps.setString(3, delegator.getRemark());
            ps.setObject(4, delegator.getTarget());
            ps.setObject(5, delegator.getStatus());
            ps.setString(6, delegator.getAddress());
            ps.setString(7, delegator.getPhone());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDelegator(Delegator delegator) {
        String sql = "UPDATE delegator SET code = ?, name = ?, remark = ?, target = ?, status = ?, address = ?, phone = ? WHERE id = ?";
        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, delegator.getCode());
            ps.setString(2, delegator.getName());
            ps.setString(3, delegator.getRemark());
            ps.setObject(4, delegator.getTarget());
            ps.setObject(5, delegator.getStatus());
            ps.setString(6, delegator.getAddress());
            ps.setString(7, delegator.getPhone());
            ps.setInt(8, delegator.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDelegator(int id) {
        String sql = "DELETE FROM delegator WHERE id = ?";
        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDelegatorStatus(String id, int status) {
        String sql = "UPDATE delegator SET status = ? WHERE name = ?";
        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setDelegatorEmpty(String id) {
        return updateDelegatorStatus(id, INACTIVE);
    }

    public boolean setDelegatorOccupied(String id) {
        return updateDelegatorStatus(id, ACTIVE);
    }

    public List<Delegator> getAllDelegators() {
        List<Delegator> list = new ArrayList<>();
        String sql = "SELECT id, code, name, remark, target, status, address, phone FROM delegator ORDER BY name COLLATE NOCASE";

        try ( Connection conn = database.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Delegator(
                        rs.getInt("id"),
                        (Integer) rs.getObject("code"),
                        rs.getString("name"),
                        rs.getString("remark"),
                        (Double) rs.getObject("target"),
                        (Integer) rs.getObject("status"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Delegator getDelegatorById(int id) {
        String sql = "SELECT id, code, name, remark, target, status, address, phone FROM delegator WHERE id = ?";
        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Delegator(
                        rs.getInt("id"),
                        (Integer) rs.getObject("code"),
                        rs.getString("name"),
                        rs.getString("remark"),
                        (Double) rs.getObject("target"),
                        (Integer) rs.getObject("status"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delegatorExists(List<Delegator> delegatorList, Delegator newDelegator) {
        return delegatorList.stream().anyMatch(d
                -> Objects.equals(d.getName(), newDelegator.getName())
                && Objects.equals(d.getRemark(), newDelegator.getRemark())
                && Objects.equals(d.getAddress(), newDelegator.getAddress())
                && Objects.equals(d.getPhone(), newDelegator.getPhone())
        );
    }

    public boolean delegatorExistsWithoutIdByName(List<Delegator> delegatorList, Delegator newDelegator) {
        return delegatorList.stream().anyMatch(d
                -> !Objects.equals(d.getId(), newDelegator.getId())
                && // id مختلف
                Objects.equals(d.getName(), newDelegator.getName())
        );
    }

    public boolean delegatorExistsWithoutIdByPhone(List<Delegator> delegatorList, Delegator newDelegator) {
        return delegatorList.stream().anyMatch(d
                -> !Objects.equals(d.getId(), newDelegator.getId())
                && Objects.equals(d.getPhone(), newDelegator.getPhone())
        );
    }

    public List<DelegatorReport> getDelegatorReport(LocalDate fromDate, LocalDate toDate) {
        List<DelegatorReport> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT d.id, d.name, d.phone, d.status, "
                + "COUNT(CASE WHEN inv.reqType = 'دليفرى' THEN 1 END) AS delivery_count, "
                + "SUM(CASE WHEN inv.reqType = 'دليفرى' THEN inv.delevary ELSE 0 END) AS total_delivery, "
                + "d.target "
                + "FROM delegator d "
                + "LEFT JOIN invoices inv ON inv.resturant_table = d.name "
                + "WHERE 1=1 "
        );

        if (fromDate != null) {
            sql.append(" AND datetime(inv.invoice_date) >= datetime(?) ");
        }
        if (toDate != null) {
            sql.append(" AND datetime(inv.invoice_date) <= datetime(?) ");
        }

        sql.append(" GROUP BY d.id, d.name, d.phone, d.status, d.target ORDER BY d.name ");

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (fromDate != null) {
                ps.setString(paramIndex++, fromDate.toString()); // بيبقى في شكل "2025-10-30"
            }
            if (toDate != null) {
                ps.setString(paramIndex++, toDate.toString());
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DelegatorReport(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getInt("status"),
                            rs.getInt("delivery_count"),
                            rs.getDouble("total_delivery"),
                            rs.getDouble("target")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<DelegatorReport> getDelegatorOrderReport(LocalDate fromDate, LocalDate toDate, String delegat) {
        List<DelegatorReport> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT inv.resturant_table, inv.orderNumber, inv.delevary, "
                + "CASE WHEN inv.total_price_before_discount = 0.0 THEN 'انتظار' ELSE 'تم التوصيل' END AS statusType, "
                + "inv.total_price_after_discount, inv.invoice_date "
                + "FROM invoices inv "
                + "WHERE inv.reqType = 'دليفرى' "
        );

        if (fromDate != null) {
            sql.append(" AND datetime(inv.invoice_date) >= datetime(?) ");
        }
        if (toDate != null) {
            sql.append(" AND datetime(inv.invoice_date) <= datetime(?) ");
        }

        if (delegat != null && !delegat.isEmpty()) {
            sql.append(" AND inv.resturant_table = ? ");
        }

        sql.append(" ORDER BY inv.invoice_date DESC ");

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (fromDate != null) {
                ps.setString(paramIndex++, fromDate.toString()); // بيبقى في شكل "2025-10-30"
            }
            if (toDate != null) {
                ps.setString(paramIndex++, toDate.toString());
            }
            if (delegat != null && !delegat.isEmpty()) {
                ps.setString(paramIndex++, delegat);
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DelegatorReport(
                            rs.getInt("orderNumber"),
                            rs.getString("resturant_table"),
                            rs.getString("statusType"),
                            rs.getDouble("delevary"),
                            rs.getDouble("total_price_after_discount"),
                            rs.getDate("invoice_date")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
