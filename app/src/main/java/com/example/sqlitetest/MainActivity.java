package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        addMountain("Mount Everest", 420);
        printMountain();
    }

    private void printMountain(){
        Cursor cursor = database.query(DatabaseTables.Mountain.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Mountain mountain = new Mountain(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT))
            );
            Log.d("MainActivity", mountain.getName());
        }
        cursor.close();
    }

    private void addMountain(String name, int height){
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Mountain.COLUMN_NAME_NAME, name);
        values.put(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT, height);
        database.insert(DatabaseTables.Mountain.TABLE_NAME, null, values);
    }
}
