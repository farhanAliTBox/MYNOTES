package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText noteTitleEditText, noteDescriptionEditText, noteDateEditText;
    private Button editButton, deleteButton;
    private DatabaseHelper databaseHelper;
    private Note note;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        noteTitleEditText = findViewById(R.id.note_title_edit_text);
        noteDescriptionEditText = findViewById(R.id.note_description_edit_text);
        noteDateEditText = findViewById(R.id.note_date_edit_text);
//        editButton = findViewById(R.id.button_edit);
//        deleteButton = findViewById(R.id.button_delete);

        databaseHelper = new DatabaseHelper(this);

        int noteId = getIntent().getIntExtra("NOTE_ID", -1);
        if (noteId != -1) {
            note = databaseHelper.getNoteById(noteId);
            if (note != null) {
                displayNoteDetails(note);
            }
        }

        editButton.setOnClickListener(v -> {
            if (isEditing) {
                saveNote();
            } else {
                enableEditing(true);
                editButton.setText("Save");
            }
            isEditing = !isEditing;
        });

        deleteButton.setOnClickListener(v -> {
            deleteNote();
        });
    }

    private void displayNoteDetails(Note note) {
        noteTitleEditText.setText(note.getTitle());
        noteDescriptionEditText.setText(note.getDescription());
        noteDateEditText.setText(note.getDate());
        enableEditing(false);
    }

    private void enableEditing(boolean enable) {
        noteTitleEditText.setEnabled(enable);
        noteDescriptionEditText.setEnabled(enable);
        noteDateEditText.setEnabled(enable);
    }

    private void saveNote() {
        String title = noteTitleEditText.getText().toString().trim();
        String description = noteDescriptionEditText.getText().toString().trim();
        String date = noteDateEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        note.setTitle(title);
        note.setDescription(description);
        note.setDate(date);

        boolean success = databaseHelper.updateNote(note);
        if (success) {
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            enableEditing(false);
            editButton.setText("Edit");
        } else {
            Toast.makeText(this, "Error updating note", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        boolean success = databaseHelper.deleteNoteById(note.getId());
        if (success) {
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            finish(); // Go back to MainActivity after deletion
        } else {
            Toast.makeText(this, "Error deleting note", Toast.LENGTH_SHORT).show();
        }
    }
}
