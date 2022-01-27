package com.example.clientlist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public static final Object LOCK = new Object();
    public static final String DATABASENAME = "client_database";
    private static DataBase instance;


    public static DataBase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        DataBase.class, DataBase.DATABASENAME).build();
            }
        }
        return instance;
    }
    public abstract Dao clientDao();


}
