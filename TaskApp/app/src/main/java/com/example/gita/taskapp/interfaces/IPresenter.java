package com.example.gita.taskapp.interfaces;

import java.util.List;

import com.example.gita.taskapp.models.Task;


public interface IPresenter {
    void finish();

    List<Task> getTasks();

    void updateTask(Task task);

    void addTask(Task task);

    void removeTask(Task task);
}
