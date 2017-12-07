package be.chickendinnerinc.school.dbtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Thomas on 4/12/2017.
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "hashed_password")
    private String hashedPassword;

    private String email;

    public User(String firstName, String lastName, String hashedPassword, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }

    public void setUid(int uid){
        this.uid = uid;
    }


    public int getUid() {
        return uid;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }

}
