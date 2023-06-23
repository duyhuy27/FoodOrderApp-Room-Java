package huyndph30375.fpoly.foodorder3.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import huyndph30375.fpoly.foodorder3.model.FoodSummary;

@Dao
public interface DaoSummary {

    @Insert
    void insertSummary(FoodSummary foodSummary);

    @Update
    void updateSummary(FoodSummary foodSummary);

    @Delete
    void deleteSummary(FoodSummary foodSummary);

    @Query("SELECT * FROM food_summary")
    List<FoodSummary> getSummaryList();

    @Query("SELECT * FROM Food_summary WHERE getNameFood = :name")
    FoodSummary getSummaryByName(String name);

    @Query("DELETE FROM food_summary")
    void deleteAllSummaries();


}
