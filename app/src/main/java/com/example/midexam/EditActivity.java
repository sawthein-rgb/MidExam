package com.example.midexam;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText etSearch, etName;
    TextView tvId;
    CheckBox cbEnrolled;
    Button btnSearch, btnSave;
    DatabaseHelper dbHelper;

    String foundId = ""; // the ID of the student we found

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch = findViewById(R.id.etSearch);
        etName = findViewById(R.id.etName);
        tvId = findViewById(R.id.tvId);
        cbEnrolled = findViewById(R.id.cbEnrolled);
        btnSearch = findViewById(R.id.btnSearch);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        // SEARCH button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etSearch.getText().toString().trim();

                if (key.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Enter a name or ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = dbHelper.findStudent(key);
                if (cursor.moveToFirst()) {
                    foundId = cursor.getString(0);
                    tvId.setText("Student ID: " + foundId);
                    etName.setText(cursor.getString(1));
                    cbEnrolled.setChecked(cursor.getInt(2) == 1);
                } else {
                    foundId = "";
                    Toast.makeText(EditActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });

        // SAVE button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foundId.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Search a student first", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = etName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.updateStudent(foundId, name, cbEnrolled.isChecked());
                if (success) {
                    Toast.makeText(EditActivity.this, "Student updated", Toast.LENGTH_SHORT).show();
                    finish(); // go back to the menu
                } else {
                    Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}