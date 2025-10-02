package CafeShopSystem;

public class Invoices {

    private int invoice_id;
    private int total_quantity;
    private int shiftsId;
    private int status;
    private String reqType;
    private double total_price_before_discount;
    private double discount_percentage;
    private double total_price_after_discount;
    private double cashPay;
    private double instaPay;
    private double vodafonePay;
    private String username;
    private String invoice_date;
    private String restTable;
    private String dataType;
    private String clientName;
    private String clientPhone;
    

    public Invoices() {
    }
    
    public Invoices(int invoice_id, int total_quantity, double total_price_before_discount, double discount_percentage, double total_price_after_discount, double cashPay, double instaPay, double vodafonePay, String username, String invoice_date, String clientName, String clientPhone) {
        this.invoice_id = invoice_id;
        this.total_quantity = total_quantity;
        this.total_price_before_discount = total_price_before_discount;
        this.total_price_after_discount = total_price_after_discount;
        this.discount_percentage = discount_percentage;
        this.cashPay = cashPay;
        this.instaPay = instaPay;
        this.vodafonePay = vodafonePay;
        this.username = username;
        this.invoice_date = invoice_date;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public double getTotal_price_before_discount() {
        return total_price_before_discount;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public double getTotal_price_after_discount() {
        return total_price_after_discount;
    }

    public double getCashPay() {
        return cashPay;
    }

    public double getInstaPay() {
        return instaPay;
    }

    public double getVodafonePay() {
        return vodafonePay;
    }

    public String getUsername() {
        return username;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * @param invoice_id the invoice_id to set
     */
    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     * @param total_quantity the total_quantity to set
     */
    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    /**
     * @param total_price_before_discount the total_price_before_discount to set
     */
    public void setTotal_price_before_discount(double total_price_before_discount) {
        this.total_price_before_discount = total_price_before_discount;
    }

    /**
     * @param discount_percentage the discount_percentage to set
     */
    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    /**
     * @param total_price_after_discount the total_price_after_discount to set
     */
    public void setTotal_price_after_discount(double total_price_after_discount) {
        this.total_price_after_discount = total_price_after_discount;
    }

    /**
     * @param cashPay the cashPay to set
     */
    public void setCashPay(double cashPay) {
        this.cashPay = cashPay;
    }

    /**
     * @param instaPay the instaPay to set
     */
    public void setInstaPay(double instaPay) {
        this.instaPay = instaPay;
    }

    /**
     * @param vodafonePay the vodafonePay to set
     */
    public void setVodafonePay(double vodafonePay) {
        this.vodafonePay = vodafonePay;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param invoice_date the invoice_date to set
     */
    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    /**
     * @return the restTable
     */
    public String getRestTable() {
        return restTable;
    }

    /**
     * @param restTable the restTable to set
     */
    public void setRestTable(String restTable) {
        this.restTable = restTable;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @param clientPhone the clientPhone to set
     */
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    /**
     * @return the shiftsId
     */
    public int getShiftsId() {
        return shiftsId;
    }

    /**
     * @param shiftsId the shiftsId to set
     */
    public void setShiftsId(int shiftsId) {
        this.shiftsId = shiftsId;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the reqType
     */
    public String getReqType() {
        return reqType;
    }

    /**
     * @param reqType the reqType to set
     */
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
}
