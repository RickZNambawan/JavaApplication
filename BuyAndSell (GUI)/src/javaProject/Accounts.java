package javaProject;

public class Accounts {
    private String itemCategory;
    private String itemName;
    private int itemNumber;
    private int itemPrice;
    private int quantity;
    private String firstName;

    public Accounts (String itemCategory, String itemName, int itemNumber, int itemPrice, int quantity, String firstName) {
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

    public String getItemName() {
        return itemName;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getFirstName() {
        return firstName;
    }

}
