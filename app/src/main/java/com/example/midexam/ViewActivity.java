package com.example.midexam;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        ArrayList<String> studentList = new ArrayList<>();

        Cursor cursor = dbHelper.getAllStudents();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            int enrolled = cursor.getInt(2);

            String status;
            if (enrolled == 1) {
                status = "Enrolled";
            } else {
                status = "Not Enrolled";
            }

            studentList.add(id + " - " + name + " (" + status + ")");
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);
    }
}