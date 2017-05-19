package com.example.gita.taskapp;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gita.taskapp.interfaces.IView;
import com.example.gita.taskapp.models.Task;
import com.example.gita.taskapp.presenters.Presenter;

import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    Presenter presenter;
    Adapter adapter;
    EditText ettitle, etcontents;


    final int DIALOG_ADDTASK = 0;
    // widgets
    Button addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTaskBtn = (Button)findViewById(R.id.add_task_button);
        addTaskBtn.setOnClickListener(this);

        // presenter and adapter.
        presenter = new Presenter(this, new TaskDB(this));
        adapter = new Adapter(presenter);

        //async stuff

        // the recyclerview
        RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.finish();
    }

    @Override
    public void refreshTasks(List<Task> all) {
        if(all != null) {
            // replace the existing list
            adapter.setTasks(all);
        } else {
            // just tell the adapter that something has changed
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_task_button:
                showDialog(DIALOG_ADDTASK);
                break;
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog dialogDetails = null;

        switch (id) {
            case DIALOG_ADDTASK:
                LayoutInflater inflater = LayoutInflater.from(this);
                View dialogview = inflater.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
                dialogbuilder.setTitle("Add Task");
                dialogbuilder.setView(dialogview);
                dialogDetails = dialogbuilder.create();
                break;
        }
        return dialogDetails;
    }


    @Override
    protected void onPrepareDialog(int id, final Dialog dialog) {
        switch (id) {
            case DIALOG_ADDTASK:
                final AlertDialog alertDialog = (AlertDialog) dialog;
                Button savebutton = (Button) alertDialog
                        .findViewById(R.id.savebutton);
                ettitle = (EditText) alertDialog
                        .findViewById(R.id.etTitle);
                etcontents = (EditText) alertDialog
                        .findViewById(R.id.etContent);
                savebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = ettitle.getText().toString();
                        String content = etcontents.getText().toString();
                        presenter.addTask(new Task(title, content));
                        dialog.dismiss();
                    };
                });
        }


        }








}
