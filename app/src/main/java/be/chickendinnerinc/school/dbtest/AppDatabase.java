package be.chickendinnerinc.school.dbtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Thomas on 4/12/2017.
 */


@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context){
        if(instance == null) instance = Room.databaseBuilder(context, AppDatabase.class, "CorgiWoef").build();
        return instance;
    }

    public static void destroy(){
        instance = null;
    }

    public abstract UserDao userDao();

}

