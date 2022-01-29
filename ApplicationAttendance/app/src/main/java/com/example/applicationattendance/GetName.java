package com.example.applicationattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetName extends AppCompatActivity {

    SubjectandStudent db ;
    Button btnDone;
    EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);
        db = new SubjectandStudent(this);
        etName = findViewById(R.id.etName);
        btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()){
                    Toast.makeText(GetName.this,"Please fill it up!",Toast.LENGTH_LONG).show();
                }else{
                    db.addTeacher(etName.getText().toString());
                    Toast.makeText(GetName.this,"Successfully Added!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(GetName.this,showSubjects.class);
                    startActivity(i);
                    finish();
                }
            }
        });

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(etName.getText().toString().isEmpty()){
//                    Toast.makeText(GetName.this,"Please fill it up!",Toast.LENGTH_LONG).show();
//                }else{
//                    db.addTeacher(etName.getText().toString());
//                    Toast.makeText(GetName.this,"Successfully Added!",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });

    }
}
