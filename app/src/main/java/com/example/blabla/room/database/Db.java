package com.example.blabla.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.blabla.room.converter.BitmapConverter;
import com.example.blabla.room.converter.DateConverter;
import com.example.blabla.room.dao.UserDao;
import com.example.blabla.room.entity.User;


@Database(entities = {User.class},version = 3)
@TypeConverters({BitmapConverter.class, DateConverter.class})
public abstract class Db extends RoomDatabase {
    public abstract UserDao userDao();
    private static Db INSTANCE;

    public static Db getDatabase(final Context context) {
        if(INSTANCE==null) {
            synchronized (Db.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),Db.class,"database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
