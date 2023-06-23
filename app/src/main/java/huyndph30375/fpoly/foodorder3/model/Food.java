package huyndph30375.fpoly.foodorder3.model;

import android.os.Bundle;
import android.view.View;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import huyndph30375.fpoly.foodorder3.activity.FoodDetailActivity;
import huyndph30375.fpoly.foodorder3.constant.GlobalFunction;
import huyndph30375.fpoly.foodorder3.listener.IClickItemCartListener;

@Entity(tableName = "food")
public class Food implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int price;
    private int picsFood;
    private int numberInCart;
    private int totalPrice;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Food(int id, String title, String description, int price, int picsFood, int numberInCart) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.picsFood = picsFood;
        this.numberInCart = numberInCart;
    }

    public Food(String title, String description, int price, int picsFood) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.picsFood = picsFood;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPicsFood() {
        return picsFood;
    }

    public void setPicsFood(int picsFood) {
        this.picsFood = picsFood;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public void goToFoodDetail(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("food", this);
        GlobalFunction.startActivity(view.getContext(), FoodDetailActivity.class, bundle);
    }

    public void PlusFood(View view) {
        int newCount = getNumberInCart() + 1;
        int totalPrice = getPrice() * newCount;
        setNumberInCart(newCount);
        setTotalPrice(totalPrice);

    }

    public void Subtract(View view) {
        int count = getNumberInCart();
        if (count <= 1) {
            return;
        }
        int newCount = getNumberInCart() - 1;

        int totalPrice = getPrice() * newCount;
        setNumberInCart(newCount);
        setTotalPrice(totalPrice);

    }

}
