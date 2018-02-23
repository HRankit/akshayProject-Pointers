package com.example.pointers;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView roll, name;
    DatabaseHelper myDb;
    RadioGroup rg1, rg2, rg3, rg4, rg5;
    EditText edit_roll, edit_name;

    List<Integer> selectedradioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        selectedradioButtons = new ArrayList<Integer>();
        initializeViews();
    }

    private void initializeViews() {
        rg1 = findViewById(R.id.g1);
        rg2 = findViewById(R.id.g2);
        rg3 = findViewById(R.id.g3);
        rg4 = findViewById(R.id.g4);
        rg5 = findViewById(R.id.g5);

        edit_roll = findViewById(R.id.edit_roll);
        edit_name = findViewById(R.id.edit_name);
    }

    public void insert() {
        String se = "", cn = "", daa = "", dp = "", map = "";

        List<String> marks = process();

        String roll = edit_roll.getText().toString();
        String name = edit_name.getText().toString();

        Boolean result = myDb.insertData(roll, name, marks.get(0), marks.get(1), marks.get(2), marks.get(3), marks.get(4));
        Log.d("MainAct", roll + " " + name + " " + se + " " + cn + " " + daa + " " + dp + " " + map);

        if (result) {
            Toast.makeText(this, "Inserted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NOT Inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void read() {
        Cursor c = myDb.getAllData();
        StringBuffer s = new StringBuffer();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                s.append("ID: " + c.getInt(0) + "\n");
                s.append("ROLL: " + c.getString(1) + "\n");
                s.append("NAME: " + c.getString(2) + "\n");
                s.append("SE: " + c.getString(3) + "\n");
                s.append("CN: " + c.getString(4) + "\n");
                s.append("DAA: " + c.getString(5) + "\n");
                s.append("DP: " + c.getString(6) + "\n");
                s.append("MAP: " + c.getString(7) + "\n");
            }
            Toast.makeText(this, s.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void delete() {
        String roll = edit_roll.getText().toString();
        int i = myDb.deleteData(roll);
        Toast.makeText(this, "Rows affected: " + i, Toast.LENGTH_LONG).show();
    }


    public void update() {
        String roll = edit_roll.getText().toString();
        String name = edit_name.getText().toString();

        String se = "", cn = "", daa = "", dp = "", map = "";

        List<String> marks = process();

        boolean result = myDb.updateData(roll, name, marks.get(0), marks.get(1), marks.get(2), marks.get(3), marks.get(4));
        Log.d("MainAct", roll + " " + name + " " + se + " " + cn + " " + daa + " " + dp + " " + map);

        if (result) {
            Toast.makeText(this, "Inserted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "NOT Inserted", Toast.LENGTH_LONG).show();
        }

    }

    private List<String> process() {
        List<String> marks = new ArrayList<String>();

        selectedradioButtons.add(rg1.getCheckedRadioButtonId());
        selectedradioButtons.add(rg2.getCheckedRadioButtonId());
        selectedradioButtons.add(rg3.getCheckedRadioButtonId());
        selectedradioButtons.add(rg4.getCheckedRadioButtonId());
        selectedradioButtons.add(rg5.getCheckedRadioButtonId());

        for (int i = 0; i < selectedradioButtons.size(); i++) {
            int radiobutton = selectedradioButtons.get(i);
            if (radiobutton != -1) {
                RadioButton rb = findViewById(radiobutton);
                rb.getText();
                marks.add(i, rb.getText().toString());
            } else {
                marks.add(i, "");
            }
        }

        Log.d("MainAct", "arr: " + marks.toString());
        return marks;
    }


    @Override
    protected void onDestroy() {
        if (myDb != null) {
            myDb.close();
            myDb = null;
        }
        super.onDestroy();
    }

    public void insertClick(View v) {
        insert();
    }

    public void readClick(View v) {
        read();
    }

    public void updateClick(View v) {
        update();
    }

    public void delClick(View v) {
        delete();
    }


}
