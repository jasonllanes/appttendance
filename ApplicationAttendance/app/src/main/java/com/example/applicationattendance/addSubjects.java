package com.example.applicationattendance;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class addSubjects extends AppCompatActivity {

    SubjectandStudent db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);
        db = new SubjectandStudent(this);



    }
//    public void addSubject(){
//        btnAddSubject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(etAddSubject.getText().toString().isEmpty()){
//                    Toast.makeText(addSubject.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
//                }else{
//                    db.addSub(etAddSubject.getText().toString());
//                    int counts = db.getLength();
//                    db.close();
//                    Toast.makeText(addSubject.this,"Successfully Added!",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(addSubject.this,Menu.class);
//                    startActivity(i);
//
//                }
//            }
//        });

}
