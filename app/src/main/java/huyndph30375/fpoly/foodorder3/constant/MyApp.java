package huyndph30375.fpoly.foodorder3.constant;

import android.app.Application;

import huyndph30375.fpoly.foodorder3.room.FoodDatabase;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FoodDatabase.getInstance(getApplicationContext());
    }
}
