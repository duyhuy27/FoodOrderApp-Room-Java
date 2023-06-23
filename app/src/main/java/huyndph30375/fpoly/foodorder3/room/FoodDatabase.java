package huyndph30375.fpoly.foodorder3.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import huyndph30375.fpoly.foodorder3.model.Address;
import huyndph30375.fpoly.foodorder3.model.Food;
import huyndph30375.fpoly.foodorder3.model.FoodSummary;
import huyndph30375.fpoly.foodorder3.model.OrderInformation;

@Database(entities = {Food.class, Address.class, OrderInformation.class, FoodSummary.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "food.db";

    private static FoodDatabase instance;

    public static synchronized FoodDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FoodDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract DaoFood daoFood();
    public abstract DaoAddress daoAddress();
    public abstract DaoOrder daoOrder();
    public abstract DaoSummary daoSummary();

}
