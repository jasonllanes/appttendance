package com.example.applicationattendance;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class showStudents extends AppCompatActivity {


    SubjectandStudent db;
    Button btnAdd,btnBack,btnAddStudents,btnSave;
    ListView book_list;
    public ListAdapter adapter;
    String put,getId,getAttendance,putSubject,putTime;
    TextView tvName,tvSubject,tvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students);
        db = new SubjectandStudent(this);
        tvName = findViewById(R.id.tvName);
        tvTime = findViewById(R.id.tvTime);
        tvSubject = findViewById(R.id.tvSubject);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnAddStudents = findViewById(R.id.btnAddStudents);
         db.checkTeacherName();
         tvName.setText(db.name);
        put = getIntent().getExtras().getString("id");
        putSubject = getIntent().getExtras().getString("SubjectName");
        putTime = getIntent().getExtras().getString("Time");
//
        tvSubject.setText(putSubject);
        tvTime.setText(putTime);

        viewData();
        longClick();
        clickItem();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().removeExtra("id");
                getIntent().removeExtra("SubjectName");
                getIntent().removeExtra("Time");
                Intent i = new Intent(showStudents.this, showSubjects.class);
                startActivity(i);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(showStudents.this,"Saved!",Toast.LENGTH_LONG).show();
            }
        });

        btnAddStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(showStudents.this);
                final EditText newStudent = new EditText(showStudents.this);

                alert.setTitle("Add Student");
                newStudent.setInputType(InputType.TYPE_CLASS_TEXT);

                newStudent.setHint("Student name...");
                alert.setView(newStudent);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String convert =newStudent.getText().toString().toUpperCase();
                        if(newStudent.getText().toString().isEmpty()){
                            Toast.makeText(showStudents.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                        }else{
                            if(db.studentExist(convert,put)== true){
                                Toast.makeText(showStudents.this,"Student exist already.",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(showStudents.this,"Successfully Updated!",Toast.LENGTH_LONG).show();
                                db.addStudent(put,newStudent.getText().toString().toUpperCase());
                                viewData();
                            }

                        }


                    }
                });
                alert.show();
            }
        });


    }
    public void viewData () {
        ArrayList<HashMap<String, String>> bookList = db.getStudents(put);
        book_list = (ListView) findViewById(R.id.lvStudents);
        adapter = new SimpleAdapter(showStudents.this, bookList, R.layout.list_roww, new String[]{"id", "student", "attendance"}, new int[]{R.id.number, R.id.name, R.id.attendance});
        book_list.setAdapter(adapter);
    }
    public void longClick(){
            book_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                    getId = ((TextView) view.findViewById(R.id.number)).getText().toString();
                    AlertDialog.Builder alert = new AlertDialog.Builder(showStudents.this);
                    alert.setTitle("Update or Delete?");
                    alert.setMessage("Choose among the following:");

                    alert.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // do nothing
                            AlertDialog.Builder alert = new AlertDialog.Builder(showStudents.this);
                            alert.setTitle("Delete subject!");
                            alert.setMessage("Are you sure you want to delete?");

                            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // do nothing
                                }
                            });

                            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // sends user to key setup
                                    getId = ((TextView) view.findViewById(R.id.number)).getText().toString();
                                    View list = findViewById(R.id.item);
                                    db.deleteDataStudent(getId);
                                    viewData();

                                }
                            });
                            alert.show();
                        }
                    });

                    alert.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // sends user to key setup
                            final AlertDialog.Builder alert = new AlertDialog.Builder(showStudents.this);
                            final EditText newSubject = new EditText(showStudents.this);
                            alert.setCancelable(true);

                            alert.setTitle("Change name:");
                            newSubject.setInputType(InputType.TYPE_CLASS_TEXT);

                            newSubject.setHint("Name...");
                            alert.setView(newSubject);

                            alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // sends user to key setup
                                    String convert = newSubject.getText().toString().toUpperCase();
                                    if (newSubject.getText().toString().isEmpty()) {
                                        Toast.makeText(showStudents.this, "It seems that the blank provided is still empty.", Toast.LENGTH_LONG).show();
                                    } else {
                                        if(db.studentExist(convert,put)== true){
                                            Toast.makeText(showStudents.this, "Student exist already.", Toast.LENGTH_LONG).show();
                                        }else{
                                                            viewData();
                                                            db.updateStudent(getId,newSubject.getText().toString());
                                                            Toast.makeText(showStudents.this, "Successfully Added!", Toast.LENGTH_LONG).show();
                                                            book_list.setClickable(true);
                                                            viewData();
                                                        }

                                    }
                                }
                            });
                            alert.show();
                        }
                    });

                    alert.show();
                    return true;
                }
            });
        }
        public void clickItem(){
            book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getId = ((TextView) view.findViewById(R.id.number)).getText().toString();
                    getAttendance = ((TextView) view.findViewById(R.id.attendance)).getText().toString();

                    if(getAttendance.equalsIgnoreCase("Present")){
                        String yeah = "Absent";
                        db.updateAttendance(getId,"Absent");
                        viewData();
                    }else if(getAttendance.equalsIgnoreCase("Absent")){
                        String yeah = "Present";
                        db.updateAttendance(getId,"Present");
                        viewData();

                    }

                }
            });
        }
    @Override
    public void onBackPressed() {
       Intent i = new Intent(showStudents.this,showSubjects.class);
       startActivity(i);
       finish();

    }
    }

