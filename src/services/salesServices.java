/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import CafeShopSystem.Departments;
import CafeShopSystem.InvoiceItem;
import CafeShopSystem.Invoices;
import CafeShopSystem.Sales;
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

    public Double getDiscountInvoice(int invoiceId) {
        String sql = "SELECT discount_percentage FROM invoices WHERE invoice_id = ?";
        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("discount_percentage");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("فشل في جلب نسبة الخصم", Alert.AlertType.ERROR);
        }

        return null; // لو الفاتورة مش موجودة
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

    public ObservableList<InvoiceItem> getAllSales() {
        ObservableList<InvoiceItem> salesList = FXCollections.observableArrayList();

        String sql = "SELECT sales_id, model_id, quantity, total_price, date, invoice_id, "
                + "totalWholesalesPrice, model_name, realprice, inventory_type "
                + "FROM sales";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

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

    public boolean returnItemById(Sales sales, String payType, int quantity, String returnReason) {
        String updateInventory = "UPDATE inventory SET available = available + ? WHERE id = ?";
        String updateSale = "UPDATE sales SET quantity = quantity - ?, total_price = (quantity - ?) * ? WHERE sales_id = ?";
        String deleteSale = "DELETE FROM sales WHERE sales_id = ?";
        String updateInvoice
                = "UPDATE invoices SET "
                + "total_price_after_discount = total_price_after_discount - ?, "
                + "total_price_before_discount = total_price_before_discount - ?, "
                + "total_quantity = total_quantity - ?, "
                + "cashPay = CASE WHEN ? = 'نقدي' THEN cashPay - ? ELSE cashPay END, "
                + "vodafonePay = CASE WHEN ? = 'فودافون كاش' THEN vodafonePay - ? ELSE vodafonePay END, "
                + "instaPay = CASE WHEN ? = 'انستا باي' THEN instaPay - ? ELSE instaPay END "
                + "WHERE invoice_id = ?";
        String insertReturn
                = "INSERT INTO returns (product_id, model_name, invoice_id, quantity_returned, return_reason, price_of_return) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                     PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory);  PreparedStatement updateSalePs = connect.prepareStatement(updateSale);  PreparedStatement deleteSalePs = connect.prepareStatement(deleteSale);  PreparedStatement updateInvoicePs = connect.prepareStatement(updateInvoice);  PreparedStatement insertReturnPs = connect.prepareStatement(insertReturn)) {
                // 1- تحديث المخزون لو الصنف من الـ inventory
                if ("inventory".equalsIgnoreCase(sales.getType())) {
                    updateInventoryPs.setInt(1, quantity);
                    updateInventoryPs.setInt(2, sales.getModel_id());
                    updateInventoryPs.executeUpdate();
                }

                // 2- التعامل مع الـ sales حسب الكمية المرجعة
                if (quantity == sales.getQuantity()) {
                    // حذف الصف
                    deleteSalePs.setInt(1, sales.getSales_id());
                    deleteSalePs.executeUpdate();
                } else if (quantity < sales.getQuantity()) {
                    // تحديث الكمية والسعر
                    updateSalePs.setInt(1, quantity);              // خصم الكمية
                    updateSalePs.setInt(2, quantity);              // نفس الكمية للطرح في المعادلة
                    updateSalePs.setDouble(3, sales.getPrice());   // سعر الوحدة
                    updateSalePs.setInt(4, sales.getSales_id());
                    updateSalePs.executeUpdate();
                } else {
                    // الكمية المرجعة أكبر من المباعة -> إلغاء العملية
                    al.E_Alert("خطأ: الكمية المرجعة أكبر من الكمية المباعة!", Alert.AlertType.ERROR);
                    connect.rollback();
                    return false;
                }

                // 3- تحديث الفاتورة
                double returnTotalPrice = quantity * sales.getPrice(); // قيمة المرتجع

                updateInvoicePs.setDouble(1, returnTotalPrice);
                updateInvoicePs.setDouble(2, returnTotalPrice);
                updateInvoicePs.setInt(3, quantity);

                updateInvoicePs.setString(4, payType);
                updateInvoicePs.setDouble(5, returnTotalPrice);

                updateInvoicePs.setString(6, payType);
                updateInvoicePs.setDouble(7, returnTotalPrice);

                updateInvoicePs.setString(8, payType);
                updateInvoicePs.setDouble(9, returnTotalPrice);

                updateInvoicePs.setInt(10, sales.getInvoice_id());
                updateInvoicePs.executeUpdate();

                // 4- تسجيل المرتجع
                insertReturnPs.setInt(1, sales.getModel_id());
                insertReturnPs.setString(2, sales.getModel_name());
                insertReturnPs.setInt(3, sales.getInvoice_id());
                insertReturnPs.setInt(4, quantity);              // العدد المرجع
                insertReturnPs.setString(5, returnReason);       // سبب المرتجع
                insertReturnPs.setDouble(6, returnTotalPrice);   // قيمة المرتجع
                insertReturnPs.executeUpdate();

                // 5- تأكيد العملية
                connect.commit();
                al.E_Alert("تم إرجاع المنتج وتسجيله في المرتجعات وتحديث الفاتورة", Alert.AlertType.INFORMATION);
                return true;

            } catch (SQLException e) {
                connect.rollback();
                e.printStackTrace();
                al.E_Alert("فشل أثناء إرجاع المنتج. تم التراجع عن العملية.", Alert.AlertType.ERROR);
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

    public boolean returnInvoice(int invoiceId, String payType, String returnReason) {
        String getSales = "SELECT * FROM sales WHERE invoice_id = ?";
        String updateInventory = "UPDATE inventory SET available = available + ? WHERE id = ?";
        String deleteSales = "DELETE FROM sales WHERE invoice_id = ?";
        String updateInvoice
                = "UPDATE invoices SET "
                + "total_price_after_discount = total_price_after_discount - ?, "
                + "total_quantity = total_quantity - ?, "
                + "cashPay = CASE WHEN ? = 'نقدي' THEN cashPay - ? ELSE cashPay END, "
                + "vodafonePay = CASE WHEN ? = 'فودافون كاش' THEN vodafonePay - ? ELSE vodafonePay END, "
                + "instaPay = CASE WHEN ? = 'انستا باي' THEN instaPay - ? ELSE instaPay END "
                + "WHERE invoice_id = ?";
        String insertReturn
                = "INSERT INTO returns (product_id, model_name, invoice_id, quantity_returned, return_reason, price_of_return) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                     PreparedStatement getSalesPs = connect.prepareStatement(getSales);  PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory);  PreparedStatement deleteSalesPs = connect.prepareStatement(deleteSales);  PreparedStatement updateInvoicePs = connect.prepareStatement(updateInvoice);  PreparedStatement insertReturnPs = connect.prepareStatement(insertReturn)) {
                // ✅ هات كل الأصناف الخاصة بالفاتورة
                getSalesPs.setInt(1, invoiceId);

                int totalQty = 0;
                double totalPrice = 0.0;

                try ( ResultSet rs = getSalesPs.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("model_id");
                        String modelName = rs.getString("model_name");
                        int qty = rs.getInt("quantity");
                        double price = rs.getDouble("total_price");
                        String type = rs.getString("inventory_type");

                        totalQty += qty;
                        totalPrice += price;

                        // ✅ رجع للمخزون لو من نوع inventory
                        if ("inventory".equalsIgnoreCase(type)) {
                            updateInventoryPs.setInt(1, qty);
                            updateInventoryPs.setInt(2, productId);
                            updateInventoryPs.addBatch();
                        }

                        // ✅ سجل المرتجع
                        insertReturnPs.setInt(1, productId);
                        insertReturnPs.setString(2, modelName);
                        insertReturnPs.setInt(3, invoiceId);
                        insertReturnPs.setInt(4, qty);
                        insertReturnPs.setString(5, returnReason);
                        insertReturnPs.setDouble(6, price);
                        insertReturnPs.addBatch();
                    }

                    updateInventoryPs.executeBatch();
                    insertReturnPs.executeBatch();
                }

                // ✅ امسح كل الأصناف من sales
                deleteSalesPs.setInt(1, invoiceId);
                deleteSalesPs.executeUpdate();

                // ✅ خصم الإجماليات من الفاتورة
                updateInvoicePs.setDouble(1, totalPrice);  // خصم السعر الكلي
                updateInvoicePs.setInt(2, totalQty);       // خصم الكمية الكلية

                updateInvoicePs.setString(3, payType);
                updateInvoicePs.setDouble(4, totalPrice);

                updateInvoicePs.setString(5, payType);
                updateInvoicePs.setDouble(6, totalPrice);

                updateInvoicePs.setString(7, payType);
                updateInvoicePs.setDouble(8, totalPrice);

                updateInvoicePs.setInt(9, invoiceId);
                updateInvoicePs.executeUpdate();

                connect.commit();
                al.E_Alert("تم إرجاع الفاتورة كاملة وتحديث المخزون والمرتجعات", Alert.AlertType.INFORMATION);
                return true;

            } catch (SQLException e) {
                connect.rollback();
                e.printStackTrace();
                al.E_Alert("فشل أثناء إرجاع الفاتورة. تم التراجع عن العملية.", Alert.AlertType.ERROR);
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
