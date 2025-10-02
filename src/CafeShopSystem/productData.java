package CafeShopSystem;

public class productData {

    private int id, soldNo, availableNo;
    private int quantitiy;
    private String model, type, available;
    private String dataType;
    private Double wholesalePrice, realPrice;

    public productData(int id, String model, Double wholesalePrice, Double realPrice, String type, int soldNo, String available) {
        this.id = id;
        this.model = model;
        this.wholesalePrice = wholesalePrice;
        this.realPrice = realPrice;
        this.type = type;
        this.soldNo = soldNo;
        this.available = available;
    }

    public productData(int id, String model, Double realPrice) {
        this.id = id;
        this.model = model;
        this.realPrice = realPrice;
    }

    public productData(int id, String model, Double realPrice, String type) {
        this.id = id;
        this.model = model;
        this.realPrice = realPrice;
        this.type = type;
    }
    
    public productData(int id, String model, Double realPrice, String type,int quantitiy, String dataType) {
        this.id = id;
        this.model = model;
        this.realPrice = realPrice;
        this.type = type;
        this.quantitiy = quantitiy;
        this.dataType = dataType;
    }
    
    public productData(int id, String model, Double wholesalePrice, Double realPrice, String type, int soldNo) {
        this.id = id;
        this.model = model;
        this.wholesalePrice = wholesalePrice;
        this.realPrice = realPrice;
        this.type = type;
        this.soldNo = soldNo;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public Double getRealPrice() {
        return realPrice;
    }

    public int getSoldNo() {
        return soldNo;
    }

    public String getType() {
        return type;
    }

    /**
     * @return the available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return the availableNo
     */
    public int getAvailableNo() {
        return availableNo;
    }

    /**
     * @param availableNo the availableNo to set
     */
    public void setAvailableNo(int availableNo) {
        this.availableNo = availableNo;
    }

    /**
     * @return the quantitiy
     */
    public int getQuantitiy() {
        return quantitiy;
    }

    /**
     * @param quantitiy the quantitiy to set
     */
    public void setQuantitiy(int quantitiy) {
        this.quantitiy = quantitiy;
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
}
