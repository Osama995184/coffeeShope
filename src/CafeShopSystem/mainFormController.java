package CafeShopSystem;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import services.InventoryData;
import services.TableData;
import services.TableServices;
import services.alert;
import services.functions;
import services.productServices;
import services.salesServices;

public class mainFormController implements Initializable {

    @FXML
    private TextField menu_items_quantity;
    @FXML
    private TableView<Shift> shiftsTable;
    @FXML
    private TableColumn<Shift, String> shifts_col_closedBy;
    @FXML
    private TableColumn<Shift, String> shifts_col_username;
    @FXML
    private TableColumn<Shift, Double> shifts_col_closingBalance;
    @FXML
    private TableColumn<Shift, Double> shifts_col_openingBalance;
    @FXML
    private TableColumn<Shift, String> shifts_col_closingTime;
    @FXML
    private TableColumn<Shift, String> shifts_col_openingTime;
    @FXML
    private TableColumn<Shift, Integer> shifts_col_shiftId;

    @FXML
    private TableView<Sales> salesTable_shifts;
    @FXML
    private TableColumn<Sales, String> Sales_col_date_shifts;
    @FXML
    private TableColumn<Sales, Double> col_totalPrice_shifts;
    @FXML
    private TableColumn<Sales, Integer> col_quantity_shifts;
    @FXML
    private TableColumn<Sales, String> col_type_sales_shifts;
    @FXML
    private TableColumn<Sales, String> col_model_sales_shifts;
    @FXML
    private TableColumn<Sales, String> col_modelId_shifts;
    @FXML
    private TableColumn<Sales, Integer> col_invoiceId_sales_shifts;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button mainPage_btn;

    @FXML
    private Button sell_btn;

    @FXML
    private Button addProduct_btn;

    @FXML
    private Button employee_btn;

    @FXML
    private Button returnedProductBtn;

    @FXML
    private Button capital_btn;

    @FXML
    private Button clients_btn;

    @FXML
    private Button dailySells_btn;

    @FXML
    private Button expenses_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button settings_btn;

    @FXML
    private Label username;

    @FXML
    private AnchorPane settings_form;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> col_employeePassword;

    @FXML
    private TableColumn<Employee, String> col_employeeUsername;

    @FXML
    private TableColumn<Employee, String> col_emplyeeId;

    @FXML
    private TextField emplyeeName;

    @FXML
    private TextField employeePassword;

    @FXML
    private TextField searchEmployee;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField departmentTextField;

    @FXML
    private TextField tableNameTextField;

    @FXML
    private TableView<Departments> departmentTable;

    @FXML
    private TableColumn<Departments, String> col_departmentId;

    @FXML
    private TableColumn<Departments, String> col_dedepartment;

    @FXML
    private TableView<Departments> departmentTable_menuForm;

    @FXML
    private TableView<TableData> tableOfTables;

    @FXML
    private TableColumn<TableData, String> col_tablesName;

    @FXML
    private TableColumn<TableData, String> col_tablesId;

    @FXML
    private TableColumn<Departments, String> col_departmentId_menuForm;

    @FXML
    private TableColumn<Departments, String> col_dedepartment_menuForm;

    @FXML
    private CheckBox mainPage_checkBox;
    @FXML
    private CheckBox returnPage_checkBox;
    @FXML
    private CheckBox addItemsPage_checkBox;
    @FXML
    private CheckBox missingItemsPage_checkBox;
    @FXML
    private CheckBox salesPage_checkBox;
    @FXML
    private CheckBox expensesPage_checkBox;
    @FXML
    private CheckBox clientsPage_checkBox;
    @FXML
    private CheckBox monyPage_checkBox;
    @FXML
    private CheckBox sellPage_checkBox;
    @FXML
    private Button saveRolesBtn;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane open_shifts_form;
    @FXML
    private AnchorPane close_shifts_form;

    @FXML
    private Label cashier_label;
    @FXML
    private Label opening_date_open_shifts_form;

    @FXML
    private TextField opening_balance_open_shifts_form;
    @FXML
    private TextField vodafoneCash_open_shifts_form;
    @FXML
    private TextField cash_open_shifts_form;
    @FXML
    private TextField instaPay_open_shifts_form;

    @FXML
    private Label opening_date_close_shifts_form;
    @FXML
    private Label closing_date_close_shifts_form;
    @FXML
    private TextField opening_balance_close_shifts_form;
    @FXML
    private TextField closing_balance_close_shifts_form;
    @FXML
    private TextField cash_close_shifts_form;
    @FXML
    private TextField vodafoneCash_close_shifts_form;
    @FXML
    private TextField instaPay_close_shifts_form;
    @FXML
    private TableView<Sales> salesTable_close_shifts_form;
    @FXML
    private TableColumn<Sales, String> col_date_close_shifts_form;
    @FXML
    private TableColumn<Sales, Double> col_totalPrice_close_shifts_form;
    @FXML
    private TableColumn<Sales, Integer> col_quantity_close_shifts_form;
    @FXML
    private TableColumn<Sales, String> col_type_sales_close_shifts_form;
    @FXML
    private TableColumn<Sales, String> col_model_sales_close_shifts_form;
    @FXML
    private TableColumn<Sales, Integer> col_modelId_close_shifts_form;
    @FXML
    private TableColumn<Sales, Integer> col_invoiceId_sales_close_shifts_form;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane employee_form;

    @FXML
    private TableView<productData> inventoryTableView;

    @FXML
    private TableColumn<productData, String> inventory_col_realPrice;

    @FXML
    private TableColumn<productData, String> inventory_col_wholesalePrice;

    @FXML
    private TableColumn<productData, String> inventory_col_model;

    @FXML
    private TableColumn<productData, String> inventory_col_id;

    @FXML
    private TableColumn<productData, String> inventory_col_soldNo;

    @FXML
    private TableColumn<productData, String> inventory_col_available;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

    @FXML
    private TableView<InventoryData> stockTableView;

    @FXML
    private TableColumn<InventoryData, String> stock_col_realPrice;

    @FXML
    private TableColumn<InventoryData, String> stock_col_wholesalePrice;

    @FXML
    private TableColumn<InventoryData, String> stock_col_model;

    @FXML
    private TableColumn<InventoryData, String> stock_col_id;

    @FXML
    private TableColumn<InventoryData, String> stock_col_soldNo;

    @FXML
    private TableColumn<InventoryData, String> stock_col_available;

    @FXML
    private TableColumn<InventoryData, String> stock_col_type;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_model;

    @FXML
    private ComboBox<String> inventoryAvailableList;

    @FXML
    private ComboBox<String> tablesList_menuForm;

    @FXML
    private ComboBox<String> requestTypeList_menuForm;

    @FXML
    private ComboBox<String> returnMethodList;

    @FXML
    private TextField inventory_wholesalePrice;

    @FXML
    private TextField inventory_realPrice;
    @FXML
    private TextField stock_productID;

    @FXML
    private TextField stock_model;

    @FXML
    private ComboBox<String> itemTypeListSearch_stockForm;

    @FXML
    private TextField stock_wholesalePrice;

    @FXML
    private TextField stock_realPrice;

    @FXML
    private TextField inventory_Search;
    @FXML
    private TextField stock_Search;

    @FXML
    private TextField barcodeNo_textField;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<InvoiceItem> receipt_tableView;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_model;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_No;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_price;

    @FXML
    private TextField disqaunt_textField;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<Invoices> wait_invoice_tableView;

    @FXML
    private TableColumn<Invoices, String> wait_invoice_col_toatal;

    @FXML
    private TableColumn<Invoices, String> wait_invoice_col_table;

    @FXML
    private TableColumn<Invoices, String> wait_invoice_col_invId;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane menu_form;

    @FXML
    private TextField menu_cashPay;

    @FXML
    private TextField menu_VodafonePay;

    @FXML
    private TextField menu_instaPay;

    @FXML
    private TableView<productData> menuTableView;

    @FXML
    private TableColumn<productData, String> menu_col_realPrice;

    @FXML
    private TableColumn<productData, String> menu_col_model;

    @FXML
    private TableColumn<productData, String> menu_col_id;

    @FXML
    private TextField menu_productID;

    @FXML
    private TextField menu_model;

//    @FXML
//    private TextField menu_realPrice;
    @FXML
    private TextField menu_Search;

    @FXML
    private TextField menu_clientName;

    @FXML
    private TextField menu_clientPhone;

    @FXML
    private TextField menu_clientAddress;

    @FXML
    private TextField delivery_price;
    @FXML
    private TextField stock_QBox;
    @FXML
    private TextField stock_Quantity;

    @FXML
    private ComboBox<String> itemTypeList_salesForm;

    @FXML
    private ComboBox<String> itemTypeList_shiftsForm;

    @FXML
    private ComboBox<String> itemTypeList_addForm;

    @FXML
    private ComboBox<String> itemTypeList_stockForm;

    @FXML
    private ComboBox<String> itemTypeListSearch_addForm;

    @FXML
    public Label menu_total;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane returned_form;

    @FXML
    private TableView<Sales> returnTableView;

    @FXML
    private TableColumn<Sales, String> return_col_date;

    @FXML
    private TableColumn<Sales, String> return_col_totalPrice;

    @FXML
    private TableColumn<Sales, String> return_col_quantity;

    @FXML
    private TableColumn<Sales, String> return_col_type;

    @FXML
    private TableColumn<Sales, String> return_col_model;

    @FXML
    private TableColumn<Sales, String> return_col_modelsId;

    @FXML
    private TableColumn<Sales, String> return_col_invoicesId;

    @FXML
    private Spinner<Integer> return_spinner_no;

    @FXML
    private TextField return_productID;

    @FXML
    private TextField return_model;

    @FXML
    private TextField return_type;

    @FXML
    private TextField return_realPrice;

    @FXML
    private TextField retrun_Search_field;

    @FXML
    private TextField return_reciptID;

    @FXML
    private TextField return_reson;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane product_form;
    @FXML
    private TableView<productData> missingProductTableView;
    @FXML
    private TableColumn<productData, String> missing_col_realPrice;
    @FXML
    private TableColumn<productData, String> missing_col_wholesalePrice;
    @FXML
    private TableColumn<productData, String> missing_col_mNo;
    @FXML
    private TableColumn<productData, String> missing_col_mSize;
    @FXML
    private TableColumn<productData, String> missing_col_mcolor;
    @FXML
    private TableColumn<productData, String> missing_col_model;
    @FXML
    private TableColumn<productData, String> missing_col_id;
    @FXML
    private TableColumn<productData, String> missing_col_soldNo;
    @FXML
    private TableColumn<productData, String> missing_col_type;
    @FXML
    private TextField missingProduct_Search;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<Returns> returnProductTableView;

    @FXML
    private TableColumn<Returns, String> returnProduct_col_id;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelID;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_invoiceID;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_quantity;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_reson;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_money;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_date;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelSize;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelColor;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelName;

    @FXML
    private TextField returnProduct_Search;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane sales_form;

    @FXML
    private TableView<Invoices> invoiceTable;
    @FXML
    private TableColumn<Invoices, Integer> col_invoiceID;
    @FXML
    private TableColumn<Invoices, Integer> col_totalQty;
    @FXML
    private TableColumn<Invoices, Double> col_totalBeforeDiscount;
    @FXML
    private TableColumn<Invoices, Double> col_discount;
    @FXML
    private TableColumn<Invoices, Double> col_totalAfterDiscount;
    @FXML
    private TableColumn<Invoices, Double> col_cash;
    @FXML
    private TableColumn<Invoices, Double> col_insta;
    @FXML
    private TableColumn<Invoices, Double> col_voda;
    @FXML
    private TableColumn<Invoices, String> col_username;
    @FXML
    private TableColumn<Invoices, String> col_date;
    @FXML
    private TableColumn<Invoices, String> col_clientPhone_salesForm;
    @FXML
    private TableColumn<Invoices, String> col_clientName_salesForm;

    @FXML
    private TextField invoice_search;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField search_shifts;

    @FXML
    private TextField salesShifts_search;

    @FXML
    private DatePicker fromDatePicker_shifts;

    @FXML
    private DatePicker toDatePicker_shifts;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<Sales> salesTable;

    @FXML
    private TableColumn<Sales, Integer> col_invoiceId_sales;
    @FXML
    private TableColumn<Sales, Integer> col_modelId;
    @FXML
    private TableColumn<Sales, Integer> col_quantity;
    @FXML
    private TableColumn<Sales, Double> col_totalPrice;
    @FXML
    private TableColumn<Sales, String> Sales_col_date;
    @FXML
    private TableColumn<Sales, String> col_model_sales;
    @FXML
    private TableColumn<Sales, String> col_type_sales;
    @FXML
    private TextField salesModelID_search;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane expenses_form;
    @FXML
    private TableView<Expenses> expensesTable;
    @FXML
    private TableColumn<Expenses, Integer> col_expenseId;
    @FXML
    private TableColumn<Expenses, Double> col_amount;
    @FXML
    private TableColumn<Expenses, Double> col_cash_expenses;
    @FXML
    private TableColumn<Expenses, Double> col_vodafone;
    @FXML
    private TableColumn<Expenses, Double> col_instaPay;
    @FXML
    private TableColumn<Expenses, String> col_reason;
    @FXML
    private TableColumn<Expenses, String> col_emUsername_expenses;
    @FXML
    private TableColumn<Expenses, String> col_withdrawnBy;
    @FXML
    private TableColumn<Expenses, String> col_date_expenses;
    @FXML
    private TableColumn<Expenses, String> col_payStatues_expenses;
    @FXML
    private TextField withdrawnBy_field;
    @FXML
    private TextField reason_field;
    @FXML
    private TextField cash_field;
    @FXML
    private TextField vodafone_field;
    @FXML
    private TextField instaPay_field;
    @FXML
    private TextField searchByName;
    @FXML
    private TextField totalOutgoing_expenses;
    @FXML
    private TextField totalExpenses_expenses;
    @FXML
    private ComboBox<String> statues_list_expenses;
    @FXML
    private DatePicker fromDatePickerEx;
    @FXML
    private DatePicker toDatePickerEx;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane capital_form;
    @FXML
    private AnchorPane clients_form;
    @FXML
    private TextField total_sales_wholesale_price;
    @FXML
    private TextField total_sales_Real_price;
    @FXML
    private TextField profit_without_deducting_expenses;
    @FXML
    private TextField tota_expenses_field;
    @FXML
    private TextField total_vodafoneCash_field;
    @FXML
    private TextField total_Cash_field;
    @FXML
    private TextField total_instaPay_field;
    @FXML
    private TextField NetProfit_field;
    @FXML
    private TextField totalOutgoing_capital;
    @FXML
    private TextField NetProfit_field_withOutgoing;
    @FXML
    private TextField totalAvailableWholeprice_field;
    @FXML
    private TextField totalAvailableRealprice_field;
    @FXML
    private Label Money_safe;
    @FXML
    private Label UnavailbleProductCount_label;
    @FXML
    private Label AvailbleProductCount_label;
    @FXML
    private DatePicker fromDatePickerCapital;
    @FXML
    private DatePicker toDatePickerCapital;
    @FXML
    private Label totalPriceCurrentMonth_field;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField clientSearch;
    @FXML
    private TextField salesModelID_search_flientForm;
    @FXML
    private DatePicker fromDatePickerClient;
    @FXML
    private DatePicker toDatePickerClient;
    @FXML
    private TableView<ClientSale> clientSalesTable;
    @FXML
    private TableColumn<ClientSale, Integer> clientSales_col_modelId;
    @FXML
    private TableColumn<ClientSale, Integer> clientSales_col_invoiceId;
    @FXML
    private TableColumn<ClientSale, Double> clientSales_col_totalPrice;
    @FXML
    private TableColumn<ClientSale, String> clientSales_col_model_sales;
    @FXML
    private TableColumn<ClientSale, String> clientSales_col_quantity;
    @FXML
    private TableColumn<ClientSale, String> clientSales_col_date;

    @FXML
    private TableView<Clients> clientsTable;
    @FXML
    private TableColumn<Clients, Integer> clients_col_invoiceID;
    @FXML
    private TableColumn<Clients, Integer> clients_col_totalQty;
    @FXML
    private TableColumn<Clients, Double> clients_col_totalAfterDiscount;
    @FXML
    private TableColumn<Clients, String> clients_col_clientName_clientForm;
    @FXML
    private TableColumn<Clients, String> clients_col_clientPhone_clientForm;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private Label Safe_main;
    @FXML
    private Label today_salaries;
    @FXML
    private Label unavailable_main;
    @FXML
    private Label available_main;
    @FXML
    private AreaChart<String, Number> dailySalesChart;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<ProfitReport> profitReportTable;
    @FXML
    private TableColumn<ProfitReport, Double> gain_col;
    @FXML
    private TableColumn<ProfitReport, Double> price_col;
    @FXML
    private TableColumn<ProfitReport, Double> wholesales_col;
    @FXML
    private TableColumn<ProfitReport, Integer> quantity_col;
    @FXML
    private TableColumn<ProfitReport, String> type_col;

    //////////////////////////////////////////////////////////////////////////////////////////////
    private final String[] Available = {"متاح", "غير متاح"};
    private final String[] RequestType = {"تيك اواي", "صالة"};
    private final String[] RMethod = {"نقدي", "فودافون كاش", "انستا باي"};
    private final String[] EStatues = {"المصروفات", "الخوارج"};

    private Alert alert;

    List<TableData> tableDataList = new ArrayList<>();
    productServices proServices = new productServices();
    TableServices tabserves = new TableServices();
    functions func = new functions();
    salesServices servicesSales = new salesServices();
    alert al = new alert();

    private Image image;

    public void E_Alert(String ms, AlertType Atype) {
        alert = new Alert(Atype);
        alert.setTitle("Warning Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
        alert.showAndWait();
    }

    public void Confirmation_Alert(String ms) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Warning Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
    }

