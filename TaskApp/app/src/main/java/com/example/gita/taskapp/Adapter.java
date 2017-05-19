package com.example.gita.taskapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gita.taskapp.interfaces.IPresenter;
import com.example.gita.taskapp.models.Task;

import java.io.InputStream;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.DemoViewHolder> {

    private List<Task> tasks;
    private IPresenter presenter;

    public Adapter(IPresenter presenter) {
        this.presenter = presenter;
        // fetch the list once, this way we aren't always querying the database for the list
        this.tasks = presenter.getTasks();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void delete(int adapterPosition) {
        Task task = tasks.get(adapterPosition);
        presenter.updateTask(task);
    }

    public void move(int adapterPosition, int targetAdapterPosition) {
        Task task = tasks.remove(adapterPosition);
        tasks.add(targetAdapterPosition, task);
        notifyItemMoved(adapterPosition, targetAdapterPosition);
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView contentTv;
        //ImageView imageV;
        Task task;

        public DemoViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView)itemView.findViewById(R.id.titleTv);
            contentTv = (TextView)itemView.findViewById(R.id.contentTv);

            // when you click on this item, update the Person object associated with it.
            titleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.updateTask(task); //add dialog box to update
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    presenter.removeTask(task);
                    return false;
                }
            });


            new DownloadImageTask((ImageView)itemView.findViewById(R.id.imageView))
                    .execute("https://api.adorable.io/avatars/126/happy@adorable.io.png");



        }

        public void bind(Task task) {
            this.task = task;
            titleTv.setText(task.Title);
            contentTv.setText(task.Content);
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        public ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }


    }



}
