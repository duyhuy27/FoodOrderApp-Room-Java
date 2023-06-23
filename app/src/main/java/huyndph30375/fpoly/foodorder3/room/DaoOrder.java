package huyndph30375.fpoly.foodorder3.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import huyndph30375.fpoly.foodorder3.model.OrderInformation;

@Dao
public interface DaoOrder {

    @Insert
    void insertOrder(OrderInformation orderInformation);

    @Update
    void updateOrder(OrderInformation orderInformation);

    @Delete
    void deleteOrder(OrderInformation orderInformation);

    @Query("SELECT * FROM orderTb")
    List<OrderInformation> getOrderList();

    @Query("SELECT * FROM orderTb WHERE payment LIKE '%' || :payment  || '%'")
    List<OrderInformation> searchOrder(String payment);

}
