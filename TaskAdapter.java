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
    public TaskAdapter(List<Task> taskList, TaskDeleteListener deleteListener) {
        this.taskList = taskList;
        this.deleteListener = deleteListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Set task data to the views
        holder.taskNameTextView.setText(task.getTaskName());
        holder.taskDescriptionTextView.setText(task.getTaskDescription());

        // Update checkbox state
        holder.taskCheckbox.setChecked(task.isSelected());

        // Handle checkbox state change
        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Notify the listener about the task deletion
                if (deleteListener != null) {
                    deleteListener.onTaskDeleted(task);
                }
                // Remove the task locally and update the RecyclerView
                int adapterPosition = holder.getAdapterPosition();
                taskList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
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
        void onTaskDeleted(Task task);
    }

    // Method to update the task list and refresh RecyclerView
    public void updateTasks(List<Task> tasksForSelectedDate) {
        taskList.clear();
        taskList.addAll(tasksForSelectedDate);
        notifyDataSetChanged();
    }
}
