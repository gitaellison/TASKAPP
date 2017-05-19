package com.example.gita.taskapp.mock;

import com.example.gita.taskapp.interfaces.ITaskService;

import java.util.List;

/**
 * Created by Gita on 2/13/2017.
 */

public class MockTaskService implements ITaskService {

    public List<Task> tasks;

    public MockTaskService(List<Task> tasks){
        this.tasks = tasks;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public void close() {

    }
}
