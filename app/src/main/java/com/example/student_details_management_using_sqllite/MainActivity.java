package com.example.student_details_management_using_sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    private Button btn_insert, btn_view, btn_update, btn_delete;
    private EditText et_credit, et_name, et_contact, et_dob;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fieldInitialization();

        this.btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String credits = et_credit.getText().toString();
                String name = et_name.getText().toString();
                String contact = et_contact.getText().toString();
                String dob = et_dob.getText().toString();
                boolean result = dbHelper.insertStudentData(calculateGPA(credits), name, contact, dob);
                if(result == true){
                    Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Inserted successfully", Toast.LENGTH_SHORT).show();
                }
                clearValues();
            }
        });

        this.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String contact = et_contact.getText().toString();
                String dob = et_dob.getText().toString();
                boolean result = dbHelper.updateStudentData(name, contact, dob);
                if(result){
                    Toast.makeText(MainActivity.this, "Updated uccessfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not updated successfully", Toast.LENGTH_SHORT).show();
                }
                clearValues();
            }
        });

        this.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                boolean result = dbHelper.deleteStudentData(name);
                if(result == true){
                    Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Deleted successfully", Toast.LENGTH_SHORT).show();
                }
                clearValues();
            }
        });

        this.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = dbHelper.readStudentData();
                int count = 0;
                if(result.getCount() > 0){
                    StringBuffer buffer = new StringBuffer();
                    while(result.moveToNext()){
                        buffer.append("Student "+ (count + 1) + " details\n");
                        buffer.append("---------------------------\n");
                        buffer.append("Name : " + result.getString(0) + "\n");
                        buffer.append("Contact: " + result.getString(1) + "\n");
                        buffer.append("GPA: " + result.getString(3) + "\n");
                        buffer.append("Dob: " + result.getString(2) + "\n\n");
                        count++;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Student entries..");
                    builder.setCancelable(true);
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
                else{
                    Toast.makeText(MainActivity.this, "No datas", Toast.LENGTH_SHORT).show();
                }
                clearValues();
            }
        });


    }

    private void fieldInitialization() {
        this.et_credit = findViewById(R.id.et_credit);
        this.et_name = findViewById(R.id.et_name);
        this.et_contact = findViewById(R.id.et_contact);
        this.et_dob = findViewById(R.id.et_dob);
        this.btn_insert = findViewById(R.id.btn_insert);
        this.btn_update = findViewById(R.id.btn_update);
        this.btn_delete = findViewById(R.id.btn_delete);
        this.btn_view = findViewById(R.id.btn_view);
        this.dbHelper = new DBHelper(this);
    }

    private void clearValues(){
        this.et_credit.setText("");
        this.et_name.setText("");
        this.et_contact.setText("");
        this.et_dob.setText("");
    }

    private int calculateGPA(String input){
        int s = Integer.parseInt(input);
        int sum = 0;
        int temp = s;
        int rem;
        while(temp > 0){
            rem = temp % 10;
            sum = sum + (rem * 3);
            temp = temp / 10;
        }
        Toast.makeText(this, "gpa = "+ sum/15, Toast.LENGTH_SHORT).show();
        return sum/15 ;
    }
}
