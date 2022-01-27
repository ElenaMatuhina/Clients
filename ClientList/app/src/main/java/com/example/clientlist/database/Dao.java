package com.example.clientlist.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@androidx.room.Dao
public interface
Dao {
    @Query("Select * from client")
    List<Client> getClientList();
    @Query("Select * from client where vip is 1")
    List<Client> getClientListVip();
    @Query("Select * from client where type is:importance")
    List<Client> getClientImpotance(int importance);
    @Query("Select * from client where first_name Like '%' || :name || '%'")
    List<Client> getClientName(String name);
    @Insert
    void insertClient(Client client);
    @Update
    void updateClient(Client client);
    @Delete
    void deleteClient(Client client);
}