    public boolean isntNumeric(String str) {
        try {
            if (Double.parseDouble(str) < 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public boolean checkTextField() {
        return (inventory_model.getText().isEmpty())
                || (inventory_wholesalePrice.getText().isEmpty())
                || (itemTypeList_addForm.getSelectionModel().getSelectedItem() == null)
                || (inventoryAvailableList.getSelectionModel().getSelectedItem() == null)
                || (isntNumeric(inventory_wholesalePrice.getText()))
                || (isntNumeric(inventory_realPrice.getText()));
    }

    public boolean checkTextFieldinventory() {
        return (stock_model.getText().isEmpty())
                || (itemTypeList_stockForm.getSelectionModel().getSelectedItem() == null)
                || (isntNumeric(stock_realPrice.getText()))
                || (isntNumeric(stock_wholesalePrice.getText()))
                || (isntNumeric(stock_QBox.getText()))
                || (isntNumeric(stock_Quantity.getText()));
    }

    public void inventoryAddbtn() {
        if (checkTextField()) {
            E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
            return;
        }
        data.path = "";
        String path = data.path.replace("\\", "\\\\");
//        String invoiceID = inventory_invoicesID.getText().trim();
        String checkProductSQL = "SELECT model FROM product WHERE model = ?";
        String insertProductSQL = "INSERT INTO product "
                + "(model, wholesaleprice, realprice, type, available) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection()) {

            // 2. تحقق من تكرار المنتج
            try (PreparedStatement checkProductStmt = connect.prepareStatement(checkProductSQL)) {
                checkProductStmt.setString(1, inventory_model.getText());
                try (ResultSet rs = checkProductStmt.executeQuery()) {
                    if (rs.next()) {
                        E_Alert("هذا المنتج موجود بالفعل", AlertType.ERROR);
                        return;
                    }
                }
            }

            // 3. تنفيذ الإضافة
            try (PreparedStatement insertStmt = connect.prepareStatement(insertProductSQL)) {
                insertStmt.setString(1, inventory_model.getText());
                insertStmt.setString(2, inventory_wholesalePrice.getText());
                insertStmt.setString(3, inventory_realPrice.getText());
                insertStmt.setString(4, itemTypeList_addForm.getSelectionModel().getSelectedItem());
                insertStmt.setString(5, inventoryAvailableList.getSelectionModel().getSelectedItem());
                insertStmt.executeUpdate();
                E_Alert("تم حفظ المنتج بنجاح", AlertType.INFORMATION);
                inventoryShowData();
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حفظ المنتج:\n" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void stockAddbtn() {
        if (checkTextFieldinventory()) {
            al.E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
            return;
        }
        InventoryData data = new InventoryData();
        int qBox = Integer.parseInt(stock_QBox.getText());
        int quantity = Integer.parseInt(stock_Quantity.getText());

        data.setAvailableNo(qBox * quantity);
        data.setCount(quantity);
        data.setQuantity(qBox);
        data.setType(itemTypeList_stockForm.getSelectionModel().getSelectedItem());
        data.setModel(stock_model.getText());
        data.setWholesalePrice(Double.parseDouble(stock_wholesalePrice.getText()));
        data.setRealPrice(Double.parseDouble(stock_realPrice.getText()));

        if (proServices.insertInventoryData(data)) {
            al.E_Alert("تم اضافة المنتج بنجاح", AlertType.INFORMATION);
            stockShowData();
            stockClearBtn();
        } else {
            al.E_Alert("حدث خطأ اثناء اضافة المنتج", AlertType.ERROR);
        }

    }

    public void inventoryUpdateBtn() {
        productData prodData = inventoryTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            E_Alert("حدد المنتج المراد تعديلة", AlertType.ERROR);
        } else if (checkTextField()) {
            E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
        } else {
            Confirmation_Alert("هل انت متاكد من تعديل بيانات المنتج؟");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                String newModel = inventory_model.getText();
                String productId = inventory_productID.getText();

                String checkQuery = "SELECT COUNT(*) FROM product WHERE model = ? AND id <> ?";

                try (Connection connect = database.getConnection(); PreparedStatement checkPs = connect.prepareStatement(checkQuery)) {

                    checkPs.setString(1, newModel);
                    checkPs.setString(2, productId);

                    ResultSet rs = checkPs.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    if (count > 0) {
                        // الاسم موجود قبل كده
                        E_Alert("الاسم الذي ادخلته موجود بالفعل لمنتج آخر!", AlertType.ERROR);
                        return; // وقف العملية
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    E_Alert("حدث خطأ أثناء فحص البيانات", AlertType.ERROR);
                    return;
                }

                // لو مفيش تكرار يبدأ التعديل
                String updateData = "UPDATE product SET "
                        + "model = ?, "
                        + "wholesaleprice = ?, "
                        + "realprice = ?, "
                        + "type = ?, "
                        + "available = ? "
                        + "WHERE id = ?";

                try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(updateData)) {

                    ps.setString(1, newModel);
                    ps.setString(2, inventory_wholesalePrice.getText());
                    ps.setString(3, inventory_realPrice.getText());
                    ps.setString(4, (String) itemTypeList_addForm.getSelectionModel().getSelectedItem());
                    ps.setString(5, (String) inventoryAvailableList.getSelectionModel().getSelectedItem());
                    ps.setString(6, productId);

                    ps.executeUpdate();
                    inventoryShowData();
                    E_Alert("تم تعديل بيانات المنتج بنجاح", AlertType.INFORMATION);

                } catch (SQLException e) {
                    E_Alert("حدث خطأ أثناء التعديل", AlertType.ERROR);
                    e.printStackTrace();
                }

            } else {
                E_Alert("تم االغاء التعديل", AlertType.ERROR);
            }
        }
    }

    public void stockUpdateBtn() {
        InventoryData prodData = stockTableView.getSelectionModel().getSelectedItem();
        if (checkTextFieldinventory()) {
            al.E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
            return;
        }
        InventoryData data = new InventoryData();
        int qBox = Integer.parseInt(stock_QBox.getText());
        int quantity = Integer.parseInt(stock_Quantity.getText());

        data.setAvailableNo(qBox * quantity);
        data.setCount(quantity);
        data.setQuantity(qBox);
        data.setId(prodData.getId());
        data.setType(itemTypeList_stockForm.getSelectionModel().getSelectedItem());
        data.setModel(stock_model.getText());
        data.setWholesalePrice(Double.parseDouble(stock_wholesalePrice.getText()));
        data.setRealPrice(Double.parseDouble(stock_realPrice.getText()));

        if (proServices.updateInventoryData(data)) {
            al.E_Alert("تم تعديل المنتج بنجاح", AlertType.INFORMATION);
            stockShowData();
            stockClearBtn();
        } else {
            al.E_Alert("حدث خطأ اثناء اضافة المنتج", AlertType.ERROR);
        }
    }

    public void inventoryDeleteBtn() {
        productData prodData = inventoryTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            E_Alert("حدد المنتج المراد حذفة", AlertType.ERROR);
            return;
        }

        Confirmation_Alert("هل انت متاكد من حذف المنتج ؟");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            String deleteData = "DELETE FROM product WHERE id = ?";

            try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(deleteData)) {

                ps.setInt(1, prodData.getId());
                ps.executeUpdate();

                inventoryShowData();
                E_Alert("تم حذف المنتج بنجاح", AlertType.INFORMATION);

            } catch (SQLException e) {
                E_Alert("حدث خطأ أثناء حذف المنتج", AlertType.ERROR);
                e.printStackTrace();
            }

        } else {
            E_Alert("تم االغاء الحذف", AlertType.ERROR);
        }
    }

    public void stockDeleteBtn() {
        InventoryData prodData = stockTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            al.E_Alert("حدد المنتج المراد حذفة", AlertType.ERROR);
            return;
        }

        al.Confirmation_Alert("هل انت متاكد من حذف المنتج ؟");
        Optional<ButtonType> option = al.getAlert().showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            if (proServices.deleteData(prodData.getId())) {
                al.E_Alert("تم حذف المنتج بنجاح", AlertType.INFORMATION);
                stockShowData();
                stockClearBtn();
            } else {
                al.E_Alert("حدث خطأ اثناء حذف المنتج", AlertType.ERROR);
            }

        } else {
            al.E_Alert("تم االغاء الحذف", AlertType.ERROR);
        }
    }

    public void inventoryClearBtn() {
        inventory_Search.clear();
        inventory_model.clear();
        inventory_wholesalePrice.clear();
        inventory_realPrice.clear();
        inventory_productID.clear();
        itemTypeListSearch_addForm.getSelectionModel().clearSelection();
        inventoryAvailableList.getSelectionModel().clearSelection();
        itemTypeList_addForm.getSelectionModel().clearSelection();
    }

    public void stockClearBtn() {
        stock_Search.clear();
        stock_model.clear();
        stock_wholesalePrice.clear();
        stock_realPrice.clear();
        stock_productID.clear();
        stock_Quantity.clear();
        stock_QBox.clear();
        barcodeNo_textField.clear();
        itemTypeListSearch_stockForm.getSelectionModel().clearSelection();
        itemTypeList_stockForm.getSelectionModel().clearSelection();
    }

    // MERGE ALL DATAS
    public ObservableList<productData> inventoryDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();
        String type = null;
        type = itemTypeListSearch_addForm.getSelectionModel().getSelectedItem();
        String sql = null;
        if (type != null) {
            sql = "SELECT * FROM product WHERE type LIKE '" + type + "' ORDER BY model COLLATE NOCASE";
        } else {
            sql = "SELECT * FROM product ORDER BY model COLLATE NOCASE";
        }

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("wholesaleprice"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getInt("soldNo"),
                        rs.getString("available")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ اثناء تحميل المنتجات", AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public void inventorySearchAndFilter() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String keyword = inventory_Search.getText(); // TextField بتاع البحث
        String type = itemTypeListSearch_addForm.getSelectionModel().getSelectedItem(); // ComboBox بتاع النوع

        String sql = "SELECT * FROM product WHERE 1=1";

        // لو فيه نوع محدد
        if (type != null && !type.isEmpty()) {
            sql += " AND type LIKE ?";
        }

        // البحث في model OR type OR id
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (model LIKE ? OR type LIKE ? OR id LIKE ? OR available LIKE ?)";
        }

        sql += " ORDER BY model COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            int paramIndex = 1;

            if (type != null && !type.isEmpty()) {
                ps.setString(paramIndex++, "%" + type + "%");
            }
            if (keyword != null && !keyword.isEmpty()) {
                String searchParam = "%" + keyword + "%";
                ps.setString(paramIndex++, searchParam); // model
                ps.setString(paramIndex++, searchParam); // type
                ps.setString(paramIndex++, searchParam); // id
                ps.setString(paramIndex++, searchParam); // id
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo"),
                            rs.getString("available")
                    );
                    listData.add(prodData);
                }
            }

            // عرض النتيجة في الجدول
            inventoryTableView.setItems(listData);

        } catch (SQLException e) {
            E_Alert("حدث خطأ اثناء البحث عن المنتجات", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();
        inventory_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        inventory_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        inventory_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        inventory_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_soldNo.setCellValueFactory(new PropertyValueFactory<>("soldNo"));
        inventory_col_available.setCellValueFactory(new PropertyValueFactory<>("available"));

        inventoryTableView.setItems(inventoryListData);
    }

    private ObservableList<InventoryData> stockListData = FXCollections.observableArrayList();

    public void stockShowData() {
        String type;
        if (itemTypeListSearch_stockForm == null || itemTypeListSearch_stockForm.getSelectionModel().getSelectedItem() == null) {
            type = null;
        } else {
            type = itemTypeListSearch_stockForm.getSelectionModel().getSelectedItem();
        }
        stockListData = proServices.getAllData(type);
        stock_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        stock_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stock_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        stock_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        stock_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        stock_col_soldNo.setCellValueFactory(new PropertyValueFactory<>("soldNo"));
        stock_col_available.setCellValueFactory(new PropertyValueFactory<>("availableNo"));

        stockTableView.setItems(stockListData);

        func.setupTableSearchFilter(stock_Search, stockTableView, stockListData, Arrays.asList("model", "type", "id"));
    }

    public void inventoryAvailableList() {
        List<String> avilable = new ArrayList<>();
        for (String data : Available) {
            avilable.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(Available);
        inventoryAvailableList.setItems(listData);
    }

    public void requestTypeList() {
        List<String> request = new ArrayList<>();
        for (String data : RequestType) {
            request.add(data);
        }

        if (tablesList_menuForm.getItems() != null) {
            tablesList_menuForm.getItems().clear();
        }
        for (TableData t : tableDataList) {
            tablesList_menuForm.getItems().add(t.getName());
        }

        ObservableList listData = FXCollections.observableArrayList(RequestType);
        requestTypeList_menuForm.setItems(listData);
//        ObservableList listData2 = FXCollections.observableArrayList(tableDataList);
//        tablesList_menuForm.setItems(listData2);
    }

    public void inventorySelectData() {
        productData prodData = inventoryTableView.getSelectionModel().getSelectedItem();
//        int num = inventoryTableView.getSelectionModel().getSelectedIndex();
        if (prodData == null) {
            return;
        }

        inventory_productID.setText(Integer.toString(prodData.getId()));
        inventory_model.setText(prodData.getModel());
        inventory_wholesalePrice.setText(Double.toString(prodData.getWholesalePrice()));
        inventory_realPrice.setText(Double.toString(prodData.getRealPrice()));
        itemTypeList_addForm.getSelectionModel().select(prodData.getType());
        inventoryAvailableList.getSelectionModel().select(prodData.getAvailable());
    }

    public void stockSelectData() {
        InventoryData prodData = stockTableView.getSelectionModel().getSelectedItem();
        int num = prodData.getAvailableNo();
        if (prodData == null) {
            return;
        }

        stock_productID.setText(Integer.toString(prodData.getId()));
        stock_model.setText(prodData.getModel());
        stock_wholesalePrice.setText(Double.toString(prodData.getWholesalePrice()));
        stock_realPrice.setText(Double.toString(prodData.getRealPrice()));
        itemTypeList_stockForm.getSelectionModel().select(prodData.getType());
        barcodeNo_textField.setText(num + "");
        stock_QBox.setText(Integer.toString(prodData.getQuantity()));
        stock_Quantity.setText(Integer.toString(prodData.getCount()));
    }

    public void addInvoicesTab() {
        inventoryShowData();
        loadDepartmentsIntoComboBox();
        inventoryAvailableList();
    }

    public void printBarcode() {
        InputStream reportStream = null;
        int numberOfCopies = 0;
        try {
            numberOfCopies = Integer.parseInt(barcodeNo_textField.getText());
        } catch (Exception e) {
            E_Alert("ادخل عدد النسخ عبارة عن ارقام", AlertType.ERROR);
            return;
        }

        try {
            productData prodData = inventoryTableView.getSelectionModel().getSelectedItem();

            if (prodData == null || numberOfCopies <= 0) {
                E_Alert("حدد المنتج وعدد النسخ", AlertType.ERROR);
                return;
            } else {
                // Load JRXML
                reportStream = getClass().getResourceAsStream("/reports/barcode_1_1.jrxml");
                if (reportStream == null) {
                    E_Alert("لم يتم العثور على ملف التصميم الباركود", AlertType.ERROR);
                    return;
                }

                JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

                // Build fake data source with N rows
                List<Map<String, ?>> barcodeList = new ArrayList<>();
                for (int i = 0; i < numberOfCopies; i++) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("storeName", data.storeName);
                    parameters.put("barcodeNum", prodData.getId() + "");
                    parameters.put("barcode", "Code: " + prodData.getId());
                    parameters.put("modelName", prodData.getModel());
                    parameters.put("price", " price: " + prodData.getRealPrice());
                    barcodeList.add(parameters);
                }

                JRDataSource dataSource = new JRMapCollectionDataSource(barcodeList);

                // Compile and print
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
                JasperPrintManager.printReport(jasperPrint, true);
                E_Alert("تم طباعة الباركود بنجاح", AlertType.INFORMATION);
            }

        } catch (Exception e) {
            E_Alert("حدث خطاء اثناء طباعة الباركود: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (reportStream != null) {
                    reportStream.close();
                }
            } catch (IOException ioEx) {
                E_Alert("حدث خطاء اثناء طباعة الباركود: " + ioEx.getMessage(), AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<Employee> emplyeeDataList() {
        ObservableList<Employee> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee ORDER BY username COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                listData.add(employee);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ اثناء تحميل بيانات الموظفين", AlertType.ERROR);
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Employee> emplyeeDataList;

    public void employeddShowData() {
        emplyeeDataList = emplyeeDataList();
        col_emplyeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_employeeUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_employeePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        employeeTable.setItems(emplyeeDataList);
    }

    public void employeeSelectData() {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return;
        }

        emplyeeName.setText(employee.getUsername());
        employeePassword.setText(employee.getPassword());
        rolesGetData(employee.getId());
    }

    public void employeeRemoveBtn() {
        emplyeeName.clear();
        employeePassword.clear();
    }

    public void employeesUpdateBtn() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
//        String userName = emplyeeName.getText();
        String password = employeePassword.getText();
        if (selected == null) {
            E_Alert("للتعديل يجب اختيار المستخدم اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        if (password.length() < 8) {
            E_Alert("الرقم السري يجب الا يقل عن 8 احرف", Alert.AlertType.ERROR);
            return;
        }

//        String sql = "UPDATE employee SET username=?, password=? WHERE id=?";
        String sql = "UPDATE employee SET password=? WHERE id=?";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, userName);
            pstmt.setString(1, password);
            pstmt.setInt(2, selected.getId());
            pstmt.executeUpdate();
            E_Alert("تم تعديل بيانات المستخدم بنجاح", Alert.AlertType.INFORMATION);
            employeddShowData();
            employeeRemoveBtn();
        } catch (SQLException e) {
            E_Alert("فشل تعديل بيانات المستخدم" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void employeeDeleteBtn() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            E_Alert("للحذف يجب اختيار موظف اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        if (selected.getUsername().equals("admin")) {
            E_Alert("لا يمكن حذف هذا المستخدم", Alert.AlertType.ERROR);
        } else {
            String sql = "DELETE FROM employee WHERE id=?";

            try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selected.getId());
                pstmt.executeUpdate();
                E_Alert("تم حذف المستخدم بنجاح", Alert.AlertType.INFORMATION);
                employeddShowData();
                employeeRemoveBtn();
            } catch (SQLException e) {
                E_Alert("فشل حذف المستخدم" + e, Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }

    }

    public void searchEmployeeFromDB() {
        ObservableList<Employee> searchResults = FXCollections.observableArrayList();
        String searchText = searchEmployee.getText().trim();
        String sql = "SELECT * FROM employee WHERE username LIKE ? OR password LIKE ? ORDER BY username COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                    searchResults.add(employee);
                }
            }
            employeeTable.setItems(searchResults);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<Departments> departmentDataList() {
        ObservableList<Departments> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM department ORDER BY department COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departments departments = new Departments(
                        rs.getInt("id"),
                        rs.getString("department")
                );
                listData.add(departments);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ اثناء تحميل البنود", AlertType.ERROR);
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Departments> departmentDataList;

    public void departmentShowData() {
        departmentDataList = departmentDataList();
        col_departmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_dedepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentTable.setItems(departmentDataList);
        loadDepartmentsIntoComboBox();
    }

    public void departmentSelectData() {
        Departments department = departmentTable.getSelectionModel().getSelectedItem();
        if (department == null) {
            return;
        }

        departmentTextField.setText(department.getDepartment());
    }

    public void departmentSelectData_menuForm() {
//        Departments department = departmentTable.getSelectionModel().getSelectedItem();
        menueShowData();
    }

    public void departmentRemoveBtn() {
        departmentTextField.clear();
    }

    public void departmentDeleteBtn() {
        Departments selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            E_Alert("للحذف يجب اختيار موظف اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        String sql = "DELETE FROM department WHERE id=?";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selected.getId());
            pstmt.executeUpdate();
            E_Alert("تم حذف البند بنجاح", Alert.AlertType.INFORMATION);
            departmentShowData();
            departmentRemoveBtn();

        } catch (SQLException e) {
            E_Alert("فشل الحذف" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void departmentAddbtn() {
        String department = departmentTextField.getText().trim();

        if (department.isEmpty()) {
            E_Alert("يجب ادخال البند اولا", Alert.AlertType.ERROR);
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM department WHERE department = ?";
        String insertSql = "INSERT INTO department (department) VALUES (?)";

        try (Connection conn = database.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, department);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                E_Alert("هذا البند موجود بالفعل!", Alert.AlertType.WARNING);
                return;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, department);
                insertStmt.executeUpdate();
                E_Alert("تم اضافة البند بنجاح", Alert.AlertType.INFORMATION);
                departmentShowData(); // reload table
                departmentRemoveBtn();
            }

        } catch (SQLException e) {
            E_Alert("فشل اضافة بند جديد:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadDepartmentsIntoComboBox() {
        String sql = "SELECT id, department FROM department";
        ObservableList<Departments> departmentList = FXCollections.observableArrayList();

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            if (itemTypeListSearch_addForm != null) {
                itemTypeListSearch_addForm.getItems().clear();
            } else {
                itemTypeListSearch_addForm = new ComboBox<>();
            }

            if (itemTypeListSearch_stockForm != null) {
                itemTypeListSearch_stockForm.getItems().clear();
            } else {
                itemTypeListSearch_stockForm = new ComboBox<>();
            }

            if (itemTypeList_stockForm != null) {
                itemTypeList_stockForm.getItems().clear();
            } else {
                itemTypeList_stockForm = new ComboBox<>();
            }

            if (itemTypeList_addForm != null) {
                itemTypeList_addForm.getItems().clear();
            } else {
                itemTypeList_addForm = new ComboBox<>();
            }

            if (itemTypeList_shiftsForm != null) {
                itemTypeList_shiftsForm.getItems().clear();
            } else {
                itemTypeList_shiftsForm = new ComboBox<>();
            }

            if (itemTypeList_salesForm != null) {
                itemTypeList_salesForm.getItems().clear();
            } else {
                itemTypeList_salesForm = new ComboBox<>();
            }

            boolean check = false;
            while (rs.next()) {
                check = true;
                String department = rs.getString("department");
                int id = rs.getInt("id");

                // Add to ComboBoxes
                itemTypeListSearch_addForm.getItems().add(department);
                itemTypeListSearch_stockForm.getItems().add(department);
                itemTypeList_stockForm.getItems().add(department);
                itemTypeList_addForm.getItems().add(department);
                itemTypeList_salesForm.getItems().add(department);
                itemTypeList_shiftsForm.getItems().add(department);

                // Add to TableView
                departmentList.add(new Departments(id, department));
            }

            if (!check) {
                E_Alert("لا يوجد بنود مسجلة", AlertType.WARNING);
                return;
            }
            col_dedepartment_menuForm.setCellValueFactory(new PropertyValueFactory<>("department"));
            col_departmentId_menuForm.setCellValueFactory(new PropertyValueFactory<>("id"));
            departmentTable_menuForm.setItems(departmentList);

        } catch (SQLException e) {
            E_Alert("فشل تحميل البنود:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> menueListData;

    public void menueShowData() {
        menueListData = servicesSales.menueDataList(departmentTable_menuForm);
        menu_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        menu_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        menu_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        menuTableView.setItems(menueListData);

        func.setupTableSearchFilter(menu_Search, menuTableView, menueListData, Arrays.asList("model"));

    }

    private SpinnerValueFactory<Integer> spin;

    @FXML
    private void menueSelectData(MouseEvent event) {
        productData selectedItem = menuTableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {  // Double click
            if (selectedItem != null) {
                addToReceipt();
            }
        }

    }

    Boolean WaitChice;

    @FXML
    private void invoiceDataWaiting(MouseEvent event) {
        Invoices selectedItem = wait_invoice_tableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 3) {
            if (selectedItem != null) {
                String tableName = selectedItem.getRestTable();
                servicesSales.deleteInvoiceAndRestoreQuantities(selectedItem.getInvoice_id());
                tabserves.setTableEmpty(tableName);
                menuRemoveBtn();
                return;
            }
        }
        if (selectedItem != null) {
            SalesList = FXCollections.observableArrayList();
            listData = FXCollections.observableArrayList();
            SalesList = servicesSales.getSalesByInvoiceId(selectedItem.getInvoice_id());
            receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("modelID"));
            receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
            receipt_col_No.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            receipt_col_price.setCellValueFactory(new PropertyValueFactory<>("totalP"));
            receipt_tableView.setItems(SalesList);
            listData.addAll(SalesList);
            SalesList.get(0).setStatus(1);
            SalesList.get(0).setRestTable(selectedItem.getRestTable());
            SalesList.get(0).setPriceForInvoice(selectedItem.getTotal_price_after_discount());
            menu_total.setText(Double.toString(selectedItem.getTotal_price_after_discount()));
            disqaunt_textField.setText(Double.toString(selectedItem.getDiscount_percentage()));
            data.Total_invoice_price = selectedItem.getTotal_price_after_discount();
            data.discount = selectedItem.getDiscount_percentage();
            WaitChice = Boolean.TRUE;
        }
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<InvoiceItem> SalesList;
    private ObservableList<Invoices> WaitList;

    public void salesShowData() {
        SalesList = salesDataList();
        receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("modelID"));
        receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        receipt_col_No.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        receipt_col_price.setCellValueFactory(new PropertyValueFactory<>("totalP"));
        receipt_tableView.setItems(SalesList);
    }

    public void salesShowDataff() {
        SalesList = salesDataList();
        receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("modelID"));
        receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        receipt_col_No.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        receipt_col_price.setCellValueFactory(new PropertyValueFactory<>("totalP"));
        receipt_tableView.setItems(SalesList);
    }

    public void waitShowData() {
        WaitList = servicesSales.getAllInvoices();

        wait_invoice_col_table.setCellValueFactory(new PropertyValueFactory<>("restTable"));
        wait_invoice_col_toatal.setCellValueFactory(new PropertyValueFactory<>("total_price_after_discount"));
        wait_invoice_col_invId.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));

        wait_invoice_tableView.setItems(WaitList);
    }
    ObservableList<InvoiceItem> listData = FXCollections.observableArrayList();

    public ObservableList<InvoiceItem> salesDataList() {
        productData prodData = menuTableView.getSelectionModel().getSelectedItem();

        if (prodData == null) {
            E_Alert("يرجى اختيار منتج أولاً", AlertType.WARNING);
            return listData;
        }
        try {
            data.price = prodData.getRealPrice();
        } catch (NumberFormatException e) {
            E_Alert("ادخل سعر المنتج عبارة عن أرقام فقط", AlertType.ERROR);
            return listData;
        }
        int qty = 1;

        try {
            qty = Integer.parseInt(menu_items_quantity.getText());
        } catch (NumberFormatException e) {
            E_Alert("ادخل عدد الاصناف عبارة عن ارقام", AlertType.ERROR);
            return listData;
        }

        String modelID = prodData.getId() + "";
//        int qty = 1;
        double price = data.price;
        double pricePeforDis = prodData.getRealPrice();
        String dataType = prodData.getDataType();
        double totalPerItem = qty * price;
        double totalPerItemPeforDis = qty * pricePeforDis;

        // Check if item already exists in the list
        for (InvoiceItem item : listData) {
            if (item.getModelID().equals(modelID) && item.getPrice() == price) {
                int newQty = item.getQuantity() + qty;
                double newTotal = newQty * price;
                double newTotalBeforeDiscount = newQty * pricePeforDis;

                item.setQuantity(newQty);
                item.setTotalP(newTotal);
                item.setTotalPriceBeforeDis(newTotalBeforeDiscount);

                data.Total_invoice_price += totalPerItem;
                data.Total_invoice_price_peforDis += totalPerItemPeforDis;

                receipt_tableView.refresh();
                return listData;
            }
        }

        // Add new item if not found
        InvoiceItem invoiceItem = new InvoiceItem(
                modelID,
                prodData.getModel(),
                qty,
                totalPerItem,
                pricePeforDis,
                pricePeforDis,
                totalPerItemPeforDis,
                dataType
        );

        listData.add(invoiceItem);

        data.Total_invoice_price += totalPerItem;
        data.Total_invoice_price_peforDis += totalPerItemPeforDis;

        return listData;
    }

    public void salesRemoveItem() {
        Invoices selectedItem = wait_invoice_tableView.getSelectionModel().getSelectedItem();
        InvoiceItem selectedSalesItem = receipt_tableView.getSelectionModel().getSelectedItem();
        if (selectedSalesItem == null) {
            al.E_Alert("يجب اختيار الصنف المراد حذفه", AlertType.ERROR);
        } else {
            if (selectedItem == null) {
                SalesList.remove(selectedSalesItem);
            } else {
                selectedSalesItem.getQuantity();
                selectedSalesItem.getPrice();
                servicesSales.deleteSaleById(selectedSalesItem.getSalesId());
                SalesList.remove(selectedSalesItem);
            }
        }

    }

    public void menuRemoveBtn() {
        SalesList = FXCollections.observableArrayList();
        listData = FXCollections.observableArrayList();
        WaitChice = Boolean.FALSE;
        delivery_price.clear();
        disqaunt_textField.setText("0");
        menu_total.setText("0.0");
        menu_cashPay.setText("0");
        menu_VodafonePay.setText("0");
        menu_instaPay.setText("0");
        menu_items_quantity.setText("1");
        receipt_tableView.getItems().clear();
        wait_invoice_tableView.getItems().clear();
        menu_Search.clear();
//        menu_model.clear();
//        menu_realPrice.clear();
        menu_clientPhone.clear();
        menu_clientName.clear();
        menu_clientAddress.clear();
        data.Total_invoice_price = 0;
        data.price = 0;
        data.invoiceId = 0;
        data.Total_invoice_price_peforDis = 0;
        data.pricePeforDisForItem = 0;
        menuTableView.getSelectionModel().clearSelection();
        departmentTable_menuForm.getSelectionModel().clearSelection();
        tablesList_menuForm.getSelectionModel().clearSelection();
//        requestTypeList_menuForm.getSelectionModel().clearSelection();
        data.clientName = "";
        data.clientphone = "";
        data.clientAdd = "";
        SalesList = null;
        tableDataList = tabserves.getAllTablesByStatus();
        menueShowData();
        waitShowData();
        requestTypeList();
    }

    public void searchProductByNameFromDB() {
        ObservableList<productData> searchResults = FXCollections.observableArrayList();
        String searchText = menu_Search.getText().trim();
        String sql;
//        String type = itemTypeList_menuForm.getSelectionModel().getSelectedItem();
//        if (type != null) {
//            sql = "SELECT id, model, realprice, type "
//                    + "FROM product WHERE (model LIKE ? OR id LIKE ?) AND available = 'متاح' AND type = '" + type + "' ORDER BY model COLLATE NOCASE";
//        } else {
//        }
        sql = "SELECT id, model, realprice, type "
                + "FROM product WHERE (model LIKE ? OR id LIKE ?) AND available = 'متاح' ORDER BY model COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("realprice")
                    );
                    searchResults.add(prodData);
                }
            }

            // تحديث الجدول
            menuTableView.setItems(searchResults);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updateInventoryAfterSale() {
        String insertSales = "INSERT INTO sales (model_id, quantity, total_price, invoice_id, totalWholesalesPrice, model_name, realprice, inventory_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String getInventory = "SELECT wholesaleprice, realprice, available FROM inventory WHERE id = ?";
        String updateInventory = "UPDATE inventory SET available = available - ? WHERE id = ?";

        try (Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                    PreparedStatement insertPs = connect.prepareStatement(insertSales); PreparedStatement selectInventoryPs = connect.prepareStatement(getInventory); PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory)) {
                for (InvoiceItem item : SalesList) {

                    double wholePrice;
                    double realPrice;
                    int availableQty;
                    if ("inventory".equalsIgnoreCase(item.getDataType())) {

                        // جلب بيانات المخزون
                        selectInventoryPs.setString(1, item.getModelID());
                        try (ResultSet rs = selectInventoryPs.executeQuery()) {
                            if (rs.next()) {
                                wholePrice = rs.getDouble("wholesaleprice");
                                realPrice = rs.getDouble("realprice");
                                availableQty = rs.getInt("available");

                                if (item.getQuantity() > availableQty) {
                                    throw new SQLException("الكمية المطلوبة أكبر من المتاحة للموديل: " + item.getModel());
                                }

                                // خصم الكمية من المخزن
                                updateInventoryPs.setInt(1, item.getQuantity());
                                updateInventoryPs.setString(2, item.getModelID());
                                updateInventoryPs.addBatch();

                            } else {
                                throw new SQLException("المنتج غير موجود في المخزن ID: " + item.getModelID());
                            }
                        }
                    }

                    // الحسابات
                    double totalPrice = item.getTotalP();
                    double totalRealPrice = item.getPrice();
                    double totalPriceAfterDiscount = new BigDecimal(item.getTotalP())
                            .setScale(0, RoundingMode.HALF_UP)
                            .doubleValue();

                    // إدخال في جدول المبيعات
                    insertPs.setString(1, item.getModelID());
                    insertPs.setInt(2, item.getQuantity());
                    insertPs.setDouble(3, totalPriceAfterDiscount);
                    insertPs.setInt(4, data.invoiceId);
                    insertPs.setDouble(5, totalPrice);
                    insertPs.setString(6, item.getModel());
                    insertPs.setDouble(7, totalRealPrice);
                    insertPs.setString(8, "inventory");
                    insertPs.addBatch();
                }

                // تنفيذ
                insertPs.executeBatch();
                updateInventoryPs.executeBatch();
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                E_Alert("حدث خطأ أثناء البيع (المخزن فقط). تم التراجع عن العملية.", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updateProductQuantitiesAfterSaleForWait() {
        String insertSales = "INSERT INTO sales (model_id, quantity, total_price, invoice_id, totalWholesalesPrice, model_name, realprice, inventory_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String getWholePriceInventory = "SELECT wholesaleprice, realprice, available FROM inventory WHERE id = ?";
        String getWholePriceProduct = "SELECT wholesaleprice, realprice FROM product WHERE id = ?";
        String updateInventory = "UPDATE inventory SET available = available - ? WHERE id = ?";

        try (Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                    PreparedStatement insertPs = connect.prepareStatement(insertSales); PreparedStatement selectInventoryPs = connect.prepareStatement(getWholePriceInventory); PreparedStatement selectProductPs = connect.prepareStatement(getWholePriceProduct); PreparedStatement updateInventoryPs = connect.prepareStatement(updateInventory)) {
                for (InvoiceItem item : SalesList) {

                    double wholePrice;
                    double realPrice;
                    int availableQty = -1; // الافتراضي -1 (لو جاي من product يبقى مفيش كمية)

                    if ("inventory".equalsIgnoreCase(item.getDataType())) {
                        // جلب البيانات من جدول المخزون
                        selectInventoryPs.setString(1, item.getModelID());
                        try (ResultSet rs = selectInventoryPs.executeQuery()) {
                            if (rs.next()) {
                                wholePrice = rs.getDouble("wholesaleprice");
                                realPrice = rs.getDouble("realprice");
                                availableQty = rs.getInt("available");

                                if (item.getQuantity() > availableQty) {
                                    throw new SQLException("الكمية المطلوبة أكبر من الكمية المتاحة للموديل (inventory): " + item.getModel());
                                }

                                // تحديث الكمية فى المخزون
                                updateInventoryPs.setInt(1, item.getQuantity());
                                updateInventoryPs.setString(2, item.getModelID());
                                updateInventoryPs.addBatch();
                            } else {
                                throw new SQLException("فشل العثور على بيانات المنتج فى المخزون ID: " + item.getModelID());
                            }
                        }

                    } else if ("product".equalsIgnoreCase(item.getDataType())) {
                        // جلب البيانات من جدول المنتج فقط (بدون تعديل كمية)
                        selectProductPs.setString(1, item.getModelID());
                        try (ResultSet rs = selectProductPs.executeQuery()) {
                            if (rs.next()) {
                                wholePrice = rs.getDouble("wholesaleprice");
                                realPrice = rs.getDouble("realprice");
                            } else {
                                throw new SQLException("فشل العثور على بيانات المنتج فى جدول product ID: " + item.getModelID());
                            }
                        }
                    } else {
                        throw new SQLException("نوع الداتا غير معروف: " + item.getDataType());
                    }

                    // الحسابات المشتركة
                    double totalPrice = item.getQuantity() * realPrice;
                    double totalRealPrice = realPrice;

                    double totalPriceAfterDiscount = item.getTotalP();
                    BigDecimal bd = new BigDecimal(totalPriceAfterDiscount).setScale(0, RoundingMode.HALF_UP);
                    totalPriceAfterDiscount = bd.doubleValue();

                    // إدخال بيانات البيع
                    insertPs.setString(1, item.getModelID());
                    insertPs.setInt(2, item.getQuantity());
                    insertPs.setDouble(3, totalPriceAfterDiscount);
                    insertPs.setInt(4, data.invoiceId);
                    insertPs.setDouble(5, totalPrice);
                    insertPs.setString(6, item.getModel());
                    insertPs.setDouble(7, totalRealPrice);
                    insertPs.setString(8, item.getDataType());
                    insertPs.addBatch();
                }

                // تنفيذ العمليات
                insertPs.executeBatch();
                updateInventoryPs.executeBatch();
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                E_Alert("حدث خطأ أثناء تنفيذ عملية البيع. تم التراجع عن العملية.", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public int insertInvoice(double totalQuantity, double totalBeforeDiscount, double discount,
            double totalAfterDiscount, String username,
            Double cashPay, Double instaPay, Double vodafonePay, String clientName, String clientPhone, int shiftId, String reqType) {

        String sql = "INSERT INTO invoices (total_quantity, total_price_before_discount, discount_percentage, "
                + "total_price_after_discount, username, cashPay, instaPay, vodafonePay, clientName, clientPhone, shiftsId, reqType, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setDouble(1, totalQuantity);
            pst.setDouble(2, totalBeforeDiscount);
            pst.setDouble(3, discount);
            pst.setDouble(4, totalAfterDiscount);
            pst.setString(5, username);
            pst.setDouble(6, cashPay != null ? cashPay : 0.0);
            pst.setDouble(7, instaPay != null ? instaPay : 0.0);
            pst.setDouble(8, vodafonePay != null ? vodafonePay : 0.0);
            pst.setString(9, clientName);
            pst.setString(10, clientPhone);
            pst.setInt(11, data.shiftId);
            pst.setString(12, reqType);
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل إنشاء الفاتورة، لم يتم تعديل أي صف.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID الناتج
                } else {
                    throw new SQLException("فشل إنشاء الفاتورة، لم يتم الحصول على رقم الفاتورة.");
                }
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حفظ الفاتورة في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
            return -1;
        }
    }

    public int updateInvoiceAfterWait(double totalQunt, double totalBeforeDiscount, double discount,
            double totalAfterDiscount, Double cashPay, Double instaPay, Double vodafonePay, int invoiceId) {

        String sql = "UPDATE invoices SET total_price_before_discount = ?, "
                + "discount_percentage = ?, "
                + "total_price_after_discount = ?, "
                + "cashPay = ?, "
                + "instaPay = ?, "
                + "vodafonePay = ?, "
                + "total_quantity = ?, "
                + "status = 1 "
                + "WHERE invoice_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql)) {

            pst.setDouble(1, totalBeforeDiscount);
            pst.setDouble(2, discount);
            pst.setDouble(3, totalAfterDiscount);
            pst.setDouble(4, cashPay != null ? cashPay : 0.0);
            pst.setDouble(5, instaPay != null ? instaPay : 0.0);
            pst.setDouble(6, vodafonePay != null ? vodafonePay : 0.0);
            pst.setDouble(7, totalQunt);
            pst.setInt(8, invoiceId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل التحديث: لم يتم تعديل أي صف.");
            }

            return invoiceId; // أو affectedRows لو عايز عدد الصفوف

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تحديث الفاتورة في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
            return -1;
        }
    }

    public int insertInvoiceForWait(double totalAfterDiscount, double discount, double totalQuantity, String username,
            String clientName, String clientPhone, int shiftId, String reqType, String restTable) {

        String sql = "INSERT INTO invoices (total_quantity, username,"
                + " clientName, clientPhone, shiftsId, reqType, resturant_table, total_price_after_discount,discount_percentage) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setDouble(1, totalQuantity);
            pst.setString(2, username);
            pst.setString(3, clientName);
            pst.setString(4, clientPhone);
            pst.setInt(5, data.shiftId);
            pst.setString(6, reqType);
            pst.setString(7, restTable);
            pst.setDouble(8, totalAfterDiscount);
            pst.setDouble(9, discount);
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل إنشاء الفاتورة، لم يتم تعديل أي صف.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID الناتج
                } else {
                    throw new SQLException("فشل إنشاء الفاتورة، لم يتم الحصول على رقم الفاتورة.");
                }
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حفظ الفاتورة في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
            return -1;
        }
    }

    public void updateInvoiceForWait(double totalAfterDiscount, double discount, double totalQuantity, int invoiceId) {

        String sql = "UPDATE invoices SET total_quantity = ? , total_price_after_discount = ? , discount_percentage = ? where invoice_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setDouble(1, totalQuantity);
            pst.setDouble(2, totalAfterDiscount);
            pst.setDouble(3, discount);
            pst.setInt(4, invoiceId);
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل إنشاء الفاتورة، لم يتم تعديل أي صف.");
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حفظ الفاتورة في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<Sales> returnListData;

    public void returnShowData() {
        returnListData = getSalesData(Boolean.TRUE);

        return_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        return_col_model.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        return_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        return_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        return_col_modelsId.setCellValueFactory(new PropertyValueFactory<>("model_id"));
        return_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        return_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        returnTableView.setItems(returnListData);
        func.setupTableSearchFilter(retrun_Search_field, returnTableView, returnListData,
                Arrays.asList("model", "type", "modelID", "invoiceId"));

    }

    public void returnRemoveBtn() {
        receipt_tableView.getItems().clear();
        wait_invoice_tableView.getItems().clear();
        retrun_Search_field.clear();
        return_model.clear();
        return_productID.clear();
        return_realPrice.clear();
        return_type.clear();
        setQuantityr();
        return_reciptID.clear();
        return_reson.clear();
    }

    public void setQuantityr() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        return_spinner_no.setValueFactory(spin);
    }

    public void returnSelectData() {
        Sales prodData = returnTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            return;
        }

        return_productID.setText(Integer.toString(prodData.getModel_id()));
        return_model.setText(prodData.getModel_name());
        return_type.setText(prodData.getType());
        return_realPrice.setText(Double.toString(prodData.getTotal_price()));
        return_spinner_no.getValueFactory().setValue(prodData.getQuantity());
        return_reciptID.setText(Integer.toString(prodData.getInvoice_id()));
    }

    public void searchMissingProduct() {
        ObservableList<productData> searchResult = FXCollections.observableArrayList();
        String searchText = missingProduct_Search.getText().trim();

        String sql = "SELECT * FROM product WHERE (model LIKE ? OR id LIKE ? OR type LIKE ?) AND available = 'غير متاح' ORDER BY model COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setString(3, "%" + searchText + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    searchResult.add(prodData);
                }
            }

            // إعداد الأعمدة فقط مرة واحدة في مكان آخر (وليس عند كل عملية بحث)
            missingProductTableView.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

