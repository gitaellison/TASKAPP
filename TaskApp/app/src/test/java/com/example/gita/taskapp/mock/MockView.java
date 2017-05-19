package com.example.gita.taskapp.mock;
import com.example.gita.taskapp.interfaces.IView;

/**
 * Created by Gita on 2/13/2017.
 */

    public class MockView implements IView {

        public boolean displayTaskWasCalled;
        public boolean displayedNextTask;
        public Task displayedTask;

        @Override
        public void displayTask(Task task) {
            displayTaskWasCalled = true;
            displayedTask = task;
        }

        @Override
        public void displayNextTask(Task task) {
            displayedNextTask = true;
        }

    @Override
    public void showAddView() {

    }

    @Override
    public void showEditView() {

    }
}


