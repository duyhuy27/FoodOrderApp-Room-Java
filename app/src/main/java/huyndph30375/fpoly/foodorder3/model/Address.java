package huyndph30375.fpoly.foodorder3.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "address")
public class Address implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idAddress;
    private String name;
    private String address;
    private String detailsAddress;
    private String noteToDriver;

    public Address(String name, String address, String detailsAddress, String noteToDriver) {
        this.name = name;
        this.address = address;
        this.detailsAddress = detailsAddress;
        this.noteToDriver = noteToDriver;
    }

    public Address() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailsAddress() {
        return detailsAddress;
    }

    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }

    public String getNoteToDriver() {
        return noteToDriver;
    }

    public void setNoteToDriver(String noteToDriver) {
        this.noteToDriver = noteToDriver;
    }
}
