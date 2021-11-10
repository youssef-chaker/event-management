package com.example.blabla.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.blabla.model.Event;
import com.example.blabla.model.LocationDto;
import com.example.blabla.model.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import com.example.blabla.model.Token;
import com.example.blabla.repo.Repository;
import com.example.blabla.room.entity.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class MainViewModel extends ViewModel {
    public MutableLiveData<LatLng> location= new MutableLiveData<>();
    private SavedStateHandle savedStateHandle;
    private final Repository repo;
    private static final String TAG = "MainViewModel";
    public MutableLiveData<Message> message = new MutableLiveData<>() ;
    public MutableLiveData<Token> token ;
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<Bitmap> avatar = new MutableLiveData<>();
    public List<Event> events;
    public MutableLiveData<List<Event>> eventsLiveData;

    @Inject
    public MainViewModel(SavedStateHandle savedStateHandle,Repository repo) {
        this.savedStateHandle = savedStateHandle;
        this.repo = repo;
//        token = savedStateHandle.getLiveData("token",new Token());
        email = savedStateHandle.getLiveData("email");
        password = savedStateHandle.getLiveData("password");
        eventsLiveData = savedStateHandle.getLiveData("events");
        if(eventsLiveData.getValue() == null) eventsLiveData.setValue(new ArrayList<>());
        User user = null;
        try {
            user = repo.getUserLocal();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if(user != null) {
            token = new MutableLiveData<>(new Token(user.getToken(),user.getUsername(),user.getEmail()));
            if(user.getAvatar() != null) avatar.setValue(user.getAvatar());
        } else {
            token = new MutableLiveData<>(new Token());
        }
    }

    public void login(String email , String password) {
        Log.i(TAG, "login: aaaaaaaaaaaaaaaaaaa");
        repo.login(email,password,new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token.postValue(new Token(response.body().getToken(),response.body().getUsername()));
                    message.postValue(new Message(true,"successfully logged in"));
                    repo.insertUserLocal(new User(response.body().getToken(),response.body().getUsername(),response.body().getEmail()) );
                }else {
                    message.postValue(new Message(false,response.message()));
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                message.postValue(new Message(false,"unable to connect"));
            }
        });
    }

    public void signUp(String username,String email , String password) {
        Log.i(TAG, "signup: aaaaaaaaaaaaaaaaaaa");
        repo.signup(username,email,password,new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token.postValue(new Token(response.body().getToken(),response.body().getUsername()));
                    message.postValue(new Message(true,"successfully logged in"));
                    repo.insertUserLocal(new User(response.body().getToken(),response.body().getUsername(),response.body().getEmail()) );
                }else {
                    message.postValue(new Message(false,response.message()));
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                message.postValue(new Message(false,"unable to connect"));
            }
        });
    }

    public void getAvatar()  {
        Log.i(TAG, "getAvatar: inside get avatar");
        repo.getAvatar(token.getValue().getToken(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "onResponse: got avatar");
                    try{
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
//                                .decodeByteArray(response.body().bytes(),0,response.body().bytes().length);
                        avatar.postValue(bm);
                        repo.addAvatarLocal(token.getValue().getToken(),bm);
                    }catch (Exception e){
                        Log.i(TAG, "onResponse: ERROR");
                        Log.e(TAG, "onResponse: ",e );
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                message.postValue(new Message(false,"unable to connect"));
            }
        });
    }

    public void getEventsNearMe(double longtitude,double latitude) {
        Log.i(TAG, "getEventsNearMe: ");
        Log.i(TAG, "getEventsNearMe: "+token.getValue().getToken());
        repo.getEventsNearMe(token.getValue().getToken(),longtitude,latitude,new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()) {
                    message.postValue(new Message(true,"fetched events"));
                    eventsLiveData.postValue(response.body());
                }else {
                    message.postValue(new Message(false,"could not fetch events"));
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                message.postValue(new Message(false,"unable to connect"));
            }
        });
    }

    public void getEvents() {
        Log.i(TAG, "getEvents: ");
        repo.getEvents(
                new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()) {
                    message.postValue(new Message(true,"fetched events"));
                    eventsLiveData.postValue(response.body());
                }else {
                    message.postValue(new Message(false,"could not fetch events"));
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                message.postValue(new Message(false,"unable to connect"));
            }
        });
    }

    public void subscribeToEvent(String eventId,Callback<Void> callback) {
        Log.i(TAG, "subscribeToEvent: ");
        repo.subscribeToEvent(token.getValue().getToken(), eventId,callback);
    }

//    public void updateLocationThenGetEvents(double longtitude,double latitude) {
//        Log.i(TAG, "updateLocationThenGetEvents: ");
//        repo.updateLocationThenGetEvents(token.getValue().getToken(),new LocationDto("Point",new double[]{longtitude,latitude}),new Callback<List<Event>>() {
//            @Override
//            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
//                if(response.isSuccessful()) {
//                    message.postValue(new Message(true,"fetched events"));
//                    eventsLiveData.postValue(response.body());
//                }else {
//                    message.postValue(new Message(false,"could not fetch events"));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Event>> call, Throwable t) {
//                message.postValue(new Message(false,"unable to connect"));
//            }
//        });
//    }

    public void logout() {
        repo.clearAllUsersLocal();
    }

    public void updateUserLocation(double longtitude,double latitude) {
        Log.i(TAG, "updateUserLocation: ");
        repo.updateUserLocation(token.getValue().getToken(),new LocationDto("Point",new double[]{longtitude,latitude}));
    }

    public void postEvent(Event event,Callback<Event> callback) {
        repo.postEvent(token.getValue().getToken(), event, callback);
    }


    @Override
    protected void onCleared() {
        Log.i(TAG, "onCleared: MainViewModel destroyed");
        super.onCleared();
    }
}
