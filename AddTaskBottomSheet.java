package com.example.reminderappproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTaskBottomSheet extends BottomSheetDialogFragment {

    private EditText taskNameEditText, taskDateEditText, taskTimeEditText, taskDescriptionEditText;
    private Button saveButton;
    private TaskAddListener taskAddListener;

    // Define a listener to communicate with MainActivity
    public interface TaskAddListener {
        void onTaskAdded(String taskName, String taskDescription, String taskDate, String taskTime);
    }

    // Set the listener from MainActivity or calling activity
    public void setTaskAddListener(TaskAddListener taskAddListener) {
        this.taskAddListener = taskAddListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this bottom sheet
        View rootView = inflater.inflate(R.layout.item_task, container, false);

        // Initialize the views
        taskNameEditText = rootView.findViewById(R.id.editTextTitle);
        taskDateEditText = rootView.findViewById(R.id.editTextDate2);
        taskTimeEditText = rootView.findViewById(R.id.editTextTime);
        taskDescriptionEditText = rootView.findViewById(R.id.editTextText);
        saveButton = rootView.findViewById(R.id.saveButton);

        // Set the click listener for the save button
        saveButton.setOnClickListener(v -> saveTask());

        return rootView;
    }

    private void saveTask() {
        // Retrieve data from EditText fields
        String taskName = taskNameEditText.getText().toString().trim();
        String taskDate = taskDateEditText.getText().toString().trim();
        String taskTime = taskTimeEditText.getText().toString().trim();
        String taskDescription = taskDescriptionEditText.getText().toString().trim();

        // Validate input
        if (!validateInput(taskName, taskDate, taskTime, taskDescription)) {
            return;
        }

        // Notify the listener (MainActivity) with the task data
        if (taskAddListener != null) {
            taskAddListener.onTaskAdded(taskName, taskDescription, taskDate, taskTime);
        }

        // Dismiss the bottom sheet after task is added
        dismiss();
    }

    private boolean validateInput(String taskName, String taskDate, String taskTime, String taskDescription) {
        boolean isValid = true;

        if (taskName.isEmpty()) {
            taskNameEditText.setError("Please enter a task name.");
            isValid = false;
        }

        if (taskDate.isEmpty()) {
            taskDateEditText.setError("Please enter a date.");
            isValid = false;
        }

        if (taskTime.isEmpty()) {
            taskTimeEditText.setError("Please enter a time.");
            isValid = false;
        }

        if (taskDescription.isEmpty()) {
            taskDescriptionEditText.setError("Please provide a task description.");
            isValid = false;
        }

        return isValid;
    }
}
