package com.example.blabla.repo;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.blabla.api.BlaBlaApi;
import com.example.blabla.model.Event;
import com.example.blabla.model.LocationDto;
import com.example.blabla.model.LoginDto;
import com.example.blabla.model.SignupDto;
import com.example.blabla.model.Token;
import com.example.blabla.room.dao.UserDao;
import com.example.blabla.room.database.Db;
import com.example.blabla.room.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    private BlaBlaApi remoteDataSource;
    private Db localDataSource;
    private static final String TAG = "Repository";
    private Executor executor;
    private UserDao userDao;

    public Repository(BlaBlaApi remoteDataSource, Db localDataSource, Executor executor) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.executor = executor;
        userDao = localDataSource.userDao();
    }

    public void login(String email , String password, Callback<Token> callback) {
        Call<Token> call = remoteDataSource.login(new LoginDto(email,password));
        call.enqueue(callback);
    }

    public void signup(String username , String email , String password, Callback<Token> callback) {
        Call<Token> call = remoteDataSource.signup(new SignupDto(username,email,password));
        call.enqueue(callback);
    }

    public void getAvatar(String token,Callback<ResponseBody> callback) {
        Call<ResponseBody> call = remoteDataSource.getAvatar("Bearer "+token);
        call.enqueue(callback);
    }

    public void insertUserLocal(User user) {
        executor.execute(() -> {
            userDao.insertUser(user);
        });
    }

    public void addAvatarLocal(String token, Bitmap avatar) {
        executor.execute(() -> {
            userDao.addAvatar(token,avatar);
        });
    }

    public User getUserLocal() throws ExecutionException, InterruptedException {
        CompletableFuture<User> c = CompletableFuture.supplyAsync(() -> {
            List<User> users = userDao.getUsers();
            if(users == null || users.size() < 1) return null;
            return users.get(0);
        },executor);
        return c.get();
    }

    public void getEventsNearMe(String token,double longtitude,double latitude, Callback<List<Event>> callback) {
        Call<List<Event>> call = remoteDataSource.getEventsNearMe(token,longtitude,latitude);
        call.enqueue(callback);
    }

    public void getEvents(Callback<List<Event>> callback) {
        Call<List<Event>> call = remoteDataSource.getEvents();
        call.enqueue(callback);
    }

    public void updateUserLocation(String token,LocationDto locationDto) {
        Log.i(TAG, "updateUserLocation: ");
        executor.execute(() -> {
            Call<User> call = remoteDataSource.updateLocation(token,locationDto);
            try {
                call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
//
//    public void updateLocationThenGetEvents(String token,LocationDto locationDto,Callback<List<Event>> callback) {
//        Log.i(TAG, "updateLocationThenGetEvents: ");
//        CompletableFuture.runAsync(() -> {
//            Call<User> call = remoteDataSource.updateLocation(token,locationDto);
//            try {
//                call.execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        },executor).thenRunAsync(() -> {
//            Call<List<Event>> call = remoteDataSource.getEventsNearMe(token);
//            call.enqueue(callback);
//        },executor);
//    }

    public void clearAllUsersLocal() {
        executor.execute(() -> userDao.clearAllUsers());
    }

    public void postEvent(Event event,Callback<Event> callback) {
        Log.i(TAG, "postEvent: ");
        executor.execute(() -> {
            Call<Event> call = remoteDataSource.postEvent(event);
            call.enqueue(callback);
        });
    }

    public void getEventById(String id,Callback<Event> callback) {
        Log.i(TAG, "getEventById: ");
        executor.execute(() -> {
            Call<Event> call = remoteDataSource.getEventById(id);
            call.enqueue(callback);
        });
    }

    public void subscribeToEvent(String token,String eventId,Callback<Void> callback) {
        Log.i(TAG, "subscribeToEvent: ");
        executor.execute(() -> {
            Call<Void> call = remoteDataSource.subscribeToEvent(token,eventId);
            call.enqueue(callback);
        });
    }

}
