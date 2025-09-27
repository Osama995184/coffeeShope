/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import CafeShopSystem.Departments;
import CafeShopSystem.InvoiceItem;
import CafeShopSystem.Invoices;
import CafeShopSystem.database;
import CafeShopSystem.productData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author OSOS
 */
public class salesServices {

    alert al = new alert();

    public ObservableList<productData> menueDataList(TableView<Departments> departmentTable_menuForm) {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql
                = "SELECT p.id, p.model, p.realPrice, p.type, NULL AS quantity, 'product' AS dataType "
                + "FROM product p "
                + "WHERE p.available = 'متاح' AND (? IS NULL OR p.type = ?) "
                + "UNION ALL "
                + "SELECT i.id, i.model, i.realPrice, i.type, i.available, 'inventory' AS dataType "
                + "FROM inventory i "
                + "WHERE i.available > 0 AND (? IS NULL OR i.type = ?) "
                + "ORDER BY model COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            Departments type = departmentTable_menuForm.getSelectionModel().getSelectedItem();
            if (type != null) {
                String dep = type.getDepartment();
                ps.setString(1, dep);
                ps.setString(2, dep);
                ps.setString(3, dep);
                ps.setString(4, dep);
            } else {
                ps.setNull(1, java.sql.Types.VARCHAR);
                ps.setNull(2, java.sql.Types.VARCHAR);
                ps.setNull(3, java.sql.Types.VARCHAR);
                ps.setNull(4, java.sql.Types.VARCHAR);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("realPrice"),
                        rs.getString("type"),
                        rs.getObject("quantity") != null ? rs.getInt("quantity") : 0,
                        rs.getString("dataType")
                );
                listData.add(prodData);
            }
        } catch (SQLException e) {
            al.E_Alert("خطأ في تحميل بيانات القائمة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<Invoices> getAllInvoices() {
        ObservableList<Invoices> invoiceList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM invoices where status = 0 ORDER BY invoice_id DESC"; // هيرجع كل الفواتير بالترتيب من الأحدث للأقدم

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Invoices invoice = new Invoices();

                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setTotal_quantity(rs.getInt("total_quantity"));
                invoice.setTotal_price_before_discount(rs.getDouble("total_price_before_discount"));
                invoice.setDiscount_percentage(rs.getDouble("discount_percentage"));
                invoice.setTotal_price_after_discount(rs.getDouble("total_price_after_discount"));
                invoice.setUsername(rs.getString("username"));
                invoice.setInvoice_date(rs.getString("invoice_date"));
                invoice.setCashPay(rs.getDouble("cashPay"));
                invoice.setInstaPay(rs.getDouble("instaPay"));
                invoice.setVodafonePay(rs.getDouble("vodafonePay"));
                invoice.setClientName(rs.getString("clientName"));
                invoice.setClientPhone(rs.getString("clientPhone"));
                invoice.setShiftsId(rs.getInt("shiftsId"));
                invoice.setReqType(rs.getString("reqType"));
                invoice.setStatus(rs.getInt("status"));
                invoice.setRestTable(rs.getString("resturant_table"));

                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل في جلب بيانات الفواتير", Alert.AlertType.ERROR);
        }

        return invoiceList;
    }

    public void deleteInvoiceAndRestoreQuantities(int invoiceId) {
        String getSales = "SELECT model_id, quantity, inventory_type FROM sales WHERE invoice_id = ?";
        String updateInventory = "UPDATE inventory SET available = available + ? WHERE id = ?";
        String deleteSales = "DELETE FROM sales WHERE invoice_id = ?";
        String deleteInvoice = "DELETE FROM invoices WHERE invoice_id = ?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                     PreparedStatement selectSalesPs = connect.prepareStatement(getSales);  PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory);  PreparedStatement deleteSalesPs = connect.prepareStatement(deleteSales);  PreparedStatement deleteInvoicePs = connect.prepareStatement(deleteInvoice)) {
                // استرجاع الأصناف المرتبطة بالفاتورة
                selectSalesPs.setInt(1, invoiceId);
                try ( ResultSet rs = selectSalesPs.executeQuery()) {
                    while (rs.next()) {
                        String modelId = rs.getString("model_id");
                        int qty = rs.getInt("quantity");
                        String inventoryType = rs.getString("inventory_type");

                        if ("inventory".equalsIgnoreCase(inventoryType)) {
                            updateInventoryPs.setInt(1, qty);
                            updateInventoryPs.setString(2, modelId);
                            updateInventoryPs.addBatch();
                        }
                    }
                }

                // نفذ عملية إرجاع الكميات (لو فيه)
                updateInventoryPs.executeBatch();

                // احذف تفاصيل البيع الخاصة بالـ invoiceId
                deleteSalesPs.setInt(1, invoiceId);
                deleteSalesPs.executeUpdate();

                // احذف الفاتورة نفسها
                deleteInvoicePs.setInt(1, invoiceId);
                deleteInvoicePs.executeUpdate();

                connect.commit();
                al.E_Alert("تم حذف الفاتورة", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                connect.rollback();
                al.E_Alert("حدث خطأ أثناء حذف الفاتورة. تم التراجع عن العملية.", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            al.E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    public void deleteSalesWithoutInvoiceAndRestoreQuantities(int invoiceId) {
        String getSales = "SELECT model_id, quantity, inventory_type FROM sales WHERE invoice_id = ?";
        String updateInventory = "UPDATE inventory SET available = available + ? WHERE id = ?";
        String deleteSales = "DELETE FROM sales WHERE invoice_id = ?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                     PreparedStatement selectSalesPs = connect.prepareStatement(getSales);  PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory);  PreparedStatement deleteSalesPs = connect.prepareStatement(deleteSales);) {
                // استرجاع الأصناف المرتبطة بالفاتورة
                selectSalesPs.setInt(1, invoiceId);
                try ( ResultSet rs = selectSalesPs.executeQuery()) {
                    while (rs.next()) {
                        String modelId = rs.getString("model_id");
                        int qty = rs.getInt("quantity");
                        String inventoryType = rs.getString("inventory_type");

                        if ("inventory".equalsIgnoreCase(inventoryType)) {
                            updateInventoryPs.setInt(1, qty);
                            updateInventoryPs.setString(2, modelId);
                            updateInventoryPs.addBatch();
                        }
                    }
                }

                // نفذ عملية إرجاع الكميات (لو فيه)
                updateInventoryPs.executeBatch();

                // احذف تفاصيل البيع الخاصة بالـ invoiceId
                deleteSalesPs.setInt(1, invoiceId);
                deleteSalesPs.executeUpdate();

                connect.commit();
            } catch (SQLException e) {
                connect.rollback();
                al.E_Alert("حدث خطأ أثناء حذف الفاتورة. تم التراجع عن العملية.", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            al.E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public ObservableList<InvoiceItem> getSalesByInvoiceId(int invoiceId) {
        ObservableList<InvoiceItem> salesList = FXCollections.observableArrayList();

        String sql = "SELECT sales_id, model_id, quantity, total_price, date, invoice_id, "
                + "totalWholesalesPrice, model_name, realprice, inventory_type "
                + "FROM sales WHERE invoice_id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvoiceItem sale = new InvoiceItem();
                    sale.setSalesId(rs.getInt("sales_id"));
                    sale.setModelID(rs.getString("model_id"));
                    sale.setQuantity(rs.getInt("quantity"));
                    sale.setTotalP(rs.getDouble("total_price"));
                    sale.setDate(rs.getTimestamp("date"));
                    sale.setInvoiceId(rs.getInt("invoice_id"));
                    sale.setPrice_beforDis(rs.getDouble("totalWholesalesPrice"));
                    sale.setModel(rs.getString("model_name"));
                    sale.setPrice(rs.getDouble("realprice"));
                    sale.setDataType(rs.getString("inventory_type"));

                    salesList.add(sale);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل في جلب تفاصيل المبيعات", Alert.AlertType.ERROR);
        }

        return salesList;
    }

    public boolean deleteSaleById(int salesId) {
        String getSale = "SELECT model_id, quantity, total_price, totalWholesalesPrice, invoice_id, inventory_type "
                + "FROM sales WHERE sales_id = ?";
        String updateInventory = "UPDATE inventory SET available = available + ? WHERE id = ?";
        String deleteSale = "DELETE FROM sales WHERE sales_id = ?";
        String recalcInvoice
                = "UPDATE invoices SET "
                + "total_price_after_discount = total_price_after_discount - ?"
                + "total_quantity = total_quantity - ?"
                + "WHERE invoice_id = ?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                     PreparedStatement getSalePs = connect.prepareStatement(getSale);  PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory);  PreparedStatement deleteSalePs = connect.prepareStatement(deleteSale);  PreparedStatement updateInvoicePs = connect.prepareStatement(recalcInvoice)) {
                // 1️⃣ هات بيانات البيع
                getSalePs.setInt(1, salesId);
                try ( ResultSet rs = getSalePs.executeQuery()) {
                    if (rs.next()) {
                        String modelId = rs.getString("model_id");
                        int qty = rs.getInt("quantity");
                        double totalPrice = rs.getDouble("total_price");
                        int invoiceId = rs.getInt("invoice_id");
                        String inventoryType = rs.getString("inventory_type");

                        if ("inventory".equalsIgnoreCase(inventoryType)) {
                            updateInventoryPs.setInt(1, qty);
                            updateInventoryPs.setString(2, modelId);
                            updateInventoryPs.executeUpdate();
                        }

                        deleteSalePs.setInt(1, salesId);
                        deleteSalePs.executeUpdate();

                        updateInvoicePs.setDouble(1, totalPrice);   
                        updateInvoicePs.setInt(2, qty);                
                        updateInvoicePs.setInt(5, invoiceId);
                        updateInvoicePs.executeUpdate();

                        connect.commit();
                        al.E_Alert("تم حذف المنتج وتحديث الفاتورة", Alert.AlertType.INFORMATION);
                        return true;
                    } else {
                        al.E_Alert("لم يتم العثور على المنتج المطلوب", Alert.AlertType.ERROR);
                        return false;
                    }
                }

            } catch (SQLException e) {
                connect.rollback();
                e.printStackTrace();
                al.E_Alert("فشل أثناء حذف المنتج. تم التراجع عن العملية.", Alert.AlertType.ERROR);
                return false;
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            return false;
        }
    }

}
