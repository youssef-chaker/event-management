package com.example.blabla.app;

import android.content.Context;

import com.example.blabla.api.BlaBlaApi;
import com.example.blabla.repo.Repository;
import com.example.blabla.room.database.Db;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ViewModelComponent.class)
@Module
public class MainModule {
    @ViewModelScoped
    @Provides
    public static Repository provideRepo(@ApplicationContext Context context, ExecutorService service) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().dispatcher(new Dispatcher(Executors.newCachedThreadPool()))
//                .addInterceptor(chain -> {
//                    Request request = chain.request();
//                    Request newRequest = request.newBuilder().header("Authorization","Bearer ").build();
//                    return chain.proceed(newRequest);
//                }).build();
//                .addInterceptor(loggingInterceptor).build();
//
        OkHttpClient okHttpClient = new OkHttpClient.Builder().dispatcher(new Dispatcher(service)).addInterceptor(loggingInterceptor).build();


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http:192.168.1.52:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        BlaBlaApi api = retrofit2.create(BlaBlaApi.class);

        Db db = Db.getDatabase(context);

        return new Repository(api,db,service);
    }

    @ViewModelScoped
    @Provides
    public static ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

}
