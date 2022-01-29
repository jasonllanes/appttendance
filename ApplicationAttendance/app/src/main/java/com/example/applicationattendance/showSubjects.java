package com.example.applicationattendance;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

public class showSubjects extends AppCompatActivity {


    SubjectandStudent db;
    Button btnAdd,btnName;
    ListView book_list;
    public ListAdapter adapter;

    TextView tvName;
    String getId,getAttendance,getSubjectName,getTime;
    private String addSubject = "";
    String name;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subjects);
        db = new SubjectandStudent(this);
        tvName = findViewById(R.id.tvName);
        btnName = findViewById(R.id.btnName);

        db.checkTeacherName();
        tvName.setText(db.name);


        btnAdd = findViewById(R.id.btnAdd);


        viewData();

        clickItem();
        longClick();

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                final EditText newName = new EditText(showSubjects.this);

                alert.setTitle("New Name");
                newName.setInputType(InputType.TYPE_CLASS_TEXT);

                newName.setHint("Full Name");
                alert.setView(newName);

                alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(newName.getText().toString().isEmpty()){
                            Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(showSubjects.this,"Successfully Updated!",Toast.LENGTH_LONG).show();
                            db.updateName("1",newName.getText().toString());
                            db.checkTeacherName();
                            tvName.setText(db.name);
                        }


                    }
                });
                alert.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(showSubjects.this,addSubjects.class);
