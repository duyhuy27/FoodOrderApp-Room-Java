package huyndph30375.fpoly.foodorder3.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import huyndph30375.fpoly.foodorder3.model.Address;

@Dao
public interface DaoAddress {

    @Insert
    void InsertAddress(Address address);

    @Delete
    void DeleteAddress(Address address);

    @Update
    void UpdateAddress(Address address);

    @Query("SELECT * FROM address")
    List<Address> getDataAddress();

    @Query("SELECT * FROM ADDRESS WHERE address LIKE '%' || :address || '%'")
    List<Address> SearchAddress(String address);
}
