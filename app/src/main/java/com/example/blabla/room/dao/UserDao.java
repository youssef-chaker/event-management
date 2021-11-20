package com.example.blabla.room.dao;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blabla.room.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("delete from users where access_token=:token")
    void deleteUser(String token);

    @Query("delete from users")
    void clearAllUsers();

    @Update
    void updateUser(User user);

//    @Query("update users set avatar=:bm where token=:token")
//    void addAvatar(String token , Bitmap bm);

    @Delete
    void deleteUser(User user);

    @Query("select * from users")
    List<User> getUsers();

}
