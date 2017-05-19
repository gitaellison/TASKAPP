package com.example.gita.taskapp.presenters;

import java.util.List;
import java.util.UUID;


import com.example.gita.taskapp.interfaces.IDataSource;
import com.example.gita.taskapp.interfaces.IPresenter;
import com.example.gita.taskapp.interfaces.IView;
import com.example.gita.taskapp.models.Task;

/**
 * Created by randy on 3/8/17.
 */

public class Presenter implements IPresenter {



    private final IView view;
    private IDataSource<Task> db;

    public Presenter(IView view, IDataSource<Task> dataSource) {
        this.db = dataSource;
        this.view = view;
    }

    public List<Task> getTasks() {
        return db.getAll();
    }

    @Override
    public void updateTask(Task task) {
        String title = task.Title;
        String content = task.Content;
//hmm
        db.update(task);
        view.refreshTasks(db.getAll());
    }



    @Override
    public void addTask(Task task) {

        task.id = UUID.randomUUID();
        db.add(task);
        view.refreshTasks(db.getAll());
    }

    @Override
    public void removeTask(Task task) {
        db.delete(task);
        view.refreshTasks(db.getAll());
    }

    @Override
    public void finish() {
        db.close();
    }


}
