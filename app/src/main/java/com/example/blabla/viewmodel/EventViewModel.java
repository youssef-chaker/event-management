package com.example.blabla.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.blabla.model.Event;
import com.example.blabla.model.Location;
import com.example.blabla.model.User;
import com.example.blabla.repo.Repository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class EventViewModel extends ViewModel {

    private SavedStateHandle savedStateHandle;
    public MutableLiveData<String> title;
    public MutableLiveData<String> description;
    public MutableLiveData<String> address;
    public MutableLiveData<Double> longtitude;
    public MutableLiveData<Double> latitude;
    private static final String TAG = "EventViewModel";

    private Repository repo;

    @Inject
    public EventViewModel(SavedStateHandle savedStateHandle,Repository repo) {
        this.savedStateHandle = savedStateHandle;
        title = savedStateHandle.getLiveData("title");
        description = savedStateHandle.getLiveData("description");
        address = savedStateHandle.getLiveData("address");
        longtitude = savedStateHandle.getLiveData("longtitude");
        latitude = savedStateHandle.getLiveData("latitude");
        this.repo = repo;
    }

    public void getEventById(String id) {
        repo.getEventById(id, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.isSuccessful()) {
                    title.postValue(response.body().getTitle());
                    description.postValue(response.body().getDescription());
                    address.postValue(response.body().getAddress());
                } else {
                    Log.i(TAG, "onResponse: not successfull");
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }
}
