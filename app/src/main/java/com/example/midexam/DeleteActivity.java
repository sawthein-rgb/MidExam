package com.example.midexam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteActivity extends AppCompatActivity {

    EditText etKey;
    Button btnDelete;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etKey = findViewById(R.id.etKey);
        btnDelete = findViewById(R.id.btnDelete);
        dbHelper = new DatabaseHelper(this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etKey.getText().toString().trim();

                if (key.isEmpty()) {
                    Toast.makeText(DeleteActivity.this, "Enter a name or ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.deleteStudent(key);
                if (success) {
                    Toast.makeText(DeleteActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                    etKey.setText("");
                } else {
                    Toast.makeText(DeleteActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}