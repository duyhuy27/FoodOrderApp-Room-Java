package huyndph30375.fpoly.foodorder3.model;

import android.os.Bundle;
import android.view.View;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import huyndph30375.fpoly.foodorder3.activity.FinishOrderActivity;
import huyndph30375.fpoly.foodorder3.constant.GlobalFunction;

@Entity(tableName = "Food_summary")
public class FoodSummary implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int IdSummary;
    private String getNameFood;
    private int countFood;
    private int price;


    public FoodSummary(String getNameFood, int countFood, int price) {
        this.getNameFood = getNameFood;
        this.countFood = countFood;
        this.price = price;
    }

    public FoodSummary() {
    }

    public void goToFinishOrder(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("foodSummary", this);
        GlobalFunction.startActivity(view.getContext(), FinishOrderActivity.class, bundle);
    }


    public int getIdSummary() {
        return IdSummary;
    }

    public void setIdSummary(int idSummary) {
        IdSummary = idSummary;
    }

    public String getGetNameFood() {
        return getNameFood;
    }

    public void setGetNameFood(String getNameFood) {
        this.getNameFood = getNameFood;
    }

    public int getCountFood() {
        return countFood;
    }

    public void setCountFood(int countFood) {
        this.countFood = countFood;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
