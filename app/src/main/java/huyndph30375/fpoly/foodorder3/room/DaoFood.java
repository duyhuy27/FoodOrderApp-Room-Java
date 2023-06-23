package huyndph30375.fpoly.foodorder3.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import huyndph30375.fpoly.foodorder3.model.Food;

@Dao
public interface DaoFood {
    @Insert
    void insertNote(Food food);

    List<Food> getListFood();

    @Delete
    void deleteNote(Food food);

    @Update
    void updateNote(Food food);

    @Query("SELECT * FROM FOOD WHERE title LIKE '%' || :title || '%'")
    List<Food> search(String title);

}
