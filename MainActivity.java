package com.example.reminderappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // Set up window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        FloatingActionButton addTaskFab = findViewById(R.id.addTaskFab);
        addTaskFab.setOnClickListener(v -> {
            // Create an explicit intent to open AddTaskActivity
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerView layout manager (vertical list in this case)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the task list
        taskList = new ArrayList<>();
        taskList.add(new Task("Task 1", "Description of Task 1"));
        taskList.add(new Task("Task 2", "Description of Task 2"));
        taskList.add(new Task("Task 3", "Description of Task 3"));

        // Initialize the adapter and set it to RecyclerView
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
    }
}
