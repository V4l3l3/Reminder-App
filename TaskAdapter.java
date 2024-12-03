package com.example.reminderappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private TaskDeleteListener deleteListener;

    // Constructor
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
        this.deleteListener = deleteListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout (task_item.xml should contain CheckBox, TextViews, etc.)
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Set the task data to the views
        holder.taskNameTextView.setText(task.getTaskName());
        holder.taskDescriptionTextView.setText(task.getTaskDescription());

        // Set listener for the checkbox (delete task if checked)
        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deleteListener.onTaskDelete(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder to hold the item views
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskNameTextView;
        public TextView taskDescriptionTextView;
        public CheckBox taskCheckbox;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskName);
            taskDescriptionTextView = itemView.findViewById(R.id.taskDescription);
            taskCheckbox = itemView.findViewById(R.id.taskCheckbox);
        }
    }

    // Interface to handle task deletion
    public interface TaskDeleteListener {
        void onTaskDelete(Task task);
    }

    // Method to update the task list and refresh RecyclerView
    public void updateTasks(List<Task> tasksForSelectedDate) {
        taskList.clear();
        taskList.addAll(tasksForSelectedDate);
        notifyDataSetChanged();
    }
}
