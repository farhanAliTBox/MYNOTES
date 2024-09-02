package com.example.mynotes;

import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.app.DatePickerDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etDob;
    private ImageView ivCalendar;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDob = findViewById(R.id.et_dob);
        ivCalendar = findViewById(R.id.icalendar);
        signupButton = findViewById(R.id.btn_create);

        ivCalendar.setOnClickListener(view -> {
            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Date Picker Dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        // Formatting date and setting it in EditText
                        String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                        etDob.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        signupButton.setOnClickListener(view -> {
            // Start HomeActivity
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        });

        // Ensure the status bar color is set and icons are visible
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            // Set the status bar color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            // Set the status bar icons to dark
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
            // Light status bar icons only work on API 23 and above
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
