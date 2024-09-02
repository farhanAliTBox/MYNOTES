//package com.example.mynotes;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class AddNoteActivity extends AppCompatActivity {
//
//    private EditText noteTitleInput, noteDescriptionInput, noteDateInput;
//    private Button saveNoteButton, deleteNoteButton;
//    private DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_note);
//
//        databaseHelper = new DatabaseHelper(this);
//
//        noteTitleInput = findViewById(R.id.note_title_input);
//        noteDescriptionInput = findViewById(R.id.note_description_input);
//        noteDateInput = findViewById(R.id.note_date_input);
//        saveNoteButton = findViewById(R.id.save_note_button);
//        deleteNoteButton = findViewById(R.id.delete_note_button);
//
//        saveNoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveNote();
//            }
//        });
//
//        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); // Close the activity
//            }
//        });
//    }
//
//    private void saveNote() {
//        String title = noteTitleInput.getText().toString().trim();
//        String description = noteDescriptionInput.getText().toString().trim();
//        String date = noteDateInput.getText().toString().trim();
//
//        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(date)) {
//            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Note note = new Note(title, description, date);
//        long id = databaseHelper.addNote(note);
//
//        if (id > 0) {
//            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
//            finish(); // Close activity and return to MainActivity
//        } else {
//            Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Matching the IDs with the layout
        titleEditText = findViewById(R.id.note_title_input);
        descriptionEditText = findViewById(R.id.note_description_input);
        Button doneButton = findViewById(R.id.save_note_button);
        Button cancelButton = findViewById(R.id.delete_note_button);
        ImageButton backButton = findViewById(R.id.addNoteBack);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set onClickListeners for buttons
        doneButton.setOnClickListener(v -> saveNote());
        cancelButton.setOnClickListener(v -> finish());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Create a new note object
        Note note = new Note(title, description, currentDate);

        // Add the note to the database
        databaseHelper.addNote(note);

        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        finish();
    }
}

