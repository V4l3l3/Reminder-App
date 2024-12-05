package com.example.reminderappproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskDeleteListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private HashMap<String, List<Task>> tasksByDate = new HashMap<>();
    private String selectedDate; // Tracks the currently selected date
    private static final int REQUEST_CODE_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        setContentView(R.layout.activity_main);

        // Set up window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // FloatingActionButton to add a new task
        FloatingActionButton addTaskFab = findViewById(R.id.addTaskFab);
        addTaskFab.setOnClickListener(v -> {
            // Start AddTaskActivity and expect a result
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical layout

        // Initialize the task list and add sample tasks
        taskList = new ArrayList<>();
        loadSampleTasks(); // Load initial sample tasks

        // Initialize the adapter and set it to RecyclerView
        taskAdapter = new TaskAdapter(taskList, this); // Pass the delete listener
        recyclerView.setAdapter(taskAdapter);

        // Set up CalendarView and date selection listener
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth); // Format selected date

            // Filter tasks for the selected date
            List<Task> tasksForSelectedDate = tasksByDate.getOrDefault(selectedDate, new ArrayList<>());
            taskAdapter.updateTasks(tasksForSelectedDate); // Update RecyclerView with tasks
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK && data != null) {
            // Retrieve the task data from the Intent
            String taskName = data.getStringExtra("task_name");
            String taskDescription = data.getStringExtra("task_description");
            String taskDate = data.getStringExtra("task_date");
            String taskTime = data.getStringExtra("task_time");

            // Create a new Task object
            Task newTask = new Task(taskName, taskDescription, taskDate, taskTime);

            // Add the task to the taskList
            taskList.add(newTask);

            // Populate tasksByDate when adding tasks
            if (!tasksByDate.containsKey(taskDate)) {
                tasksByDate.put(taskDate, new ArrayList<>());
            }
            tasksByDate.get(taskDate).add(newTask);

            // Notify the adapter to update the RecyclerView
            taskAdapter.notifyDataSetChanged();
        }
    }

    private void loadSampleTasks() {
        taskList.add(new Task("Meeting", "Discuss project status", "2024-12-10", "10:00 AM"));
        taskList.add(new Task("Grocery Shopping", "Buy vegetables and fruits", "2024-12-11", "5:00 PM"));

        // Populate tasksByDate
        for (Task task : taskList) {
            String date = task.getTaskDate();
            if (!tasksByDate.containsKey(date)) {
                tasksByDate.put(date, new ArrayList<>());
            }
            tasksByDate.get(date).add(task);
        }
    }

    @Override
    public void onTaskDeleted(Task task) {
        // Update the tasksByDate map
        List<Task> dateTasks = tasksByDate.get(task.getTaskDate());
        if (dateTasks != null) {
            dateTasks.remove(task);
            if (dateTasks.isEmpty()) {
                tasksByDate.remove(task.getTaskDate());
            }
        }

        // Update RecyclerView if the deleted task is part of the currently selected date
        if (task.getTaskDate().equals(selectedDate)) {
            taskAdapter.updateTasks(tasksByDate.getOrDefault(selectedDate, new ArrayList<>()));
        }
    }
}
