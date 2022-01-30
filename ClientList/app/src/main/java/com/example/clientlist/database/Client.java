package com.example.clientlist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "client")
public class Client {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "company")
    private String company;
    @ColumnInfo (name = "first_name")
    private String firstName;
    @ColumnInfo (name = "last_name")
    private String lastName;
    @ColumnInfo (name = "telepfone")
    private String telepfone;
    @ColumnInfo (name = "type")
    private int type;
    @ColumnInfo (name = "info")
    private String description;
    @ColumnInfo (name = "vip")
    private int vip;

    public Client(int id, String company, String firstName, String lastName, String telepfone, int type, String description, int vip) {
        this.id = id;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telepfone = telepfone;
        this.type = type;
        this.description = description;
        this.vip = vip;
    }

    @Ignore
    public Client(String company, String firstName, String lastName, String telepfone, int type, String description, int vip) {
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telepfone = telepfone;
        this.type = type;
        this.description = description;
        this.vip = vip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelepfone() {
        return telepfone;
    }

    public void setTelepfone(String telepfone) {
        this.telepfone = telepfone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