//                startActivity(i);
                final AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                final EditText newSubject = new EditText(showSubjects.this);

                alert.setTitle("Add Subject");
                newSubject.setInputType(InputType.TYPE_CLASS_TEXT);

                newSubject.setHint("Subject name...");
                alert.setView(newSubject);

                alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // sends user to key setup
                        String convert = newSubject.getText().toString().toUpperCase();
                        if(newSubject.getText().toString().isEmpty()){
                            Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                        }else{
                            if(db.subjectExist(convert) == true){
                                Toast.makeText(showSubjects.this,"It already exist.",Toast.LENGTH_LONG).show();
                            }else{
                                viewData();
                                AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                                final EditText SubjectTime = new EditText(showSubjects.this);
                                alert.setTitle("Starting time?");
                                alert.setMessage("Tap the blank to select time:");

                                SubjectTime.setInputType(InputType.TYPE_CLASS_TEXT);
                                SubjectTime.requestFocus();
                                SubjectTime.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Calendar mcurrentTime = Calendar.getInstance();
                                        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                        final int minute = mcurrentTime.get(Calendar.MINUTE);
                                        TimePickerDialog mTimePicker;
                                        mTimePicker = new TimePickerDialog(showSubjects.this, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                if(selectedHour > 11){
                                                    if(selectedMinute == 0){
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "0 PM" );
                                                    }else{
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "PM");
                                                    }

                                                }else if(selectedHour == 12){
                                                    if(selectedMinute == 0){
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "0 NN" );
                                                    }else{
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "NN");
                                                    }
                                                }else{
                                                    if(selectedMinute == 0){
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "0 AM" );
                                                    }else{
                                                        SubjectTime.setText( selectedHour + ":" + selectedMinute + "AM");
                                                    }
                                                }
                                            }
                                        }, hour, minute, true);//Yes 24 hour time
                                        mTimePicker.setTitle("Select Time");
                                        mTimePicker.show();
                                    }
                                });

                                SubjectTime.setHint("Tap here to select time..");
                                alert.setView(SubjectTime);

                                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(SubjectTime.getText().toString().isEmpty()){
                                            Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                                        }else{
                                        viewData();
                                        AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                                        final EditText newSubjectTimee = new EditText(showSubjects.this);
                                        alert.setTitle("End time?");

                                        alert.setMessage("Tap the blank to select time:");
                                        newSubjectTimee.setInputType(InputType.TYPE_CLASS_TEXT);
                                        newSubjectTimee.requestFocus();
                                        newSubjectTimee.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Calendar mcurrentTime = Calendar.getInstance();
                                                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                                final int minute = mcurrentTime.get(Calendar.MINUTE);
                                                TimePickerDialog mTimePicker;
                                                mTimePicker = new TimePickerDialog(showSubjects.this, new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker timePicker, int selectedHourr, int selectedMinutee) {
                                                        if(selectedHourr > 11){
                                                            if(selectedMinutee == 0){
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "0 PM" );
                                                            }else{
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "PM");
                                                            }

                                                        }else if(selectedHourr == 12){
                                                            if(selectedMinutee == 0){
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "0 NN" );
                                                            }else{
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "NN");
                                                            }
                                                        }else{
                                                            if(selectedMinutee == 0){
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "0 AM" );
                                                            }else {
                                                                newSubjectTimee.setText( selectedHourr + ":" + selectedMinutee + "AM");
                                                            }
                                                        }
                                                    }
                                                }, hour, minute, true);//Yes 24 hour time
                                                mTimePicker.setTitle("Select Time");
                                                mTimePicker.show();
                                            }
                                        });

                                        newSubjectTimee.setHint("Tap here to select time");
                                        alert.setView(newSubjectTimee);

                                        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String convert = newSubject.getText().toString().toUpperCase();
                                                if(newSubjectTimee.getText().toString().isEmpty()){
                                                    Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                                                }else{
                                                    if(db.subjectExist(convert) == true){
                                                    Toast.makeText(showSubjects.this,"It already exist.",Toast.LENGTH_LONG).show();
                                                     }else{
                                                    db.addSub(newSubject.getText().toString().toUpperCase(),SubjectTime.getText().toString()+" - "+newSubjectTimee.getText().toString());
                                                    Toast.makeText(showSubjects.this,"Successfully Added!",Toast.LENGTH_LONG).show();
                                                    viewData();
                                                }

                                            }}
                                        });
                                        alert.show();
                                    }}
                                });
                                alert.show();
                            }

                        }

                    }
                });

                alert.show();
            }
        });

    }
    public void viewData () {
        ArrayList<HashMap<String, String>> bookList = db.getAll();
        book_list = (ListView) findViewById(R.id.lvSubjects);
        adapter = new SimpleAdapter(showSubjects.this, bookList, R.layout.list_row, new String[]{"ID", "SUBJECTS", "TIME"}, new int[]{R.id.number, R.id.subject, R.id.time});
        book_list.setAdapter(adapter);
    }

    public void clickItem(){
        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getId = ((TextView) view.findViewById(R.id.number)).getText().toString();
                getSubjectName = ((TextView) view.findViewById(R.id.subject)).getText().toString();
                getTime = ((TextView) view.findViewById(R.id.time)).getText().toString();
                Intent i = new Intent(showSubjects.this,showStudents.class);
                i.putExtra("id",getId);
                i.putExtra("SubjectName",getSubjectName);
                i.putExtra("Time",getTime);
                startActivity(i);
                finish();
            }
        });

    }
    public void longClick(){
        book_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                getId = ((TextView) view.findViewById(R.id.number)).getText().toString();
                getSubjectName =((TextView) view.findViewById(R.id.subject)).getText().toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                alert.setTitle("Update or Delete?");
                alert.setMessage("Choose among the following:");

                alert.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // do nothing
                        AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
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
                                db.deleteDataSubject(getId);
                                viewData();

                            }
                        });
                        alert.show();
                    }
                });

                alert.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // sends user to key setup
                                final AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                                final EditText newSubject = new EditText(showSubjects.this);
                                alert.setCancelable(true);

                                alert.setTitle("Add Subject");
                                newSubject.setInputType(InputType.TYPE_CLASS_TEXT);

                                newSubject.setHint("Subject name...");
                                alert.setView(newSubject);

                                alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // sends user to key setup
                                        String convert = newSubject.getText().toString().toUpperCase();
                                        if (newSubject.getText().toString().isEmpty()) {
                                            Toast.makeText(showSubjects.this, "It seems that the blank provided is still empty.", Toast.LENGTH_LONG).show();
                                        } else {
                                            if(db.subjectExist(newSubject.getText().toString())== true){
                                                Toast.makeText(showSubjects.this, "It already exist.", Toast.LENGTH_LONG).show();
                                            }else{
                                                viewData();
                                                AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                                                final EditText newSubjectTime = new EditText(showSubjects.this);
                                                alert.setTitle("Starting time?");
                                                alert.setMessage("Tap the blank to select time:");
                                                newSubjectTime.setInputType(InputType.TYPE_CLASS_TEXT);
                                                newSubjectTime.requestFocus();
                                                newSubjectTime.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Calendar mcurrentTime = Calendar.getInstance();
                                                        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                                        final int minute = mcurrentTime.get(Calendar.MINUTE);
                                                        TimePickerDialog mTimePicker;
                                                        mTimePicker = new TimePickerDialog(showSubjects.this, new TimePickerDialog.OnTimeSetListener() {
                                                            @Override
                                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                                if (selectedHour > 11) {
                                                                    if (selectedMinute == 0) {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "0 PM");
                                                                    } else {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "PM");
                                                                    }

                                                                } else if (selectedHour == 12) {
                                                                    if (selectedMinute == 0) {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "0 NN");
                                                                    } else {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "NN");
                                                                    }
                                                                } else {
                                                                    if (selectedMinute == 0) {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "0 AM");
                                                                    } else {
                                                                        newSubjectTime.setText(selectedHour + ":" + selectedMinute + "AM");
                                                                    }
                                                                }
                                                            }
                                                        }, hour, minute, true);//Yes 24 hour time
                                                        mTimePicker.setTitle("Select Time");
                                                        mTimePicker.show();
                                                    }
                                                });

                                                newSubjectTime.setHint("Tap here to select time..");
                                                alert.setView(newSubjectTime);

                                                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if(newSubjectTime.getText().toString().isEmpty()){
                                                            Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                                                        }else{
                                                        viewData();
                                                        AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
                                                        final EditText newSubjectTimee = new EditText(showSubjects.this);
                                                        alert.setTitle("End time?");
                                                        alert.setMessage("Tap the blank to select time:");
                                                        newSubjectTimee.setInputType(InputType.TYPE_CLASS_TEXT);
                                                        newSubjectTimee.requestFocus();
                                                        newSubjectTimee.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Calendar mcurrentTime = Calendar.getInstance();
                                                                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                                                final int minute = mcurrentTime.get(Calendar.MINUTE);
                                                                TimePickerDialog mTimePicker;
                                                                mTimePicker = new TimePickerDialog(showSubjects.this, new TimePickerDialog.OnTimeSetListener() {
                                                                    @Override
                                                                    public void onTimeSet(TimePicker timePicker, int selectedHourr, int selectedMinutee) {
                                                                        if (selectedHourr > 11) {
                                                                            if (selectedMinutee == 0) {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "0 PM");
                                                                            } else {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "PM");
                                                                            }

                                                                        } else if (selectedHourr == 12) {
                                                                            if (selectedMinutee == 0) {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "0 NN");
                                                                            } else {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "NN");
                                                                            }
                                                                        } else {
                                                                            if (selectedMinutee == 0) {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "0 AM");
                                                                            } else {
                                                                                newSubjectTimee.setText(selectedHourr + ":" + selectedMinutee + "AM");
                                                                            }
                                                                        }
                                                                    }
                                                                }, hour, minute, true);//Yes 24 hour time
                                                                mTimePicker.setTitle("Select Time");
                                                                mTimePicker.show();
                                                            }
                                                        });

                                                        newSubjectTimee.setHint("Tap here to select time..");
                                                        alert.setView(newSubjectTimee);

                                                        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String convert = newSubject.getText().toString().toUpperCase();
                                                                if(newSubjectTimee.getText().toString().isEmpty()){
                                                                    Toast.makeText(showSubjects.this,"It seems that the blank provided is still empty.",Toast.LENGTH_LONG).show();
                                                                }else{
                                                                    if(newSubject.getText().toString().equalsIgnoreCase(getSubjectName)){
                                                                        db.updateSubject(getId,newSubject.getText().toString().toUpperCase(), newSubjectTime.getText().toString() + " - " + newSubjectTimee.getText().toString());
                                                                        Toast.makeText(showSubjects.this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                                                                        book_list.setClickable(true);
                                                                        viewData();
                                                                    }else{
                                                                        if(db.subjectExist(convert) == true){
                                                                            Toast.makeText(showSubjects.this, "Subject already exist", Toast.LENGTH_LONG).show();
                                                                        }else{
                                                                            db.updateSubject(getId,newSubject.getText().toString().toUpperCase(), newSubjectTime.getText().toString() + " - " + newSubjectTimee.getText().toString());
                                                                            Toast.makeText(showSubjects.this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                                                                            book_list.setClickable(true);
                                                                            viewData();
                                                                        }

                                                                    }


                                                            }
                                                            }
                                                        });
                                                        alert.show();
                                                    }}
                                                });
                                                alert.show();
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


//    public void doubleTap(){
//        book_list.setOnTouchListener(new View.OnTouchListener() {
//            private GestureDetector gestureDetector = new GestureDetector(showSubjects.this, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    Intent i = new Intent(showSubjects.this,showStudents.class);
//                    startActivity(i);
//                    Toast.makeText(getApplicationContext(), "onDoubleTap", Toast.LENGTH_SHORT).show();
//                    return super.onDoubleTap(e);
//                }
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent event) {
//                    Log.d("onSingleTapConfirmed", "onSingleTap");
//                    return false;
//                }
//            });
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                gestureDetector.onTouchEvent(event);
//
//                return true;
//            }
//        });
//    }
@Override
public void onBackPressed() {
    AlertDialog.Builder alert = new AlertDialog.Builder(showSubjects.this);
    alert.setTitle("Exit");
    alert.setMessage("Are you sure you want to quit?");

    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // do nothing
        }
    });

    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // sends user to key setup
            finish();
            System.exit(0);

        }
    });

    alert.show();

}
}