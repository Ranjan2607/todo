package com.example.rajivranjan.to_do;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Hashtable;


public class Details implements Serializable ,Comparable<Details>{



    private String key_id = "";
    private String title = "";
    private String description = "";
    private String date="";
    private String status="";

    public Details() {
        super();
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    @Override
    public int compareTo(@NonNull Details details) {
        return getDate().compareTo(details.getDate());
    }
}


