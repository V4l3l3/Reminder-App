package com.example.reminderappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, taskDateEditText, taskTimeEditText, taskDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_task); // This points to item_task.xml

        // Initialize the views
        taskNameEditText = findViewById(R.id.editTextTitle);
        taskDateEditText = findViewById(R.id.editTextDate2);
        taskTimeEditText = findViewById(R.id.editTextTime);
        taskDescriptionEditText = findViewById(R.id.editTextText); // assuming you add a description field

        // Handle Save button
        findViewById(R.id.saveButton).setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        // Retrieve data from EditText fields
        String taskName = taskNameEditText.getText().toString();
        String taskDate = taskDateEditText.getText().toString();
        String taskTime = taskTimeEditText.getText().toString();
        String taskDescription = taskDescriptionEditText.getText().toString();

        // Create a Task object (assuming you have a Task class to hold task data)
        Task newTask = new Task(taskName, taskDate);

        // Optionally, send the data back to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("task_name", taskName);
        resultIntent.putExtra("task_date", taskDate);
        resultIntent.putExtra("task_time", taskTime);
        resultIntent.putExtra("task_description", taskDescription);

        setResult(RESULT_OK, resultIntent);
        finish(); // Close the activity and go back to MainActivity
    }
}
