package com.example.blabla.api;

import com.example.blabla.model.Event;
import com.example.blabla.model.LocationDto;
import com.example.blabla.model.LoginDto;
import com.example.blabla.model.SignupDto;
import com.example.blabla.model.Token;
import com.example.blabla.room.entity.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlaBlaApi {
    @GET("/api/users/me")
    Call<User> getUserInfo();

    @GET("/api/users/me/avatar")
    Call<ResponseBody> getAvatar(@Header("Authorization") String token);

    @POST("/api/users/login")
    Call<Token> login(@Body LoginDto loginDto);

    @POST("/api/users")
    Call<Token> signup(@Body SignupDto signupDto);

    @PATCH("/api/users/me")
    Call<User> updateLocation(@Header("Authorization") String token,@Body LocationDto locationDto);

    @PATCH("users/me")
    Call<User> patchUser(@Body User user);

    @GET("/events/nearme")
    Call<List<Event>> getEventsNearMe(@Header("Authorization") String token, @Query("longtitude") double longtitude,@Query("latitude") double latitude);

    @GET("/api/events")
    Call<List<Event>> getEvents();

    @POST("/api/events")
    Call<Event> postEvent(@Body Event event);

    @GET("/api/events/{id}")
    Call<Event> getEventById(@Path("id") String id);

    @POST("/api/events/subscribe/{id}")
    Call<Void> subscribeToEvent(@Header("Authorization") String token,@Path("id")String id);
}
