package com.example.blabla.repo;

import com.example.blabla.api.BlaBlaApi;
import com.example.blabla.api.KeycloackApi;
import com.example.blabla.room.dao.UserDao;
import com.example.blabla.room.database.Db;

import java.util.concurrent.Executor;

public class KeyCloackRepo {
    private KeycloackApi remoteDataSource;
    private static final String TAG = "Keycloack Repo";
    private Executor executor;

    public KeyCloackRepo(KeycloackApi remoteDataSource, Executor executor) {
        this.remoteDataSource = remoteDataSource;
        this.executor = executor;
    }
}
