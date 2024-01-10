package com.example.cgpa1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Calculate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        // Retrieve the SGPA value from the intent
        double sgpa = getIntent().getDoubleExtra("SGPA", 0.0);

        // Display the SGPA in a TextView
        TextView sgpaTextView = findViewById(R.id.SGPA);
        sgpaTextView.setText("SGPA: " + sgpa);
    }
}