package com.example.midexam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText etName, etId;
    CheckBox cbEnrolled;
    Button btnSave;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);
        cbEnrolled = findViewById(R.id.cbEnrolled);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String id = etId.getText().toString().trim();
                boolean enrolled = cbEnrolled.isChecked();

                if (name.isEmpty() || id.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill name and ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.addStudent(id, name, enrolled);
                if (success) {
                    Toast.makeText(AddActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etId.setText("");
                    cbEnrolled.setChecked(false);
                } else {
                    Toast.makeText(AddActivity.this, "Student ID already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}