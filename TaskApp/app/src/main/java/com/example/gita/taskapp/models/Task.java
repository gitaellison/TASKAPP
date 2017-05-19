package com.example.gita.taskapp.models;

import java.util.UUID;


public class Task {
    public String Title;
    public String Content;
    public UUID id;

    public Task(){
        Title = null;
        Content = null;
    }

    public Task(String title, String content){
        Title = title;
        Content = content;
    }

}
