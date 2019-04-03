package javaProject;

public class DatabaseTable {

    private String itemCategory;
    private String itemName;
    private Integer itemNumber;
    private Integer itemPrice;
    private Integer quantity;
    private String firstName;

    public DatabaseTable(String itemCategory, String itemName, Integer itemNumber, Integer itemPrice, Integer quantity, String firstName) {
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.firstName = firstName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
