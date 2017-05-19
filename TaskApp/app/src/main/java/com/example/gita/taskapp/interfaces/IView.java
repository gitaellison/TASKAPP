package com.example.gita.taskapp.interfaces;

import java.util.List;

import com.example.gita.taskapp.models.Task;



public interface IView {
    void refreshTasks(List<Task> all);
}
