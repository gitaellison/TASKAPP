package com.example.gita.taskapp;

import com.example.gita.taskapp.interfaces.IPresenter;
import com.example.gita.taskapp.interfaces.ITaskService;
import com.example.gita.taskapp.interfaces.IView;
import com.example.gita.taskapp.mock.MockTaskService;
import com.example.gita.taskapp.mock.MockView;
import com.example.gita.taskapp.presenters.Presenter;

import org.junit.*;

import java.util.*;


/**
 * Created by Gita on 2/13/2017.
 */

public class UnitTests {
    IView view;
    ITaskService taskService;

    @Before
    public void setup() {
        // any setup code goes here.
    }

    private List<Task> makeTestTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setName("Test");
        tasks.add(task);
        task = new Task();
        task.setName("Test2");
        tasks.add(task);
        return tasks;
    }

    @Test
    public void Test_InitialTaskShouldBeFirstTask() {
        List<Task> tasks = makeTestTasks();

        // setup model
        taskService = new MockTaskService(tasks);
        view = new MockView();

        // create presenter
        IPresenter presenter = new Presenter(view, new TaskModel(taskService));

        // get first task
        Task expectedTask = presenter.getCurrentTask();

        // assert that it is the expected task
        Assert.assertEquals(tasks.get(0).getName(), expectedTask.getName());
        MockView mockView = (MockView)view;
        Assert.assertTrue(mockView.displayTaskWasCalled);
    }

    @Test
    public void Test_GetNextTaskReturnsNextTask() {
        List<Task> tasks = makeTestTasks();

        // setup model
        taskService = new MockTaskService(tasks);
        view = new MockView();

        // create presenter
        IPresenter presenter = new Presenter(view, new TaskModel(taskService));

        // move to next task
        presenter.moveToNextTask();

        // get first task
        Task expectedTask = presenter.getCurrentTask();

        // assert that it is the expected task
        Assert.assertEquals(tasks.get(1).getName(), expectedTask.getName());
        MockView mockView = (MockView)view;
        Assert.assertEquals(tasks.get(1), mockView.displayedTask);
    }

    @Test
    public void Test_GetPreviousTaskReturnsNextTask() {
        List<Task> tasks = makeTestTasks();

        taskService = new MockTaskService(tasks);
        view = new MockView();

        // create presenter
        IPresenter presenter = new Presenter(view, new TaskModel(taskService));

        // move to next task(twice)
        presenter.moveToNextTask();
        presenter.moveToNextTask();

        // now move to the previous task
        presenter.moveToPrevTask();

        // get first task
        Task expectedTask = presenter.getCurrentTask();

        // assert that it is the expected task
        Assert.assertEquals(tasks.get(1).getName(), expectedTask.getName());
    }




}
