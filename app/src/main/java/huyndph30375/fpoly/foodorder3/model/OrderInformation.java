package huyndph30375.fpoly.foodorder3.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orderTb")
public class OrderInformation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int IdOrder;
    private String nameSummary;
    private String address;
    private String payment;
    private int totalPrice;
    private int quantity;


    public OrderInformation(String nameSummary, String address, String payment, int totalPrice, int quantity) {
        this.nameSummary = nameSummary;
        this.address = address;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public OrderInformation() {
    }

    public int getIdOrder() {
        return IdOrder;
    }

    public void setIdOrder(int idOrder) {
        IdOrder = idOrder;
    }

    public String getNameSummary() {
        return nameSummary;
    }

    public void setNameSummary(String nameSummary) {
        this.nameSummary = nameSummary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