//    public void printReceipt() {
//        InputStream input = null;
//        try {
//            input = getClass().getResourceAsStream("/reports/report.jrxml");
//            if (input == null) {
//                E_Alert("لم يتم العثور على ملف التصميم Invoice.jrxml", AlertType.ERROR);
//                return;
//            }
//
//            JasperReport jr = JasperCompileManager.compileReport(input);
//
//            if (data.invoiceId == 0 || data.Total_invoice_price == 0.0) {
//                E_Alert("تأكد من وجود بيانات صحيحة للفاتورة قبل الطباعة", AlertType.WARNING);
//                return;
//            }
//
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("cashier", data.username);
//            parameters.put("total", String.format("%.2f $", data.Total_invoice_price));
//            parameters.put("RID", data.invoiceId + "");
//            parameters.put("totalQuantity", data.totalQty + "");
//            parameters.put("discount", String.format("%.2f $", data.discount));
//            //Data
//            List<InvoiceItem> items = listData;
////            if (!items.isEmpty()) {
////                items.remove(items.size() - 1);
////            }
//            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(items);
//
//            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);
//
//            JasperPrintManager.printReport(print, false);
////            JasperViewer.viewReport(print, false);
//
//        } catch (Exception e) {
//            E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), AlertType.ERROR);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ioEx) {
//                E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), AlertType.ERROR);
//                ioEx.printStackTrace();
//            }
//        }
//    }
//    public void printReceipt() {
//        InputStream input = null;
//        try {
//            // تحميل ملف التقرير الجاهز (jasper)
//            input = getClass().getResourceAsStream("/reports/report.jasper");
//            if (input == null) {
//                E_Alert("لم يتم العثور على ملف التقرير report.jasper", AlertType.ERROR);
//                return;
//            }
//
//            // تحميل التقرير المترجم مباشرة
//            JasperReport jr = (JasperReport) JRLoader.loadObject(input);
//
//            if (data.invoiceId == 0 || data.Total_invoice_price == 0.0) {
//                E_Alert("تأكد من وجود بيانات صحيحة للفاتورة قبل الطباعة", AlertType.WARNING);
//                return;
//            }
//
//            // إعداد الـ parameters
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("cashier", data.username);
//            parameters.put("total", String.format("%.2f $", data.Total_invoice_price));
//            parameters.put("RID", data.invoiceId + "");
//            parameters.put("totalQuantity", data.totalQty + "");
//            parameters.put("discount", String.format("%.2f $", data.discount));
//
//            // البيانات
//            List<InvoiceItem> items = listData;
//            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(items);
//
//            // تعبئة التقرير
//            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);
//
//            // الطباعة مباشرة
//            JasperPrintManager.printReport(print, false);
//
//            // أو للعرض فقط (مفيد في التجربة):
//            // JasperViewer.viewReport(print, false);
//        } catch (Exception e) {
//            E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), AlertType.ERROR);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ioEx) {
//                E_Alert("حدث خطأ أثناء إغلاق التقرير: " + ioEx.getMessage(), AlertType.ERROR);
//                ioEx.printStackTrace();
//            }
//        }
//    }
//    public void printLabel() {
//        InputStream input = null;
//        try {
//            // تحميل ملف التقرير الجاهز (jasper)
//            input = getClass().getResourceAsStream("/reports/label.jasper");
//            if (input == null) {
//                E_Alert("لم يتم العثور على ملف التقرير label.jasper", AlertType.ERROR);
//                return;
//            }
//
//            // تحميل التقرير المترجم مباشرة
//            JasperReport jr = (JasperReport) JRLoader.loadObject(input);
//
//            if (data.invoiceId == 0 || data.Total_invoice_price == 0.0) {
//                E_Alert("تأكد من وجود بيانات صحيحة للفاتورة قبل الطباعة", AlertType.WARNING);
//                return;
//            }
//
//            // إعداد الـ parameters
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("RID", data.invoiceId + "");
//
//            // البيانات
//            List<InvoiceItem> items = listData;
//            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(items);
//
//            // تعبئة التقرير
//            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);
//
//            // الطباعة مباشرة
//            JasperPrintManager.printReport(print, false);
//
//            // أو للعرض فقط (مفيد في التجربة):
//            // JasperViewer.viewReport(print, false);
//        } catch (Exception e) {
//            E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), AlertType.ERROR);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ioEx) {
//                E_Alert("حدث خطأ أثناء إغلاق التقرير: " + ioEx.getMessage(), AlertType.ERROR);
//                ioEx.printStackTrace();
//            }
//        }
//    }
    public void printReport(String reportFileName, HashMap<String, Object> parameters, Collection<?> dataSource) {
        InputStream input = null;
        try {

            // check if dataSource is null or empty
            if (dataSource == null || dataSource.isEmpty()) {
                E_Alert("قائمة البيانات فارغة، لا يمكن طباعة التقرير.", AlertType.WARNING);
                return;
            }

            // تحميل ملف التقرير
            input = getClass().getResourceAsStream("/reports/" + reportFileName);
            if (input == null) {
                E_Alert("لم يتم العثور على ملف التقرير " + reportFileName, AlertType.ERROR);
                return;
            }

            // تحميل التقرير
            JasperReport jr = (JasperReport) JRLoader.loadObject(input);

            // تحديد مصدر البيانات
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(dataSource);

            // تعبئة التقرير
            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            // الطباعة
            JasperPrintManager.printReport(print, false);

            // للتجربة فقط:
            // JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            E_Alert("حدث خطأ أثناء طباعة التقرير: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                E_Alert("حدث خطأ أثناء إغلاق التقرير: " + ioEx.getMessage(), AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printReceipt() {
        // لطباعة الفاتورة
        HashMap<String, Object> receiptParams = new HashMap<>();
        receiptParams.put("cashier", data.username);
        receiptParams.put("total", String.format("%.2f $", data.Total_invoice_price));
        receiptParams.put("RID", data.invoiceId + "");
        receiptParams.put("totalQuantity", data.totalQty + "");
        receiptParams.put("discount", String.format("%.2f $", data.discount));

        if (requestTypeList_menuForm.getSelectionModel().getSelectedItem().equals("توصيل")) {
            receiptParams.put("clientName", data.clientName);
            receiptParams.put("clientPhone", data.clientphone);
            receiptParams.put("clientAdd", data.clientAdd);
            receiptParams.put("delivery", data.deliveryPrice);
            receiptParams.put("totalWithDelivery", String.format("%.2f $", data.deliveryPrice + data.Total_invoice_price));
            printReport("report_clientInfo.jasper", receiptParams, listData);
        } else {
            printReport("report.jasper", receiptParams, listData);
        }

        // لطباعة اللابل
        HashMap<String, Object> labelParams = new HashMap<>();
        labelParams.put("RID", data.invoiceId + "");

        printReport("label.jasper", labelParams, listData);
    }

    public void returnMethodList() {
        List<String> methodL = new ArrayList<>();
        for (String data : RMethod) {
            methodL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(RMethod);
        returnMethodList.getItems().clear();
        returnMethodList.setItems(listData);
    }

    public void returnActionBtn() {
        Sales sale = returnTableView.getSelectionModel().getSelectedItem();

        if ((sale == null)) {
            E_Alert("اختر المنتج من فضلك", AlertType.ERROR);
        } else if (return_reciptID.getText().isEmpty() || return_reson.getText().isEmpty() || returnMethodList.getSelectionModel().getSelectedItem() == null) {
            E_Alert("ادخل رقم الفاتورة و السبب و طريقة استرجاع المال", AlertType.ERROR);
        } else if (servicesSales.getDiscountInvoice(sale.getInvoice_id()) != 0.0) {
            E_Alert("لا يسمح استرجاع فاتورة بها خصم", AlertType.ERROR);
        } else {
            Sales salesUpdate = new Sales();

            salesUpdate.setModel_name(return_model.getText());
            salesUpdate.setSales_id(sale.getSales_id());
            salesUpdate.setModel_id(Integer.parseInt(return_productID.getText()));
            salesUpdate.setType(sale.getInventoryType());
            salesUpdate.setQuantity(return_spinner_no.getValue());
            salesUpdate.setInvoice_id(sale.getInvoice_id());
            salesUpdate.setTotal_price(sale.getTotal_price());

            servicesSales.returnItemById(salesUpdate, returnMethodList.getSelectionModel().getSelectedItem(), sale.getQuantity(), return_reson.getText());
            returnShowData();
            retrunProductShowData();
        }
    }

    public void returnInvoiceActionBtn() {
        Sales sale = returnTableView.getSelectionModel().getSelectedItem();
        if ((sale == null)) {
            E_Alert("اختر المنتج من فضلك", AlertType.ERROR);
        } else if (return_reciptID.getText().isEmpty() || return_reson.getText().isEmpty() || returnMethodList.getSelectionModel().getSelectedItem() == null) {
            E_Alert("ادخل رقم الفاتورة و السبب و طريقة استرجاع المال", AlertType.ERROR);
        } else if (servicesSales.getDiscountInvoice(sale.getInvoice_id()) != 0.0) {
            E_Alert("لا يسمح استرجاع فاتورة بها خصم", AlertType.ERROR);
        } else {
            servicesSales.returnInvoice(Integer.parseInt(return_reciptID.getText()), returnMethodList.getSelectionModel().getSelectedItem(), return_reson.getText());
            returnShowData();
            retrunProductShowData();
        }
    }

    // الدالة اللي كتبناها قبل كده للبحث عن العميل
    public Map<String, String> getCustomerByPhone(String phone) {
        String sql = "SELECT name, address FROM customers WHERE phone LIKE ?";
        Map<String, String> customerData = null;

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + phone + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customerData = new HashMap<>();
                    customerData.put("name", rs.getString("name"));
                    customerData.put("address", rs.getString("address"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerData;
    }

    private void setupCustomerAutoFill() {
        menu_clientPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 4) {
                Map<String, String> customer = getCustomerByPhone(newValue);
                if (customer != null) {
                    menu_clientName.setText(customer.get("name"));
                    menu_clientAddress.setText(customer.get("address"));
                } else {
                    menu_clientName.clear();
                    menu_clientAddress.clear();
                }
            } else {
                menu_clientName.clear();
                menu_clientAddress.clear();
            }
        });
    }

    public void payMethod() {
        try {
            double inputCash = Double.parseDouble(menu_cashPay.getText());
            double inputVodafone = Double.parseDouble(menu_VodafonePay.getText());
            double inputInsta = Double.parseDouble(menu_instaPay.getText());
            String clientName = menu_clientName.getText();
            String clientPhone = menu_clientPhone.getText();
            String clientAdd = menu_clientAddress.getText();
            String delivery = delivery_price.getText();
            data.clientName = clientName;
            data.clientphone = clientPhone;
            data.clientAdd = clientAdd;
            String reqType = requestTypeList_menuForm.getSelectionModel().getSelectedItem();

            if (SalesList == null) {
                E_Alert("لا يمكن الدفع لعدم وجود فاتورة او اصناف", AlertType.ERROR);
                return;
            }

            for (InvoiceItem item : SalesList) {
                if (item.getDataType().equals("inventory")) {
                    InventoryData data = proServices.getInventoryDataByID(Integer.parseInt(item.getModelID()));
                    if (item.getQuantity() > data.getAvailableNo()) {
                        al.E_Alert("يجب اختيار كمية اقل" + data.getAvailableNo() + "فى المخزن " + item.getModel() + "عدد المنتج", AlertType.ERROR);
                        return;
                    }
                }
            }

            Boolean wait = Boolean.FALSE;
            int wait_id = 0;

            if (SalesList.get(0).getStatus() == 1) {
                wait = Boolean.TRUE;
                wait_id = SalesList.get(0).getInvoiceId();
            }

            if (SalesList == null) {
                E_Alert("اختر الاصناف اولا", AlertType.ERROR);
                return;
            }
            if (!wait) {
                if (reqType == null || reqType.isEmpty()) {
                    E_Alert("حدد نوع الطلب اولا", AlertType.ERROR);
                    return;
                }
            }

//            if (reqType.equals("توصيل")) {
//                if ((clientName.isEmpty() || clientPhone.isEmpty() || clientAdd.isEmpty())) {
//                    E_Alert("الرجاء ادخال اسم العميل ورقم الهاتف و العنوان", AlertType.ERROR);
//                    return;
//                }
//                if (isntNumeric(delivery)) {
//                    E_Alert("ادخل سعر التوصيل عبارة عن ارقام", AlertType.ERROR);
//                    return;
//                } else {
//                    data.deliveryPrice = Double.parseDouble(delivery_price.getText());
//                }
//            }
            data.invoiceId = 0;

            double remaining = data.Total_invoice_price;

            // Vodafone → Insta → Cash
            data.vodafonePay = Math.min(inputVodafone, remaining);
            remaining -= data.vodafonePay;

            data.instaPay = Math.min(inputInsta, remaining);
            remaining -= data.instaPay;

            data.cashPay = Math.min(inputCash, remaining);
            remaining -= data.cashPay;

            data.totalPayed = data.vodafonePay + data.instaPay + data.cashPay;
            double totalInput = inputCash + inputVodafone + inputInsta;
            data.change = totalInput - data.totalPayed;

            data.clientName = clientName;
            data.clientphone = clientPhone;

            if (data.totalPayed >= data.Total_invoice_price) {
                // save invoice
                if (wait) {
                    data.Total_invoice_price_peforDis = data.Total_invoice_price + data.discount;
                    data.invoiceId = updateInvoiceAfterWait(
                            data.totalQty,
                            data.Total_invoice_price_peforDis,
                            data.discount,
                            data.Total_invoice_price,
                            data.cashPay,
                            data.instaPay,
                            data.vodafonePay,
                            wait_id
                    );
                } else {
                    data.invoiceId = insertInvoice(
                            data.totalQty,
                            data.Total_invoice_price_peforDis,
                            data.discount,
                            data.Total_invoice_price,
                            data.username,
                            data.cashPay,
                            data.instaPay,
                            data.vodafonePay,
                            data.clientName,
                            data.clientphone,
                            data.shiftId,
                            reqType
                    );
                }

                if (data.invoiceId > 0) {
                    if (wait) {
                        servicesSales.deleteSalesWithoutInvoiceAndRestoreQuantities(data.invoiceId);
                        updateProductQuantitiesAfterSaleForWait();
                        finalizeInvoice();
                        tabserves.setTableEmpty(SalesList.get(0).getRestTable());
                    } else {
                        updateInventoryAfterSale();
                        finalizeInvoice();
                        addCustomerIfNotExists(clientName, clientPhone, clientAdd);
                    }

                    Confirmation_Alert("الباقي: (" + data.change + ")" + "\n هل تريد طباعة فاتورة؟");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        printReceipt();
                    } else {
                        E_Alert("تم إلغاء الطباعة", AlertType.ERROR);
                    }
                    E_Alert("تمت عملية البيع بنجاح", AlertType.INFORMATION);
                } else {
                    E_Alert("حدث خطأ اثناء حفظ الفاتورة, الرجاء اعادة عملية البيع", AlertType.ERROR);
                }
                menuRemoveBtn();
            } else {
                E_Alert("المبلغ غير كافي لتغطية الفاتورة", AlertType.WARNING);
            }

        } catch (NumberFormatException e) {
            E_Alert("الرجاء إدخال مبالغ صحيحة!", Alert.AlertType.ERROR);
        }
    }

    public void waitMethod() {
        try {

            String clientName = menu_clientName.getText();
            String clientPhone = menu_clientPhone.getText();
            String clientAdd = menu_clientAddress.getText();
            data.clientName = clientName;
            data.clientphone = clientPhone;
            data.clientAdd = clientAdd;
            String reqType = requestTypeList_menuForm.getSelectionModel().getSelectedItem();
            String reqTable = tablesList_menuForm.getSelectionModel().getSelectedItem();

            if (SalesList == null) {
                E_Alert("لا يمكن الانتظار لعدم وجود فاتورة او اصناف", AlertType.ERROR);
                return;
            }

            for (InvoiceItem item : SalesList) {
                if (item.getDataType().equals("inventory")) {
                    InventoryData data = proServices.getInventoryDataByID(Integer.parseInt(item.getModelID()));
                    if (item.getQuantity() > data.getAvailableNo()) {
                        al.E_Alert("يجب اختيار كمية اقل" + data.getAvailableNo() + "فى المخزن " + item.getModel() + "عدد المنتج", AlertType.ERROR);
                        return;
                    }
                }
            }

            if (SalesList == null) {
                E_Alert("اختر الاصناف اولا", AlertType.ERROR);
                return;
            }
            if (!WaitChice) {
                if (reqType == null || reqType.isEmpty()) {
                    E_Alert("حدد نوع الطلب اولا", AlertType.ERROR);
                    return;
                }
                if (reqTable == null || reqTable.isEmpty()) {
                    E_Alert("حدد الطاولة اولا", AlertType.ERROR);
                    return;
                }
                if (reqType.equals("تيك اواي")) {
                    E_Alert("غير مسموح بالانتظار فى حالة التيك اوى", AlertType.ERROR);
                    return;
                }
            }

            if (WaitChice) {
                int invoiceId = SalesList.get(0).getInvoiceId();
                data.invoiceId = invoiceId;
                updateInvoiceForWait(
                        data.Total_invoice_price,
                        data.discount,
                        data.totalQty,
                        invoiceId
                );
            } else {
                data.invoiceId = insertInvoiceForWait(
                        data.Total_invoice_price,
                        data.discount,
                        data.totalQty,
                        data.username,
                        data.clientName,
                        data.clientphone,
                        data.shiftId,
                        reqType,
                        reqTable
                );
            }

            if (data.invoiceId > 0) {
                if (WaitChice) {
                    servicesSales.deleteSalesWithoutInvoiceAndRestoreQuantities(data.invoiceId);
                    updateProductQuantitiesAfterSaleForWait();
                } else {
                    updateProductQuantitiesAfterSaleForWait();
                    addCustomerIfNotExists(clientName, clientPhone, clientAdd);
                    tabserves.setTableOccupied(reqTable);
                }
                waitShowData();
                E_Alert("تمت العملية بنجاح", AlertType.INFORMATION);
            } else {
                E_Alert("حدث خطأ اثناء حفظ الفاتورة, الرجاء اعادة عملية البيع", AlertType.ERROR);
            }
            menuRemoveBtn();

        } catch (NumberFormatException e) {
            E_Alert("الرجاء إدخال مبالغ صحيحة!", Alert.AlertType.ERROR);
        }
    }

    public void finalizeInvoice() {
        data.totalQty = 0;
        data.beforeDiscount = 0;
//        double discount = 0;
        for (InvoiceItem item : SalesList) {
            data.totalQty += item.getQuantity();
            data.beforeDiscount += item.getTotalP();
        }

        data.discount = 0;
        try {
//            discount = Double.parseDouble(disqaunt_textField.getText()) / data.beforeDiscount;
//            BigDecimal bd = new BigDecimal(discount).setScale(3, RoundingMode.HALF_UP);
//            discount = bd.doubleValue();
            data.discount_rate = Double.parseDouble(disqaunt_textField.getText());
//            data.discount = discount;
            data.discount = Double.parseDouble(disqaunt_textField.getText());
        } catch (NumberFormatException e) {
            E_Alert("الخصم غير صالح", Alert.AlertType.ERROR);
            Platform.runLater(() -> disqaunt_textField.setText("0"));
        }
        if (Double.parseDouble(disqaunt_textField.getText()) < data.beforeDiscount) {
            data.Total_invoice_price = data.beforeDiscount - (data.discount_rate);
            menu_total.setText(data.Total_invoice_price + "");
        } else if (Double.parseDouble(disqaunt_textField.getText()) == 0) {
            return;
        } else {
            E_Alert("الخصم اكبر من سعر الفاتورة", Alert.AlertType.ERROR);
            Platform.runLater(() -> disqaunt_textField.setText("0"));
        }

    }

    public void addToReceipt() {
        productData prodData = menuTableView.getSelectionModel().getSelectedItem();
        if (prodData != null) {
            salesShowData();
            finalizeInvoice();
        } else {
            E_Alert("حدد المنتج اولا من فضلك", AlertType.ERROR);
        }
        menu_items_quantity.setText("1");
    }

    public void payBtn() {
        payMethod();
        menueShowData();
    }

    public void waitBtn() {
        payMethod();
        menueShowData();
    }

    // MERGE ALL DATAS
    public ObservableList<productData> missingProductDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT id, model, wholesalePrice, realPrice, type, soldNo FROM product WHERE available = 'غير متاح' "
                + "UNION ALL "
                + "SELECT id, model, wholesalePrice, realPrice, type, soldNo FROM inventory WHERE available < 1 "
                + "ORDER BY model COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("wholesaleprice"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getInt("soldNo")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تحميل المنتجات الناقصة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> missingProductListData;

    public void missingProductShowData() {
        missingProductListData = missingProductDataList();
        missing_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        missing_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        missing_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        missing_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        missing_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        missing_col_soldNo.setCellValueFactory(new PropertyValueFactory<>("soldNo"));
        missingProductTableView.setItems(missingProductListData);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ObservableList<Returns> retrunProductListData;

    public void retrunProductShowData() {
        retrunProductListData = proServices.getAllReturns();
        returnProduct_col_id.setCellValueFactory(new PropertyValueFactory<>("return_id"));
        retrunProduct_col_modelID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        retrunProduct_col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        retrunProduct_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity_returned"));
        retrunProduct_col_reson.setCellValueFactory(new PropertyValueFactory<>("return_reason"));
        retrunProduct_col_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        retrunProduct_col_money.setCellValueFactory(new PropertyValueFactory<>("price_of_return"));
        retrunProduct_col_modelName.setCellValueFactory(new PropertyValueFactory<>("model_name"));

        returnProductTableView.setItems(retrunProductListData);

        func.setupTableSearchFilter(returnProduct_Search, returnProductTableView, retrunProductListData,
                 Arrays.asList("product_id", "invoice_id", "return_reason", "model_name"));
    }

    public void searchReturnProduct() {
        ObservableList<Returns> searchResult = FXCollections.observableArrayList();
        String searchText = returnProduct_Search.getText().trim();

        String sql = "SELECT * FROM returns WHERE CAST(invoice_id AS TEXT) LIKE ? OR product_id LIKE ? OR model_name LIKE ? OR return_reason LIKE ? OR return_date LIKE ? ORDER BY return_date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setString(3, "%" + searchText + "%");
            ps.setString(4, "%" + searchText + "%");
            ps.setString(5, "%" + searchText + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Returns returns = new Returns(
                            rs.getInt("return_id"),
                            rs.getInt("product_id"),
                            rs.getInt("invoice_id"),
                            rs.getInt("quantity_returned"),
                            rs.getString("return_reason"),
                            rs.getString("return_date"),
                            rs.getDouble("price_of_return"),
                            rs.getString("model_name")
                    );
                    searchResult.add(returns);
                }
            }
            returnProductTableView.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<Invoices> getInvoicesData() {
        ObservableList<Invoices> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM invoices WHERE total_quantity > 0 ORDER BY invoice_date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Invoices inv = new Invoices(
                        rs.getInt("invoice_id"),
                        rs.getInt("total_quantity"),
                        rs.getDouble("total_price_before_discount"),
                        rs.getDouble("discount_percentage"),
                        rs.getDouble("total_price_after_discount"),
                        rs.getDouble("cashPay"),
                        rs.getDouble("instaPay"),
                        rs.getDouble("vodafonePay"),
                        rs.getString("username"),
                        rs.getString("invoice_date"),
                        rs.getString("clientName"),
                        rs.getString("clientPhone")
                );
                list.add(inv);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تحميل الفواتير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    private ObservableList<Invoices> invoiceList;

    public void showInvoicesData() {
        invoiceList = getInvoicesData();

        col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        col_totalQty.setCellValueFactory(new PropertyValueFactory<>("total_quantity"));
        col_totalBeforeDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_before_discount"));
        col_discount.setCellValueFactory(new PropertyValueFactory<>("discount_percentage"));
        col_totalAfterDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_after_discount"));
        col_cash.setCellValueFactory(new PropertyValueFactory<>("cashPay"));
        col_insta.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        col_voda.setCellValueFactory(new PropertyValueFactory<>("vodafonePay"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));
        col_clientName_salesForm.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        col_clientPhone_salesForm.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));

        invoiceTable.setItems(invoiceList);
    }

    public void searchInvoices() {
        ObservableList<Invoices> searchResult = FXCollections.observableArrayList();
        String searchText = invoice_search.getText().trim();

        String sql = "SELECT * FROM invoices WHERE CAST(invoice_id AS TEXT) LIKE ? OR clientName LIKE ? OR clientPhone LIKE ? ORDER BY invoice_date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setString(3, "%" + searchText + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoices invoices = new Invoices(
                            rs.getInt("invoice_id"),
                            rs.getInt("total_quantity"),
                            rs.getDouble("total_price_before_discount"),
                            rs.getDouble("discount_percentage"),
                            rs.getDouble("total_price_after_discount"),
                            rs.getDouble("cashPay"),
                            rs.getDouble("instaPay"),
                            rs.getDouble("vodafonePay"),
                            rs.getString("username"),
                            rs.getString("invoice_date"),
                            rs.getString("clientName"),
                            rs.getString("clientPhone")
                    );
                    searchResult.add(invoices);
                }
            }
            invoiceTable.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchInvoicesFlexible(LocalDate fromDate, LocalDate toDate, String keyword) {
        ObservableList<Invoices> searchResult = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder("SELECT * FROM invoices WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // البحث بالتاريخ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (fromDate != null) {
            sql.append(" AND DATE(invoice_date) >= ?");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            sql.append(" AND DATE(invoice_date) <= ?");
            params.add(toDate.format(formatter));
        }

        // البحث بالكلمة المفتاحية (ID أو الاسم أو الهاتف)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (CAST(invoice_id AS TEXT) LIKE ? OR clientName LIKE ? OR clientPhone LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        sql.append(" ORDER BY invoice_date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            // تعيين القيم للـ PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoices inv = new Invoices(
                            rs.getInt("invoice_id"),
                            rs.getInt("total_quantity"),
                            rs.getDouble("total_price_before_discount"),
                            rs.getDouble("discount_percentage"),
                            rs.getDouble("total_price_after_discount"),
                            rs.getDouble("cashPay"),
                            rs.getDouble("instaPay"),
                            rs.getDouble("vodafonePay"),
                            rs.getString("username"),
                            rs.getString("invoice_date"),
                            rs.getString("clientName"),
                            rs.getString("clientPhone")
                    );
                    searchResult.add(inv);
                }
            }
            invoiceTable.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء جلب البيانات من قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchByRangeInvoicesAndSales() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        String searchInvoices = invoice_search.getText().trim();
        searchInvoicesFlexible(fromDate, toDate, searchInvoices);
        String searchSales = salesModelID_search.getText().trim();
        searchSalesFlexible(fromDate, toDate, searchSales);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<Sales> getSalesData(Boolean returns) {
        ObservableList<Sales> list = FXCollections.observableArrayList();
        String query;
        String type = itemTypeList_salesForm.getSelectionModel().getSelectedItem();

        if (returns) {
            query
                    = "SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, "
                    + "       s.date, s.invoice_id, s.model_name, COALESCE(p.type, i.type) AS type "
                    + " ,s.inventory_type "
                    + "FROM sales s "
                    + "LEFT JOIN product p ON s.model_id = p.id "
                    + "LEFT JOIN inventory i ON s.model_id = i.id "
                    + "LEFT JOIN invoices r ON s.invoice_id = r.invoice_id "
                    + "where r.status = 1 "
                    + "ORDER BY s.date DESC";
        } else {
            if (type != null) {
                query
                        = "SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, "
                        + "       s.date, s.invoice_id, s.model_name, COALESCE(p.type, i.type) AS type "
                        + " ,s.inventory_type "
                        + "FROM sales s "
                        + "LEFT JOIN product p ON s.model_id = p.id "
                        + "LEFT JOIN inventory i ON s.model_id = i.id "
                        + "WHERE COALESCE(p.type, i.type) LIKE '" + type + "' "
                        + "ORDER BY s.date DESC";
            } else {
                query
                        = "SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, "
                        + "       s.date, s.invoice_id, s.model_name, COALESCE(p.type, i.type) AS type "
                        + " ,s.inventory_type "
                        + "FROM sales s "
                        + "LEFT JOIN product p ON s.model_id = p.id "
                        + "LEFT JOIN inventory i ON s.model_id = i.id "
                        + "ORDER BY s.date DESC";
            }
        }

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("inventory_type")
                );
                list.add(sale);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء جلب بيانات المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    public void showSalesDataforSales() {
        ObservableList<Sales> listofsales = getSalesData(Boolean.TRUE);
        col_invoiceId_sales.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        col_modelId.setCellValueFactory(new PropertyValueFactory<>("model_id"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        Sales_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_model_sales.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        col_type_sales.setCellValueFactory(new PropertyValueFactory<>("type"));

        salesTable.setItems(listofsales);
    }

    public void invoicesSelectData() {
        Invoices invoices = invoiceTable.getSelectionModel().getSelectedItem();
        if (invoices == null) {
            return;
        }

        int invoiceId = invoices.getInvoice_id();

        ObservableList<Sales> searchResult = FXCollections.observableArrayList();

//        String sql = "SELECT * FROM sales WHERE invoice_id LIKE ? ORDER BY date DESC";
        String sql = "SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, pr.type ,s.inventory_type \n"
                + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE s.invoice_id LIKE ? ORDER BY s.date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("inventory_type")
                );
                searchResult.add(sale);
            }

            salesTable.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchSalesFlexible(LocalDate fromDate, LocalDate toDate, String searchText) {
        ObservableList<Sales> searchResult = FXCollections.observableArrayList();
        String type = itemTypeList_salesForm.getSelectionModel().getSelectedItem();
        // لو المستخدم لم يدخل أي شيء
        if ((fromDate == null && toDate == null) && (searchText == null || searchText.trim().isEmpty())) {
            E_Alert("يرجى إدخال تاريخ أو بيانات بحث", AlertType.WARNING);
            return;
        }

//        StringBuilder sql = new StringBuilder("SELECT * FROM sales WHERE 1=1");
        StringBuilder sql = new StringBuilder("SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, pr.type \n"
                + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (itemTypeList_salesForm.getSelectionModel().getSelectedItem() != null) {
            sql.append(" AND pr.type LIKE ?");
            parameters.add(type);
        }
        if (fromDate != null && toDate != null) {
            sql.append(" AND s.date BETWEEN ? AND ?");
            parameters.add(fromDate.toString());
            parameters.add(toDate.toString());
        } else if (fromDate != null) {
            sql.append(" AND s.date >= ?");
            parameters.add(fromDate.toString());
        } else if (toDate != null) {
            sql.append(" AND s.date <= ?");
            parameters.add(toDate.toString());
        }

        if (searchText != null && !searchText.trim().isEmpty()) {
            sql.append(" AND (s.model_name LIKE ? OR CAST(s.model_id AS TEXT) LIKE ? OR CAST(s.invoice_id AS TEXT) LIKE ?)");
            String pattern = "%" + searchText.trim() + "%";
            parameters.add(pattern);
            parameters.add(pattern);
            parameters.add(pattern);
        }

        sql.append(" ORDER BY s.date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("inventory_type")
                );
                searchResult.add(sale);
            }
            salesTable.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void sales_search_salesForm() {
        String searchText = salesModelID_search.getText().trim();
        String type = itemTypeList_salesForm.getSelectionModel().getSelectedItem();
        sales_search(searchText, type, salesTable);
    }

    public void sales_search(String searchText, String type, TableView<Sales> salesTable) {
        ObservableList<Sales> searchResult = FXCollections.observableArrayList();

//        if (searchText.isEmpty()) {
//            E_Alert("يرجى إدخال اسم الموديل للبحث", Alert.AlertType.WARNING);
//            return;
//        }
        StringBuilder sql = new StringBuilder("SELECT s.sales_id, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, pr.type ,s.inventory_type  \n"
                + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE (s.model_name LIKE ? OR s.model_id LIKE ? OR s.invoice_id LIKE ? OR pr.type LIKE ?)");

        if (type != null) {
            sql.append(" AND pr.type LIKE ?");
        }
        sql.append(" ORDER BY s.date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ps.setString(3, "%" + searchText + "%");
            ps.setString(4, "%" + searchText + "%");

            if (type != null) {
                ps.setString(5, "%" + type + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("inventory_type")
                );
                searchResult.add(sale);
            }
            salesTable.setItems(searchResult);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<Expenses> getExpensesData() {
        ObservableList<Expenses> list = FXCollections.observableArrayList();
        String query = "SELECT expense_id, cash, vodafone, instaPay, reason, em_username, withdrawn_by, date, payStatues, (cash + vodafone + instapay) AS amount FROM expenses ORDER BY date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Expenses exp = new Expenses(
                        rs.getInt("expense_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("cash"),
                        rs.getDouble("vodafone"),
                        rs.getDouble("instaPay"),
                        rs.getString("reason"),
                        rs.getString("em_username"),
                        rs.getString("withdrawn_by"),
                        rs.getString("date"),
                        rs.getString("payStatues")
                );
                list.add(exp);
            }

        } catch (SQLException e) {
            E_Alert("فشل جلب بيانات المصروفات من قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    public void statues_List() {
        ObservableList<String> listData = FXCollections.observableArrayList(EStatues);
        statues_list_expenses.setItems(listData);
    }

    public void showExpensesData() {
        ObservableList<Expenses> list = getExpensesData();

        col_expenseId.setCellValueFactory(new PropertyValueFactory<>("expense_id"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_cash_expenses.setCellValueFactory(new PropertyValueFactory<>("cash"));
        col_vodafone.setCellValueFactory(new PropertyValueFactory<>("vodafone"));
        col_instaPay.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        col_reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        col_emUsername_expenses.setCellValueFactory(new PropertyValueFactory<>("em_username"));
        col_withdrawnBy.setCellValueFactory(new PropertyValueFactory<>("withdrawn_by"));
        col_date_expenses.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_payStatues_expenses.setCellValueFactory(new PropertyValueFactory<>("payStatues"));

        expensesTable.setItems(list);
    }

    public void clearEeForm() {
        withdrawnBy_field.clear();
        reason_field.clear();
        cash_field.setText("0");
        vodafone_field.setText("0");
        instaPay_field.setText("0");
        searchByName.clear();
        fromDatePickerEx.setValue(null);
        toDatePickerEx.setValue(null);
        statues_list_expenses.getSelectionModel().clearSelection();
        totalOutgoing_expenses.clear();
        totalExpenses_expenses.clear();
        showExpensesData();
    }

    public void insertExpense() {
        String withdrawnBy = withdrawnBy_field.getText().trim();
        String reason = reason_field.getText().trim();
        String cashText = cash_field.getText().trim();
        String vodafoneText = vodafone_field.getText().trim();
        String instaPayText = instaPay_field.getText().trim();
        String statues = statues_list_expenses.getSelectionModel().getSelectedItem();

        if (withdrawnBy.isEmpty() || reason.isEmpty() || statues == null) {
            E_Alert("يرجى إدخال جميع البيانات المطلوبة", AlertType.ERROR);
            return;
        }

        if (isntNumeric(cashText) || isntNumeric(vodafoneText) || isntNumeric(instaPayText)) {
            E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = cashText.isEmpty() ? 0.0 : Double.parseDouble(cashText);
        double Dvodafone = vodafoneText.isEmpty() ? 0.0 : Double.parseDouble(vodafoneText);
        double DinstaPay = instaPayText.isEmpty() ? 0.0 : Double.parseDouble(instaPayText);

        if (Dcash < 0 || Dvodafone < 0 || DinstaPay < 0) {
            E_Alert("لا يمكن إدخال مبالغ سالبة", AlertType.ERROR);
            return;
        }

        if (Dcash > data.TotalCashPay || Dvodafone > data.TotalVodafoneCash || DinstaPay > data.TotalInstaPay) {
            E_Alert("المبلغ المسحوب أكبر من الرصيد المتوفر", AlertType.ERROR);
            return;
        }

        Expenses expense = new Expenses(Dcash, Dvodafone, DinstaPay, reason, data.username, withdrawnBy, statues);

        String sql = "INSERT INTO expenses (cash, vodafone, instapay, reason, em_username, withdrawn_by, payStatues) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setDouble(1, expense.getCash());
            ps.setDouble(2, expense.getVodafone());
            ps.setDouble(3, expense.getInstaPay());
            ps.setString(4, expense.getReason());
            ps.setString(5, expense.getEm_username());
            ps.setString(6, expense.getWithdrawn_by());
            ps.setString(7, expense.getPayStatues());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                E_Alert("تمت إضافة المصروف بنجاح", AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء إضافة المصروف", AlertType.ERROR);
            e.printStackTrace();
        }

        clearEeForm();
        showExpensesData();
    }

    public void getExpensesSelection() {
        expensesTable.setOnMouseClicked(event -> {
            Expenses selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                withdrawnBy_field.setText(selectedExpense.getWithdrawn_by());
                reason_field.setText(selectedExpense.getReason());
                cash_field.setText(String.valueOf(selectedExpense.getCash()));
                vodafone_field.setText(String.valueOf(selectedExpense.getVodafone()));
                instaPay_field.setText(String.valueOf(selectedExpense.getInstaPay()));
                statues_list_expenses.getSelectionModel().select(selectedExpense.getPayStatues());
            }
        });
    }

    public void payExpense() {
        Expenses selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
        int id = selectedExpense.getExpense_id();

        if (selectedExpense == null) {
            E_Alert("اختر الصف الذي تريد سداد المصروف له", AlertType.WARNING);
            return;
        }

        String withdrawnBy = withdrawnBy_field.getText();
        String reason = reason_field.getText();
        String cashText = cash_field.getText().trim();
        String vodafoneText = vodafone_field.getText().trim();
        String instaPayText = instaPay_field.getText().trim();
        String statues = statues_list_expenses.getSelectionModel().getSelectedItem();

        if (isntNumeric(cashText) || isntNumeric(vodafoneText) || isntNumeric(instaPayText)) {
            E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = Double.parseDouble(cashText);
        double Dvodafone = Double.parseDouble(vodafoneText);
        double DinstaPay = Double.parseDouble(instaPayText);
        double Dtotal = Dcash + Dvodafone + DinstaPay;

        if (withdrawnBy == null || withdrawnBy.isEmpty() || reason.isEmpty() || statues == null) {
            E_Alert("ادخل كل البيانات من فضلك", AlertType.ERROR);
            return;
        }

        if (Dcash < 0 || Dvodafone < 0 || DinstaPay < 0) {
            E_Alert("ادخل المبالغ اكبر من الصفر", AlertType.ERROR);
            return;
        }

        String sql = "UPDATE expenses SET cash = ?, vodafone = ?, instapay = ?, "
                + "reason = ?, payStatues = ?, withdrawn_by = ? "
                + "WHERE expense_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setDouble(1, Dcash);
            ps.setDouble(2, Dvodafone);
            ps.setDouble(3, DinstaPay);
            ps.setString(4, reason);
            ps.setString(5, statues);
            ps.setString(6, withdrawnBy);
            ps.setInt(7, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                E_Alert("تم تعديل المصروف بنجاح.", AlertType.INFORMATION);
            } else {
                E_Alert("لم يتم العثور على المصروف بالتحديد المطلوب!", AlertType.WARNING);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تعديل المصروف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        clearEeForm();
        showExpensesData();
    }

    public void searchExpenses() {
        ObservableList<Expenses> searchResult = FXCollections.observableArrayList();

        LocalDate fromDate = fromDatePickerEx.getValue();
        LocalDate toDate = toDatePickerEx.getValue();
        String searchText = searchByName.getText().trim();

        StringBuilder sql = new StringBuilder("SELECT expense_id, cash, vodafone, instaPay, reason, em_username, withdrawn_by, date, payStatues, (cash + vodafone + instapay) AS amount FROM expenses WHERE 1=1");
        StringBuilder sumSql = new StringBuilder("SELECT payStatues, SUM(cash + vodafone + instaPay) AS totalAmount FROM expenses WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (!searchText.isEmpty()) {
            sql.append(" AND withdrawn_by LIKE ? OR expense_id LIKE ? OR payStatues LIKE ? OR reason LIKE ? OR em_username LIKE ?");
            sumSql.append(" AND withdrawn_by LIKE ? OR expense_id LIKE ? OR payStatues LIKE ? OR reason LIKE ? OR em_username LIKE ?");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDate != null && toDate != null) {
            sql.append(" AND date BETWEEN ? AND ?");
            sumSql.append(" AND date BETWEEN ? AND ?");
            parameters.add(fromDate.format(formatter));
            parameters.add(toDate.format(formatter));
        } else if (fromDate != null) {
            sql.append(" AND date >= ?");
            sumSql.append(" AND date >= ?");
            parameters.add(fromDate.format(formatter));
        } else if (toDate != null) {
            sql.append(" AND date <= ?");
            sumSql.append(" AND date <= ?");
            parameters.add(toDate.format(formatter));
        }

        sql.append(" ORDER BY date DESC");
        sumSql.append(" GROUP BY payStatues");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString()); PreparedStatement sumPs = connect.prepareStatement(sumSql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
                sumPs.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Expenses expense = new Expenses(
                            rs.getInt("expense_id"),
                            rs.getDouble("amount"),
                            rs.getDouble("cash"),
                            rs.getDouble("vodafone"),
                            rs.getDouble("instaPay"),
                            rs.getString("reason"),
                            rs.getString("em_username"),
                            rs.getString("withdrawn_by"),
                            rs.getString("date"),
                            rs.getString("payStatues")
                    );
                    searchResult.add(expense);
                }
            }
            expensesTable.setItems(searchResult);

            // حساب الإجماليات
            try (ResultSet sumRs = sumPs.executeQuery()) {
                double totalExpenses = 0.0;
                double totalOutflows = 0.0;

                while (sumRs.next()) {
                    String status = sumRs.getString("payStatues");
                    double sum = sumRs.getDouble("totalAmount");

                    if ("المصروفات".equals(status)) {
                        totalExpenses = sum;
                    } else if ("الخوارج".equals(status)) {
                        totalOutflows = sum;
                    }
                }

                totalExpenses_expenses.setText(String.format("%.2f", totalExpenses));
                totalOutgoing_expenses.setText(String.format("%.2f", totalOutflows));
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء البحث أو حساب المجموع", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addCustomerIfNotExists(String name, String phone, String address) {
        String checkSql = "SELECT COUNT(*) FROM customers WHERE phone = ?";
        String insertSql = "INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            checkStmt.setString(1, phone);
//            checkStmt.setString(1, name);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, phone);
                    insertStmt.setString(3, address);
                    insertStmt.executeUpdate();
                    System.out.println("Customer added successfully.");
                } else {
                    System.out.println("Customer already exists.");
                }
            }

        } catch (SQLException e) {
            E_Alert(" حدث خطأ أثناء اضافة بيانات العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadClientsInvoices() {
        ObservableList<Clients> list = FXCollections.observableArrayList();

        String sql = "SELECT clientName, clientPhone, "
                + "SUM(total_quantity) AS total_quantity, "
                + "SUM(total_price_after_discount) AS total_price_after_discount "
                + "FROM invoices "
                + "WHERE clientName IS NOT NULL AND clientPhone IS NOT NULL "
                + "GROUP BY clientName, clientPhone "
                + "ORDER BY MAX(invoice_date) DESC";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // We don't have invoice_id here because we're aggregating data per client
                String name = rs.getString("clientName");
                String phone = rs.getString("clientPhone");
                int totalQty = rs.getInt("total_quantity");
                double totalAfter = rs.getDouble("total_price_after_discount");

                list.add(new Clients(name, phone, totalQty, totalAfter)); // Pass 0 or ignore invoiceId
            }
//            clients_col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
            clients_col_clientName_clientForm.setCellValueFactory(new PropertyValueFactory<>("clientName"));
            clients_col_clientPhone_clientForm.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
            clients_col_totalQty.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
            clients_col_totalAfterDiscount.setCellValueFactory(new PropertyValueFactory<>("totalAfterDiscount"));
            clientsTable.setItems(list);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تحميل فواتير العملاء", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchClientsInvoices() {
        String searchText = clientSearch.getText();
        ObservableList<Clients> list = FXCollections.observableArrayList();

        String sql = "SELECT clientName, clientPhone, "
                + "SUM(total_quantity) AS total_quantity, "
                + "SUM(total_price_after_discount) AS total_price_after_discount "
                + "FROM invoices "
                + "WHERE clientName IS NOT NULL AND clientPhone IS NOT NULL "
                + "AND (clientName LIKE ? OR clientPhone LIKE ?) "
                + "GROUP BY clientName, clientPhone "
                + "ORDER BY MAX(invoice_date) DESC";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String keyword = "%" + searchText + "%";
            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("clientName");
                    String phone = rs.getString("clientPhone");
                    int totalQty = rs.getInt("total_quantity");
                    double totalAfter = rs.getDouble("total_price_after_discount");

                    list.add(new Clients(name, phone, totalQty, totalAfter));
                }
            }

            clientsTable.setItems(list);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchClientSales() {
        String keyword = salesModelID_search_flientForm.getText();
        ObservableList<ClientSale> clientSalesList = FXCollections.observableArrayList();

        String sql = "SELECT s.model_id, s.model_name, "
                + "s.quantity, s.total_price, s.date, s.invoice_id "
                + "FROM sales s "
                + "WHERE CAST(s.invoice_id AS TEXT) LIKE ? "
                + "   OR CAST(s.model_id AS TEXT) LIKE ? "
                + "   OR s.model_name LIKE ? "
                + "ORDER BY s.date DESC";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ClientSale sale = new ClientSale(
                        rs.getString("date"),
                        rs.getDouble("total_price"),
                        rs.getInt("quantity"),
                        rs.getString("model_name"),
                        rs.getInt("model_id"),
                        rs.getInt("invoice_id")
                );
                clientSalesList.add(sale);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        clientSales_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientSales_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        clientSales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clientSales_col_model_sales.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        clientSales_col_modelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        clientSales_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        clientSalesTable.setItems(clientSalesList);
    }

    public void loadClientSales() {
        ObservableList<ClientSale> clientSalesList = FXCollections.observableArrayList();

        String sql = "SELECT s.model_id, s.model_name, "
                + "s.quantity, s.total_price, s.date, s.invoice_id "
                + "FROM sales s "
                + "ORDER BY s.date DESC";

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ClientSale sale = new ClientSale(
                        rs.getString("date"),
                        rs.getDouble("total_price"),
                        rs.getInt("quantity"),
                        rs.getString("model_name"),
                        rs.getInt("model_id"),
                        rs.getInt("invoice_id")
                );
                clientSalesList.add(sale);
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        clientSales_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientSales_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        clientSales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clientSales_col_model_sales.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        clientSales_col_modelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        clientSales_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        clientSalesTable.setItems(clientSalesList);
    }

    public void searchClientsAndPurchases() {
        String nameOrPhone = clientSearch.getText();
        String dateFrom = fromDatePickerClient.getValue() + "";
        String dateTo = toDatePickerClient.getValue() + "";
        ObservableList< Clients> clientsList = FXCollections.observableArrayList();
        ObservableList<ClientSale> clientSalesList = FXCollections.observableArrayList();

        StringBuilder sqlClients = new StringBuilder(
                "SELECT clientName, clientPhone, SUM(total_quantity) AS total_quantity, SUM(total_price_after_discount) AS total_price_after_discount "
                + "FROM invoices WHERE clientName IS NOT NULL AND clientPhone IS NOT NULL "
        );
        StringBuilder sqlSales = new StringBuilder(
                "SELECT s.model_id, s.model_name, s.quantity, s.total_price, s.date, i.invoice_id, i.clientName, i.clientPhone "
                + "FROM sales s "
                + "JOIN invoices i ON s.invoice_id = i.invoice_id "
                + "WHERE i.clientName IS NOT NULL AND i.clientPhone IS NOT NULL "
        );

        List<String> conditions = new ArrayList<>();
        if (nameOrPhone != null && !nameOrPhone.trim().isEmpty()) {
            conditions.add("(i.clientName LIKE ? OR i.clientPhone LIKE ?)");
            sqlClients.append(" AND (clientName LIKE ? OR clientPhone LIKE ?)");
        }
        if (dateFrom != null && !dateFrom.trim().isEmpty()) {
            conditions.add("date(i.invoice_date) >= date(?)");
            sqlClients.append(" AND date(invoice_date) >= date(?)");
        }
        if (dateTo != null && !dateTo.trim().isEmpty()) {
            conditions.add("date(i.invoice_date) <= date(?)");
            sqlClients.append(" AND date(invoice_date) <= date(?)");
        }

        for (String cond : conditions) {
            sqlSales.append(" AND ").append(cond);
        }

        sqlClients.append(" GROUP BY clientName, clientPhone ORDER BY MAX(invoice_date) DESC");

        try (Connection conn = database.getConnection(); PreparedStatement pstmtClients = conn.prepareStatement(sqlClients.toString()); PreparedStatement pstmtSales = conn.prepareStatement(sqlSales.toString())) {

            int index = 1;
            if (nameOrPhone != null && !nameOrPhone.trim().isEmpty()) {
                pstmtClients.setString(index, "%" + nameOrPhone + "%");
                pstmtClients.setString(index + 1, "%" + nameOrPhone + "%");
                pstmtSales.setString(index, "%" + nameOrPhone + "%");
                pstmtSales.setString(index + 1, "%" + nameOrPhone + "%");
                index += 2;
            }
            if (dateFrom != null && !dateFrom.trim().isEmpty()) {
                pstmtClients.setString(index, dateFrom);
                pstmtSales.setString(index, dateFrom);
                index++;
            }
            if (dateTo != null && !dateTo.trim().isEmpty()) {
                pstmtClients.setString(index, dateTo);
                pstmtSales.setString(index, dateTo);
            }

            // تحميل العملاء
            ResultSet rsClients = pstmtClients.executeQuery();
            while (rsClients.next()) {
                String name = rsClients.getString("clientName");
                String phone = rsClients.getString("clientPhone");
                int totalQty = rsClients.getInt("total_quantity");
                double totalAfter = rsClients.getDouble("total_price_after_discount");

                clientsList.add(new Clients(name, phone, totalQty, totalAfter));
            }
            clientsTable.setItems(clientsList);

            // تحميل المشتريات
            ResultSet rsSales = pstmtSales.executeQuery();
            while (rsSales.next()) {
                clientSalesList.add(new ClientSale(
                        rsSales.getString("date"),
                        rsSales.getDouble("total_price"),
                        rsSales.getInt("quantity"),
                        rsSales.getString("model_name"),
                        rsSales.getInt("model_id"),
                        rsSales.getInt("invoice_id")
                ));
            }
            clientSalesTable.setItems(clientSalesList);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadSelectedClientSalesByDate() {
        Clients selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        LocalDate dateFrom = fromDatePickerClient.getValue();
        LocalDate dateTo = toDatePickerClient.getValue();

        if (selectedClient == null) {
            System.out.println("لم يتم اختيار أي عميل.");
            return;
        }

        String clientName = selectedClient.getClientName();
        String clientPhone = selectedClient.getClientPhone();

        ObservableList<ClientSale> clientSalesList = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder(
                "SELECT s.model_id, s.model_name, "
                + "s.quantity, s.total_price, s.date, s.invoice_id "
                + "FROM sales s "
                + "JOIN invoices i ON s.invoice_id = i.invoice_id "
                + "WHERE i.clientName = ? AND i.clientPhone = ?"
        );

        if (dateFrom != null) {
            sql.append(" AND date(i.invoice_date) >= date(?)");
        }
        if (dateTo != null) {
            sql.append(" AND date(i.invoice_date) <= date(?)");
        }

        sql.append(" ORDER BY i.invoice_date DESC");

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            pstmt.setString(index++, clientName);
            pstmt.setString(index++, clientPhone);

            if (dateFrom != null) {
                pstmt.setString(index++, dateFrom + "");
            }
            if (dateTo != null) {
                pstmt.setString(index++, dateTo + "");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                clientSalesList.add(new ClientSale(
                        rs.getString("date"),
                        rs.getDouble("total_price"),
                        rs.getInt("quantity"),
                        rs.getString("model_name"),
                        rs.getInt("model_id"),
                        rs.getInt("invoice_id")
                ));
            }

            // إعداد الأعمدة
            clientSales_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            clientSales_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            clientSales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            clientSales_col_model_sales.setCellValueFactory(new PropertyValueFactory<>("modelName"));
            clientSales_col_modelId.setCellValueFactory(new PropertyValueFactory<>("modelId"));
            clientSales_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

            clientSalesTable.setItems(clientSalesList);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadProfitByTypeReport() {
        ObservableList<ProfitReport> reportList = FXCollections.observableArrayList();

        LocalDate fromDate = fromDatePickerCapital.getValue();
        LocalDate toDate = toDatePickerCapital.getValue();

        StringBuilder sql = new StringBuilder("SELECT pr.type, "
                + "SUM(s.quantity) AS totalQuantity, "
                + "SUM(s.totalWholesalesPrice) AS totalWholesalesPrice, "
                + "SUM(s.total_price) AS totalPrice, "
                + "(SUM(s.total_price) - SUM(s.totalWholesalesPrice)) AS gain "
                + "FROM sales s "
                + "LEFT JOIN product pr ON s.model_id = pr.id "
                + "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (fromDate != null) {
            sql.append("AND s.date >= ? ");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            sql.append("AND s.date <= ? ");
            params.add(toDate.format(formatter));
        }

        sql.append("GROUP BY pr.type ORDER BY gain DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i).toString());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProfitReport report = new ProfitReport(
                        rs.getString("type"),
                        rs.getInt("totalQuantity"),
                        rs.getDouble("totalWholesalesPrice"),
                        rs.getDouble("totalPrice"),
                        rs.getDouble("gain")
                );
                reportList.add(report);
            }

            // ربط البيانات بالجدول في واجهة المستخدم
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            quantity_col.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
            wholesales_col.setCellValueFactory(new PropertyValueFactory<>("totalWholesalesPrice"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            gain_col.setCellValueFactory(new PropertyValueFactory<>("gain"));

            profitReportTable.setItems(reportList);

        } catch (SQLException e) {
            e.printStackTrace();
            E_Alert("حدث خطأ أثناء تحميل تقرير الأرباح", Alert.AlertType.ERROR);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void unavailableProduct() {
        String query = "SELECT COUNT(*) AS unAvailable_model FROM product WHERE available = 'غير متاح'";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                data.UnavailbleProductCount = rs.getInt("unAvailable_model");
                UnavailbleProductCount_label.setText(String.valueOf(data.UnavailbleProductCount));
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء جلب عدد المنتجات غير المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void AvailableProducts() {
        String query = "SELECT count(model) AS all_AvailableProduct FROM product WHERE available = 'متاح'";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                data.AvailbleProductCount = rs.getInt("all_AvailableProduct");
                AvailbleProductCount_label.setText(String.valueOf(data.AvailbleProductCount));
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء جلب عدد المنتجات المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalInstaPay() {
        String invoicesQuery = "SELECT SUM(instaPay) AS total_invoices FROM invoices";
        String expensesQuery = "SELECT SUM(instaPay) AS total_expenses FROM expenses";

        try (Connection conn = database.getConnection(); PreparedStatement invoicesPS = conn.prepareStatement(invoicesQuery); PreparedStatement expensesPS = conn.prepareStatement(expensesQuery); ResultSet invoicesRs = invoicesPS.executeQuery(); ResultSet expensesRs = expensesPS.executeQuery()) {

            double totalInvoices = 0;
            double totalExpenses = 0;

            if (invoicesRs.next()) {
                totalInvoices = invoicesRs.getDouble("total_invoices");
            }

            if (expensesRs.next()) {
                totalExpenses = expensesRs.getDouble("total_expenses");
            }

            data.TotalInstaPay = totalInvoices - totalExpenses;
            total_instaPay_field.setText(String.format("%.2f $", data.TotalInstaPay));

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي InstaPay", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalVodafoneCash() {
        String invoicesVodafoneSQL = "SELECT COALESCE(SUM(vodafonePay), 0) AS total_invoices FROM invoices";
        String expensesVodafoneSQL = "SELECT COALESCE(SUM(vodafone), 0) AS total_expenses FROM expenses";

        try (Connection connect = database.getConnection(); PreparedStatement invoicePS = connect.prepareStatement(invoicesVodafoneSQL); PreparedStatement expensePS = connect.prepareStatement(expensesVodafoneSQL); ResultSet invoiceRS = invoicePS.executeQuery(); ResultSet expenseRS = expensePS.executeQuery()) {
            double totalInvoices = 0;
            double totalExpenses = 0;

            if (invoiceRS.next()) {
                totalInvoices = invoiceRS.getDouble("total_invoices");
            }
            if (expenseRS.next()) {
                totalExpenses = expenseRS.getDouble("total_expenses");
            }

            data.TotalVodafoneCash = totalInvoices - totalExpenses;
            total_vodafoneCash_field.setText(String.format("%.2f $", data.TotalVodafoneCash));

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب رصيد فودافون", Alert.AlertType.ERROR);
            e.printStackTrace(); // طباعة الخطأ للمساعدة في التصحيح
        }
    }

    public void totalCash() {
        String totalCashInvoicesSQL = "SELECT COALESCE(SUM(cashPay), 0) AS total_cash_invoices FROM invoices";
        String totalCashExpensesSQL = "SELECT COALESCE(SUM(cash), 0) AS total_cash_expenses FROM expenses";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(totalCashInvoicesSQL); PreparedStatement expensesPS = connect.prepareStatement(totalCashExpensesSQL); ResultSet invoicesRS = invoicesPS.executeQuery(); ResultSet expensesRS = expensesPS.executeQuery()) {
            double totalInvoices = 0;
            double totalExpenses = 0;

            if (invoicesRS.next()) {
                totalInvoices = invoicesRS.getDouble("total_cash_invoices");
            }
            if (expensesRS.next()) {
                totalExpenses = expensesRS.getDouble("total_cash_expenses");
            }

            data.TotalCashPay = totalInvoices - totalExpenses;
            total_Cash_field.setText(String.format("%.2f $", data.TotalCashPay));

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب الكاش", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

// Calculates total expenses with payStatues = 'المصروفات'
    public void totalExpenses() {
        String query = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE payStatues = 'المصروفات'";

        try (Connection connect = database.getConnection(); PreparedStatement stmt = connect.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                data.TotalExpense = rs.getDouble("total_expenses");

                tota_expenses_field.setText(String.format("%.2f $", data.TotalExpense));
            }
        } catch (Exception e) {
            E_Alert("حدث خطأ أثناء حساب المصروفات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalWholePrice() {
        String sql = "SELECT COALESCE(SUM(totalWholesalesPrice), 0) AS total_wholesale FROM sales WHERE quantity > 0";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalWholepriceinCapital = rs.getDouble("total_wholesale");
                total_sales_wholesale_price.setText(String.format("%.2f $", data.TotalWholepriceinCapital));
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPrice() {
        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice FROM invoices";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalRealPriceinCapital = rs.getDouble("total_realPrice");
                total_sales_Real_price.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي السعر الفعلي", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

//    public void totalAvailableProduct_wholePrice() {
//        String sql = "SELECT COALESCE(SUM(wholesaleprice * mNumberavailable), 0) AS total_wholePrice FROM product WHERE mNumberavailable > 0";
//
//        try (
//                Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                data.TotalAvailabelWholeprice = rs.getDouble("total_wholePrice");
//                totalAvailableWholeprice_field.setText(String.format("%.2f $", data.TotalAvailabelWholeprice));
//            }
//        } catch (SQLException e) {
//            E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة للمنتجات المتوفرة", Alert.AlertType.ERROR);
//            e.printStackTrace();
//        }
//    }
//    public void totalAvailableProduct_realPrice() {
//        String sql = "SELECT COALESCE(SUM(realprice * mNumberavailable), 0) AS total_realPrice FROM product WHERE mNumberavailable > 0";
//
//        try (
//                Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                data.TotalAvailabelRealprice = rs.getDouble("total_realPrice");
//                totalAvailableRealprice_field.setText(String.format("%.2f $", data.TotalAvailabelRealprice));
//            }
//        } catch (SQLException e) {
//            E_Alert("حدث خطأ أثناء حساب إجمالي السعر الحقيقي للمنتجات المتوفرة", Alert.AlertType.ERROR);
//            e.printStackTrace();
//        }
//    }
    public void totalReturnMoney() {
        String sql = "SELECT COALESCE(SUM(price_of_return), 0) AS total_returnPrice FROM returns";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalRetrunPrice = rs.getDouble("total_returnPrice");
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي قيمة المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalReturnMoney(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(price_of_return), 0) AS total_returnPrice FROM returns WHERE return_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {
//            ps.setDate(1, java.sql.Date.valueOf(fromDate));
//            ps.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data.TotalRetrunPrice = rs.getDouble("total_returnPrice");
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب المرتجعات خلال الفترة المحددة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalInstaPay(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", AlertType.ERROR);
            return;
        }

        String invoicesQuery = "SELECT COALESCE(SUM(instaPay), 0) AS total_invoices FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        String expensesQuery = "SELECT COALESCE(SUM(instaPay), 0) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery)) {
            // إعداد التواريخ للطلبين
//            invoicesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            invoicesPS.setDate(2, java.sql.Date.valueOf(toDate));
//            expensesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            expensesPS.setDate(2, java.sql.Date.valueOf(toDate));

            try (
                    ResultSet invoicesRs = invoicesPS.executeQuery(); ResultSet expensesRs = expensesPS.executeQuery()) {
                if (invoicesRs.next() && expensesRs.next()) {
                    double totalInvoices = invoicesRs.getDouble("total_invoices");
                    double totalExpenses = expensesRs.getDouble("total_expenses");

                    data.TotalInstaPay = totalInvoices - totalExpenses;
                    total_instaPay_field.setText(String.format("%.2f $", data.TotalInstaPay));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي Instapay للفترة المحددة", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalVodafoneCash(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", AlertType.ERROR);
            return;
        }

        String invoicesQuery = "SELECT COALESCE(SUM(vodafonePay), 0) AS total_invoices FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        String expensesQuery = "SELECT COALESCE(SUM(vodafone), 0) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery)) {
//            invoicesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            invoicesPS.setDate(2, java.sql.Date.valueOf(toDate));
//            expensesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            expensesPS.setDate(2, java.sql.Date.valueOf(toDate));

            try (
                    ResultSet invoicesRs = invoicesPS.executeQuery(); ResultSet expensesRs = expensesPS.executeQuery()) {
                if (invoicesRs.next() && expensesRs.next()) {
                    double totalInvoices = invoicesRs.getDouble("total_invoices");
                    double totalExpenses = expensesRs.getDouble("total_expenses");

                    data.TotalVodafoneCash = totalInvoices - totalExpenses;
                    total_vodafoneCash_field.setText(String.format("%.2f $", data.TotalVodafoneCash));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي Vodafone للفترة المحددة", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalCash(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String invoicesQuery = "SELECT COALESCE(SUM(cashPay), 0) AS total_invoices FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        String expensesQuery = "SELECT COALESCE(SUM(cash), 0) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery)) {
//            invoicesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            invoicesPS.setDate(2, java.sql.Date.valueOf(toDate));
//            expensesPS.setDate(1, java.sql.Date.valueOf(fromDate));
//            expensesPS.setDate(2, java.sql.Date.valueOf(toDate));

            try (
                    ResultSet invoicesRs = invoicesPS.executeQuery(); ResultSet expensesRs = expensesPS.executeQuery()) {
                if (invoicesRs.next() && expensesRs.next()) {
                    double totalInvoices = invoicesRs.getDouble("total_invoices");
                    double totalExpenses = expensesRs.getDouble("total_expenses");

                    data.TotalCashPay = totalInvoices - totalExpenses;
                    total_Cash_field.setText(String.format("%.2f $", data.TotalCashPay));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي الكاش للفترة المحددة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalExpenses(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String totalExpensesQuery = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND payStatues = 'المصروفات'";

        try (Connection connect = database.getConnection(); PreparedStatement expensePs = connect.prepareStatement(totalExpensesQuery)) {
//            expensePs.setDate(1, java.sql.Date.valueOf(fromDate));
//            expensePs.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet expenseRs = expensePs.executeQuery()) {
                if (expenseRs.next()) {
                    data.TotalExpense = expenseRs.getDouble("total_expenses");
                    tota_expenses_field.setText(String.format("%.2f $", data.TotalExpense));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب المصروفات للفترة المحددة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalOutgoing(LocalDate fromDate, LocalDate toDate) {
        String total_outgoing_Select = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND payStatues = 'الخوارج'";
        try (Connection connect = database.getConnection(); PreparedStatement outgoingPs = connect.prepareStatement(total_outgoing_Select)) {

//            outgoingPs.setDate(1, java.sql.Date.valueOf(fromDate));
//            outgoingPs.setDate(2, java.sql.Date.valueOf(toDate));
            ResultSet outgoingRs = outgoingPs.executeQuery();
            if (outgoingRs.next()) {
                data.TotalOutgoing = outgoingRs.getDouble("total_expenses");
                totalOutgoing_capital.setText(String.format("%.2f $", data.TotalOutgoing));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalWholePrice(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(totalWholesalesPrice), 0) AS total_wholesale FROM sales WHERE quantity > 0 AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement wholePricePs = connect.prepareStatement(sql)) {
//            wholePricePs.setDate(1, java.sql.Date.valueOf(fromDate));
//            wholePricePs.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet wholePriceRs = wholePricePs.executeQuery()) {
                if (wholePriceRs.next()) {
                    data.TotalWholepriceinCapital = wholePriceRs.getDouble("total_wholesale");
                    total_sales_wholesale_price.setText(String.format("%.2f $", data.TotalWholepriceinCapital));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة للمبيعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPrice(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement realPricePs = connect.prepareStatement(sql)) {
//            realPricePs.setDate(1, java.sql.Date.valueOf(fromDate));
//            realPricePs.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.TotalRealPriceinCapital = realPriceRs.getDouble("total_realPrice");
                    total_sales_Real_price.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي سعر البيع بعد الخصم", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPriceCurrentMonth() {
        LocalDate fromDate = LocalDate.now().withDayOfMonth(1);
        LocalDate toDate = fromDate.withDayOfMonth(fromDate.lengthOfMonth());

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (Connection connect = database.getConnection(); PreparedStatement realPricePs = connect.prepareStatement(sql)) {
//            realPricePs.setDate(1, java.sql.Date.valueOf(fromDate));
//            realPricePs.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.TotalRealPriceinCapital = realPriceRs.getDouble("total_realPrice");
                    totalPriceCurrentMonth_field.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب إجمالي مبيعات الشهر الحالي", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPriceToday() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date >= '" + today + "' AND invoice_date < '" + tomorrow + "'";

        try (Connection connect = database.getConnection(); PreparedStatement realPricePs = connect.prepareStatement(sql)) {
//            realPricePs.setDate(1, java.sql.Date.valueOf(today));
//            realPricePs.setDate(2, java.sql.Date.valueOf(tomorrow));

            try (ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.totalRealPriceToday = realPriceRs.getDouble("total_realPrice");
                    today_salaries.setText(String.format("%.2f $", data.totalRealPriceToday));
                }
            }
        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حساب مبيعات اليوم", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Calculates total outgoing with payStatues = 'الخوارج'
    public void totalOutgoing() {
        String query = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE payStatues = 'الخوارج'";

        try (Connection connect = database.getConnection(); PreparedStatement stmt = connect.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                data.TotalOutgoing = rs.getDouble("total_expenses");
                totalOutgoing_capital.setText(String.format("%.2f $", data.TotalOutgoing));
            }
        } catch (Exception e) {
            E_Alert("حدث خطأ أثناء حساب الخوارج", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void showCapital() {
        double cash = 0;

        totalWholePrice();
        totalRealPrice();
        totalReturnMoney();

        data.NetProfit_withoutDiscount = data.TotalRealPriceinCapital - data.TotalWholepriceinCapital;
        profit_without_deducting_expenses.setText(String.format("%.2f $", data.NetProfit_withoutDiscount));

        totalExpenses();
        totalOutgoing();

        totalCash();
        totalInstaPay();
        totalVodafoneCash();

        data.NetProfit = data.NetProfit_withoutDiscount - data.TotalExpense;
        NetProfit_field.setText(String.format("%.2f $", data.NetProfit));

        data.NetProfit_withOutgoing = data.NetProfit - data.TotalOutgoing;
        NetProfit_field_withOutgoing.setText(String.format("%.2f $", data.NetProfit_withOutgoing));

//        totalAvailableProduct_wholePrice();
//        totalAvailableProduct_realPrice();
        data.MoneySafe = data.TotalCashPay + data.TotalVodafoneCash + data.TotalInstaPay;

        Money_safe.setText(String.format("%.2f $", data.MoneySafe));

        unavailableProduct();
        AvailableProducts();

        totalRealPriceCurrentMonth();
        loadProfitByTypeReport();

        fromDatePickerCapital.setValue(null);
        toDatePickerCapital.setValue(null);
    }

    public void SearchByDateRangeForCapital() {
        LocalDate fromDateC = fromDatePickerCapital.getValue();
        LocalDate toDateC = toDatePickerCapital.getValue();
        if (fromDateC == null || toDateC == null) {
            E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", AlertType.ERROR);
        } else {
            totalWholePrice(fromDateC, toDateC);
            totalRealPrice(fromDateC, toDateC);

            totalReturnMoney(fromDateC, toDateC);
            data.NetProfit_withoutDiscount = data.TotalRealPriceinCapital - data.TotalWholepriceinCapital;
            profit_without_deducting_expenses.setText(String.format("%.2f $", data.NetProfit_withoutDiscount));
            totalExpenses(fromDateC, toDateC);
            totalOutgoing(fromDateC, toDateC);

            totalCash(fromDateC, toDateC);
            totalVodafoneCash(fromDateC, toDateC);
            totalInstaPay(fromDateC, toDateC);

            data.NetProfit = data.NetProfit_withoutDiscount - data.TotalExpense;
            NetProfit_field.setText(String.format("%.2f $", data.NetProfit));
//            totalAvailableProduct_wholePrice();
//            totalAvailableProduct_realPrice();
            data.MoneySafe = data.TotalCashPay + data.TotalVodafoneCash + data.TotalInstaPay;
            Money_safe.setText(String.format("%.2f $", data.MoneySafe));
            unavailableProduct();
            AvailableProducts();
            loadProfitByTypeReport();
        }

        fromDatePickerCapital.setValue(null);
        toDatePickerCapital.setValue(null);
    }

    public void mainPage() {
        totalRealPriceToday();
        Safe_main.setText(String.format("%.2f $", data.MoneySafe));
        unavailable_main.setText(data.UnavailbleProductCount + "");
        available_main.setText(data.AvailbleProductCount + "");
        loadDailySalesAreaChart();
    }

    public void loadDailySalesAreaChart() {
        dailySalesChart.getData().clear();

        String sql = "SELECT DATE(invoice_date) as day, SUM(total_price_after_discount) as total FROM invoices GROUP BY day ORDER BY day";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("مبيعات اليوم");

            while (rs.next()) {
                String day = rs.getString("day");
                double total = rs.getDouble("total");
                series.getData().add(new XYChart.Data<>(day, total));
            }

            dailySalesChart.getData().add(series);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء تجهيز الرسم البياني", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public void rolesGetData(int id) {
        String sql = "SELECT * FROM user_roles WHERE employee_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mainPage_checkBox.setSelected(rs.getInt("mainPage") == 1);
                returnPage_checkBox.setSelected(rs.getInt("returnPage") == 1);
                addItemsPage_checkBox.setSelected(rs.getInt("addItemsPage") == 1);
                missingItemsPage_checkBox.setSelected(rs.getInt("missingItemsPage") == 1);
                salesPage_checkBox.setSelected(rs.getInt("salesPage") == 1);
                expensesPage_checkBox.setSelected(rs.getInt("expensesPage") == 1);
                clientsPage_checkBox.setSelected(rs.getInt("clientsPage") == 1);
                monyPage_checkBox.setSelected(rs.getInt("monyPage") == 1);
                sellPage_checkBox.setSelected(rs.getInt("sellPage") == 1);
            }

        } catch (Exception e) {
            E_Alert("حدث خطأ أثناء تحميل الصلاحيات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void setRoleParameters(PreparedStatement stmt, int employeeId) throws SQLException {
        stmt.setInt(1, mainPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(2, returnPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(3, addItemsPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(4, missingItemsPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(5, salesPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(6, expensesPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(7, clientsPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(8, monyPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(9, sellPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(10, employeeId);
    }

    public void saveUserRoles(int employeeId) {
        String checkSql = "SELECT id FROM user_roles WHERE employee_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement checkStmt = connect.prepareStatement(checkSql)) {

            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // موجود: نعمل UPDATE
                String updateSql = "UPDATE user_roles SET "
                        + "mainPage = ?, returnPage = ?, addItemsPage = ?, missingItemsPage = ?, "
                        + "salesPage = ?, expensesPage = ?, clientsPage = ?, monyPage = ?, sellPage = ? "
                        + "WHERE employee_id = ?";

                try (PreparedStatement updateStmt = connect.prepareStatement(updateSql)) {
                    setRoleParameters(updateStmt, employeeId);
                    updateStmt.executeUpdate();
                }

            } else {
                // غير موجود: نعمل INSERT
                String insertSql = "INSERT INTO user_roles (employee_id, mainPage, returnPage, addItemsPage, "
                        + "missingItemsPage, salesPage, expensesPage, clientsPage, monyPage, sellPage) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement insertStmt = connect.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, employeeId);
                    insertStmt.setInt(2, mainPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(3, returnPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(4, addItemsPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(5, missingItemsPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(6, salesPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(7, expensesPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(8, clientsPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(9, monyPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(10, sellPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.executeUpdate();
                }
            }

            E_Alert("تم حفظ الصلاحيات بنجاح", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء حفظ الصلاحيات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void saveRolesBtn() {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            E_Alert("الرجاء تحديد الموظف من الجدول", AlertType.ERROR);
            return;
        }
        int selectedEmployeeId = employee.getId();
        saveUserRoles(selectedEmployeeId);
    }

    public UserRoles loadRolesForUser(int employeeId) {
        UserRoles roles = new UserRoles();
        String sql = "SELECT * FROM user_roles WHERE employee_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roles.mainPage = rs.getBoolean("mainPage");
                roles.returnPage = rs.getBoolean("returnPage");
                roles.addItemsPage = rs.getBoolean("addItemsPage");
                roles.missingItemsPage = rs.getBoolean("missingItemsPage");
                roles.salesPage = rs.getBoolean("salesPage");
                roles.expensesPage = rs.getBoolean("expensesPage");
                roles.clientsPage = rs.getBoolean("clientsPage");
                roles.monyPage = rs.getBoolean("monyPage");
                roles.sellPage = rs.getBoolean("sellPage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public void initUserPermissions(int employeeId, String username) {
        data.employeeId = employeeId;
        data.username = username;

        if (!username.equalsIgnoreCase("admin")) {
            UserRoles userRoles = loadRolesForUser(employeeId);

            mainPage_btn.setVisible(userRoles.mainPage);
            returnedProductBtn.setVisible(userRoles.returnPage);
            addProduct_btn.setVisible(userRoles.addItemsPage);
            employee_btn.setVisible(userRoles.missingItemsPage);
            dailySells_btn.setVisible(userRoles.salesPage);
            expenses_btn.setVisible(userRoles.expensesPage);
            clients_btn.setVisible(userRoles.clientsPage);
            capital_btn.setVisible(userRoles.monyPage);
            sell_btn.setVisible(userRoles.sellPage);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public void switchFrom(ActionEvent event) {
        if (event.getSource() == mainPage_btn) {
            main_form.setVisible(true);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            mainPage();
            showCapital();
            inventoryAvailableList();
            inventoryShowData();
            stockShowData();
            departmentShowData();
        } else if (event.getSource() == sell_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(true);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            menueShowData();
            menuRemoveBtn();
            loadDepartmentsIntoComboBox();
            requestTypeList_menuForm.getSelectionModel().clearSelection();
            data.shiftId = getShiftId();
        } else if (event.getSource() == addProduct_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(true);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            inventoryShowData();
            stockShowData();
            missingProductShowData();
            retrunProductShowData();
            inventoryClearBtn();
            stockClearBtn();
        } else if (event.getSource() == employee_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(true);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
        } else if (event.getSource() == dailySells_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(true);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            showSalesDataforSales();
            showInvoicesData();
            clearShifts();
            fromDatePicker.setValue(null);
            toDatePicker.setValue(null);
            itemTypeList_salesForm.getSelectionModel().clearSelection();
            invoice_search.clear();
            salesModelID_search.clear();
        } else if (event.getSource() == expenses_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(true);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            showExpensesData();
            clearEeForm();
        } else if (event.getSource() == returnedProductBtn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(true);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            returnMethodList.getSelectionModel().clearSelection();
            returnMethodList();
            returnShowData();
            returnRemoveBtn();
            setQuantityr();
        } else if (event.getSource() == capital_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(true);
            clients_form.setVisible(false);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            showCapital();
        } else if (event.getSource() == clients_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(true);
            settings_form.setVisible(false);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            fromDatePickerClient.setValue(null);
            toDatePickerClient.setValue(null);
            loadClientsInvoices();
            loadClientSales();
        } else if (event.getSource() == settings_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            employee_form.setVisible(false);
            returned_form.setVisible(false);
            product_form.setVisible(false);
            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            clients_form.setVisible(false);
            settings_form.setVisible(true);
            open_shifts_form.setVisible(false);
            close_shifts_form.setVisible(false);
            employeddShowData();
            tableShowData();
            departmentShowData();
        }
    }

    public void logout() {
        try {
            // TO HIDE MAIN FORM
            logout_btn.getScene().getWindow().hide();

            // LINK YOUR LOGIN FORM AND SHOW IT
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Resturant POS system");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            E_Alert("حدث خطأ أثناء تسجيل الخروج", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadInfoToOpenShiftForm() {
        showCapital();
        cashier_label.setText(data.username);
        opening_balance_open_shifts_form.setText(String.format("%.2f $", data.MoneySafe));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        data.openingShiftTime = now.format(formatter);
        opening_date_open_shifts_form.setText(now.format(formatter));

        cash_open_shifts_form.setText(String.format("%.2f $", data.TotalCashPay));
        vodafoneCash_open_shifts_form.setText(String.format("%.2f $", data.TotalVodafoneCash));
        instaPay_open_shifts_form.setText(String.format("%.2f $", data.TotalInstaPay));
    }

    public void loadInfoToCloseShiftForm() {
        showCapital();
//    cashier_label.setText(data.username);
        data.openinBalance = getOpeningBalance();
        opening_balance_close_shifts_form.setText(String.format("%.2f $", data.openinBalance));
        closing_balance_close_shifts_form.setText(String.format("%.2f $", data.MoneySafe));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        data.closingShiftTime = now.format(formatter);

        // getShiftStartTime دلوقتي بيرجع formatted string جاهز
        String startTimeStr = getShiftStartTime();
        data.openingShiftTime = startTimeStr;
        // نعرض مباشرة بدون parsing
        opening_date_close_shifts_form.setText(startTimeStr);
        closing_date_close_shifts_form.setText(now.format(formatter));

        cash_close_shifts_form.setText(String.format("%.2f $", data.TotalCashPay));
        vodafoneCash_close_shifts_form.setText(String.format("%.2f $", data.TotalVodafoneCash));
        instaPay_close_shifts_form.setText(String.format("%.2f $", data.TotalInstaPay));
    }

    public void openShift() {
        double startingBalance = data.MoneySafe;
        String sql = "INSERT INTO shifts (start_time, starting_balance, status, username) VALUES (?, ?, 'open', ?)";
        try (Connection connect = database.getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, LocalDateTime.now().toString());
            pstmt.setDouble(2, startingBalance);
            pstmt.setString(3, data.username);
            pstmt.executeUpdate();
            open_shifts_form.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            E_Alert("حدث خطأ اثناء فتح الوردية", AlertType.ERROR);
        }
    }

    // Get opening balance of the current open shift
    public Double getOpeningBalance() {
        String sql = "SELECT starting_balance FROM shifts WHERE status = 'open' LIMIT 1";
        try (Connection connect = database.getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("starting_balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no open shift
    }

    // Get start time of the current open shift
    public String getShiftStartTime() {
        String sql = "SELECT start_time FROM shifts WHERE status = 'open' LIMIT 1";
        try (Connection connect = database.getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                String dbValue = rs.getString("start_time");
                if (dbValue != null) {
                    try {
                        // Parse using ISO format (e.g., 2025-09-04T12:44:50.601)
                        LocalDateTime dateTime = LocalDateTime.parse(dbValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                        // Format into desired pattern
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        return dateTime.format(outputFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return dbValue; // fallback: return raw string
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no open shift
    }

    public int getShiftId() {
        String sql = "SELECT id FROM shifts WHERE status = 'open'";

        try (Connection connect = database.getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("id");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void checkOpenShift() {
        String currentUsername = data.username;

        int openShiftId = -1;
        String shiftOwner = null;

        String selectSql = "SELECT id, username FROM shifts WHERE status = 'open' LIMIT 1";
        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(selectSql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                openShiftId = rs.getInt("id");
                shiftOwner = rs.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (openShiftId != -1) {
            if (shiftOwner.equals(currentUsername)) {
                // ✅ نفس المستخدم → تنبيه فقط
                Alert shiftAlert = new Alert(Alert.AlertType.INFORMATION);
                shiftAlert.setTitle("وردية مفتوحة");
                shiftAlert.setHeaderText("يوجد وردية مفتوحة خاصة بك");
                shiftAlert.setContentText("سيتم الاستمرار في نفس الوردية الحالية.");
                shiftAlert.showAndWait();

                open_shifts_form.setVisible(false);

            } else {
                // ❌ لمستخدم آخر → تنبيه + تسجيل خروج
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("وردية مفتوحة لمستخدم آخر");
                alert.setHeaderText("يوجد وردية مفتوحة للمستخدم: " + shiftOwner);
                alert.setContentText("لا يمكنك فتح وردية جديدة. سيتم تسجيل خروجك الآن.");
                alert.showAndWait();

                logout();
            }
        } else {
            // مفيش وردية مفتوحة → افتح واحدة جديدة
            openShiftsForm();
        }
    }

    public boolean closeShiftForOtherUser(String currentUser) {
        final int MAX_RETRIES = 5;
        final int RETRY_DELAY_MS = 100;

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                return attemptCloseShift(currentUser);
            } catch (SQLException e) {
                if (e.getMessage().contains("SQLITE_BUSY") && attempt < MAX_RETRIES - 1) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                } else {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    private boolean attemptCloseShift(String currentUser) throws SQLException {
        double endingBalance = data.MoneySafe;
        String selectSql = "SELECT id FROM shifts WHERE status = 'open' LIMIT 1";
        String updateSql = "UPDATE shifts SET end_time = ?, ending_balance = ?, "
                + "status = 'closed', closed_by = ? WHERE id = ?";

        try (Connection conn = database.getConnection()) {
            conn.setAutoCommit(false);

            int openShiftId = -1;
            try (PreparedStatement ps = conn.prepareStatement(selectSql); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    openShiftId = rs.getInt("id");
                }
            }

            if (openShiftId != -1) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, LocalDateTime.now().toString());
                    ps.setDouble(2, endingBalance);
                    ps.setString(3, currentUser);
                    ps.setInt(4, openShiftId);
                    ps.executeUpdate();
                }
            }
            conn.commit();
            return true;
        }
    }

    public void openShiftsForm() {
        open_shifts_form.setVisible(true);
        close_shifts_form.setVisible(false);
        main_form.setVisible(false);
        menu_form.setVisible(false);
        employee_form.setVisible(false);
        returned_form.setVisible(false);
        product_form.setVisible(false);
        sales_form.setVisible(false);
        expenses_form.setVisible(false);
        capital_form.setVisible(false);
        clients_form.setVisible(false);
        settings_form.setVisible(false);
        loadInfoToOpenShiftForm();
    }

    public void logoutBtn() {
        closeShiftsForm();
    }

    public void closeShiftsForm() {
        open_shifts_form.setVisible(false);
        close_shifts_form.setVisible(true);
        main_form.setVisible(false);
        menu_form.setVisible(false);
        employee_form.setVisible(false);
        returned_form.setVisible(false);
        product_form.setVisible(false);
        sales_form.setVisible(false);
        expenses_form.setVisible(false);
        capital_form.setVisible(false);
        clients_form.setVisible(false);
        settings_form.setVisible(false);
        data.shiftId = getShiftId();
        showSalesDataforShifts_closingForm();
        loadInfoToCloseShiftForm();
    }

    public void closeShiftBtn() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("إغلاق الوردية");
        confirmAlert.setHeaderText("هل تريد طباعة تقرير الإغلاق؟");
        confirmAlert.setContentText("اختر 'نعم' للطباعة أو 'لا' لتسجيل الخروج مباشرة.");

        ButtonType printButton = new ButtonType("نعم، اطبع", ButtonBar.ButtonData.YES);
        ButtonType logoutButton = new ButtonType("لا، تسجيل الخروج", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmAlert.getButtonTypes().setAll(printButton, logoutButton, cancelButton);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == printButton) {
                // إغلاق الوردية وطباعة التقرير
                if (closeShiftForOtherUser(data.username)) {
                    printShiftReport(); // ← دي فانكشن الطباعة بتاعة التقرير
                }
            } else if (result.get() == logoutButton) {
                // إغلاق الوردية وتسجيل الخروج
                if (closeShiftForOtherUser(data.username)) {
                    logout(); // ← دي فانكشن تسجيل الخروج عندك
                }
            }
        }
    }

    public void printShiftReport() {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("cashier", data.username);
        parameters.put("opiningDate", data.openingShiftTime);
        parameters.put("closingDate", data.closingShiftTime);
        parameters.put("opiningBalance", data.openinBalance);
        parameters.put("closingBalance", data.MoneySafe);

        parameters.put("cash", String.format("%.2f $", data.TotalCashPay));
        parameters.put("vodafone", String.format("%.2f $", data.TotalVodafoneCash));
        parameters.put("instaPay", String.format("%.2f $", data.TotalInstaPay));

        printReport("shifts_report.jasper", parameters, getSalesDataForShifts(data.shiftId));
        logout();
    }

    public ObservableList<Sales> getSalesDataForShifts(Integer shiftId) {
        ObservableList<Sales> list = FXCollections.observableArrayList();

        String query = "SELECT s.sales_id, s.model_id, s.quantity, s.total_price, "
                + "s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, pr.type  ,s.inventory_type "
                + "FROM sales s "
                + "LEFT JOIN product pr ON s.model_id = pr.id "
                + "INNER JOIN invoices inv ON s.invoice_id = inv.invoice_id "
                + "WHERE s.quantity > 0 ";

        if (shiftId != null) {
            query += "AND inv.shiftsId = ? ";
        }

        query += "ORDER BY s.date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(query)) {

            if (shiftId != null) {
                ps.setInt(1, shiftId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sales sale = new Sales(
                            rs.getInt("sales_id"),
                            rs.getInt("invoice_id"),
                            rs.getInt("model_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("total_price"),
                            rs.getString("date"),
                            rs.getString("model_name"),
                            rs.getString("type"),
                            rs.getString("inventory_type")
                    );
                    list.add(sale);
                }
            }

        } catch (SQLException e) {
            E_Alert("حدث خطأ أثناء جلب بيانات المبيعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    public void showSalesDataforShifts_closingForm() {
        ObservableList<Sales> listofsales = getSalesDataForShifts(data.shiftId);

        col_date_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_totalPrice_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        col_quantity_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_type_sales_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_model_sales_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        col_modelId_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("model_id"));
        col_invoiceId_sales_close_shifts_form.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));

        salesTable_close_shifts_form.setItems(listofsales);
    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    public void loadShiftsData() {
        ObservableList<Shift> shiftsList = FXCollections.observableArrayList();

        String query = "SELECT * FROM shifts ORDER BY id DESC";

        try (Connection conn = database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String closedBy = rs.getString("closed_by");
                double startingBalance = rs.getDouble("starting_balance");
                double endingBalance = rs.getDouble("ending_balance");

                // تحويل النصوص من SQLite إلى LocalDateTime
                LocalDateTime openingTime = rs.getString("start_time") != null
                        ? LocalDateTime.parse(rs.getString("start_time").replace(" ", "T")) : null;

                LocalDateTime closingTime = rs.getString("end_time") != null
                        ? LocalDateTime.parse(rs.getString("end_time").replace(" ", "T")) : null;

                shiftsList.add(new Shift(id, username, closedBy, startingBalance, endingBalance, openingTime, closingTime));
            }

            // ربط الأعمدة بالبيانات
            shifts_col_shiftId.setCellValueFactory(new PropertyValueFactory<>("id"));
            shifts_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            shifts_col_closedBy.setCellValueFactory(new PropertyValueFactory<>("closedBy"));
            shifts_col_openingBalance.setCellValueFactory(new PropertyValueFactory<>("startingBalance"));
            shifts_col_closingBalance.setCellValueFactory(new PropertyValueFactory<>("endingBalance"));
            shifts_col_openingTime.setCellValueFactory(new PropertyValueFactory<>("openingTimeFormatted"));
            shifts_col_closingTime.setCellValueFactory(new PropertyValueFactory<>("closingTimeFormatted"));

            // عرض البيانات
            shiftsTable.setItems(shiftsList);

        } catch (Exception e) {
            e.printStackTrace();
            // هنا ممكن تعمل Alert
        }
    }

    public void searchShifts(LocalDate fromDate, LocalDate toDate, String keyword) {
        ObservableList<Shift> shiftsList = FXCollections.observableArrayList();

        StringBuilder query = new StringBuilder("SELECT * FROM shifts WHERE 1=1 ");

        // لو في تاريخ "من"
        if (fromDate != null) {
            query.append(" AND date(start_time) >= date(?) ");
        }

        // لو في تاريخ "إلى"
        if (toDate != null) {
            query.append(" AND date(start_time) <= date(?) ");
        }

        // لو في كلمة مفتاحية (id أو اسم مستخدم أو مغلق وردية)
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append(" AND (username LIKE ? OR closed_by LIKE ? OR id LIKE ?) ");
        }

        query.append(" ORDER BY id DESC");

        try (Connection conn = database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;

            if (fromDate != null) {
                pstmt.setString(paramIndex++, fromDate.toString());
            }

            if (toDate != null) {
                pstmt.setString(paramIndex++, toDate.toString());
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                pstmt.setString(paramIndex++, likeKeyword); // username
                pstmt.setString(paramIndex++, likeKeyword); // closed_by
                pstmt.setString(paramIndex++, likeKeyword); // id
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String closedBy = rs.getString("closed_by");
                double startingBalance = rs.getDouble("starting_balance");
                double endingBalance = rs.getDouble("ending_balance");

                LocalDateTime openingTime = rs.getString("start_time") != null
                        ? LocalDateTime.parse(rs.getString("start_time").replace(" ", "T")) : null;

                LocalDateTime closingTime = rs.getString("end_time") != null
                        ? LocalDateTime.parse(rs.getString("end_time").replace(" ", "T")) : null;

                shiftsList.add(new Shift(id, username, closedBy, startingBalance, endingBalance, openingTime, closingTime));
            }

            shiftsTable.setItems(shiftsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchForShifts() {
        LocalDate from = fromDatePicker_shifts.getValue();
        LocalDate to = toDatePicker_shifts.getValue();
        String searchText = search_shifts.getText();
        searchShifts(from, to, searchText);
    }

    public void showSalesDataforShifts(Integer id) {
        ObservableList<Sales> listofsales = getSalesDataForShifts(id);

        Sales_col_date_shifts.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_totalPrice_shifts.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        col_quantity_shifts.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_type_sales_shifts.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_model_sales_shifts.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        col_modelId_shifts.setCellValueFactory(new PropertyValueFactory<>("model_id"));
        col_invoiceId_sales_shifts.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));

        salesTable_shifts.setItems(listofsales);
    }

    public void shiftSelectData() {
        Shift shift = shiftsTable.getSelectionModel().getSelectedItem();
        if (shift != null) {
            showSalesDataforShifts(shift.getId());
        }
    }

    public void clearShifts() {
        loadShiftsData();
        showSalesDataforShifts(null);
        fromDatePicker_shifts.setValue(null);
        toDatePicker_shifts.setValue(null);
        search_shifts.clear();
        shiftsTable.getSelectionModel().clearSelection();
        itemTypeList_shiftsForm.getSelectionModel().clearSelection();
        salesShifts_search.clear();
    }

    public void sales_search_shiftsForm() {
        String searchText = salesShifts_search.getText();
        String type = itemTypeList_shiftsForm.getSelectionModel().getSelectedItem();
        sales_search(searchText, type, salesTable_shifts);
    }

    public String getOpenShiftUser() {
        String openUser = null;
        String sql = "SELECT username FROM shifts WHERE status = 'open' LIMIT 1";

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                openUser = rs.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return openUser; // null لو مفيش شيفت مفتوح
    }

    public void checkOpenShift_test() {
        String openUser = getOpenShiftUser();
        if (openUser != null) {
            if (openUser.equals(data.username)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "لديك شيفت مفتوح بالفعل، يمكنك الاستمرار فيه.", ButtonType.OK);
                alert.showAndWait();
//                openShiftsForm(); // لو مفيش شيفت مفتوح، افتح جديد مباشرة
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "يوجد شيفت مفتوح لمستخدم آخر: " + openUser + "\nيجب اغلاق الوردية اولا", ButtonType.OK);
                alert.showAndWait();
                closeShiftsForm(); // دالة عندك مسبقًا لفتح صفحة تسجيل الشيفت
            }
        } else {
            openShiftsForm();// لو مفيش شيفت مفتوح، افتح جديد مباشرة
        }
    }

    public void addTables() {
        List<TableData> list = tabserves.getTables();
        ObservableList<TableData> observableList = FXCollections.observableArrayList(list);

        String name = tableNameTextField.getText();

        if (name == null || name.isEmpty()) {
            E_Alert("ادخل اسم الطاولة من فضلك", AlertType.ERROR);
            return; // خروج عشان ميتكملش الكود
        }

        // check لو الاسم موجود بالفعل
        boolean exists = observableList.stream()
                .anyMatch(table -> table.getName().equalsIgnoreCase(name));

        if (exists) {
            E_Alert("هذا الاسم موجود بالفعل، من فضلك اختر اسم آخر", AlertType.WARNING);
            return;
        }

        // لو مش موجود → نضيف
        boolean success = tabserves.addTable(name);

        if (success) {
            E_Alert("تم حفظ الطاولة بنجاح", AlertType.INFORMATION);
            tableNameTextField.clear();
            tableShowData();
        } else {
            E_Alert("فشلت عملية الحفظ", AlertType.ERROR);
        }
    }

    public void deleteTables() {
        TableData tables = tableOfTables.getSelectionModel().getSelectedItem();
        Boolean check = false;
        if (tables == null) {
            E_Alert("حدد الطاولة المراد حذفها من فضلك", AlertType.ERROR);
        } else {
            check = tabserves.deleteTable(tables.getId());
        }

        if (check) {
            E_Alert("تم حذف الطاولة بنجاح", AlertType.INFORMATION);
            tableOfTables.getSelectionModel().clearSelection();
            tableShowData();
        } else {
            E_Alert("فشلت عمليت الحذف", AlertType.ERROR);
        }
    }

    public void tableShowData() {
        List<TableData> list = tabserves.getTables();
        ObservableList<TableData> observableList = FXCollections.observableArrayList(list);
        col_tablesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_tablesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableOfTables.setItems(observableList);
    }

    public void tableSelectData() {
        TableData tables = tableOfTables.getSelectionModel().getSelectedItem();
        tableNameTextField.setText(tables.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        if (!data.username.equalsIgnoreCase("admin")) {
//        }
//        checkOpenShift();
        checkOpenShift_test();
        clearShifts();
        loadInfoToOpenShiftForm();
        showCapital();
        displayUsername();
        inventoryAvailableList();
        inventoryShowData();
        stockShowData();
        menueShowData();
        returnShowData();
        stockShowData();
        statues_List();
        tableShowData();
        loadDepartmentsIntoComboBox();
        setupCustomerAutoFill();
        inventory_Search.textProperty().addListener((obs, oldVal, newVal) -> inventorySearchAndFilter());
        missingProduct_Search.textProperty().addListener((obs, oldVal, newVal) -> searchMissingProduct());
        invoice_search.textProperty().addListener((obs, oldVal, newVal) -> searchInvoices());
        salesModelID_search.textProperty().addListener((obs, oldVal, newVal) -> sales_search_salesForm());
        searchByName.textProperty().addListener((obs, oldVal, newVal) -> searchExpenses());
        disqaunt_textField.textProperty().addListener((obs, oldVal, newVal) -> finalizeInvoice());
        clientSearch.textProperty().addListener((obs, oldVal, newVal) -> searchClientsInvoices());
        salesModelID_search_flientForm.textProperty().addListener((obs, oldVal, newVal) -> searchClientSales());
        searchEmployee.textProperty().addListener((obs, oldVal, newVal) -> searchEmployeeFromDB());
        search_shifts.textProperty().addListener((obs, oldVal, newVal) -> searchForShifts());
        salesShifts_search.textProperty().addListener((obs, oldVal, newVal) -> sales_search_shiftsForm());

        totalRealPriceToday();
        Safe_main.setText(String.format("%.2f $", data.MoneySafe));
        unavailable_main.setText(data.UnavailbleProductCount + "");
        available_main.setText(data.AvailbleProductCount + "");
    }
}
