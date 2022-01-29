package com.example.applicationattendance;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SubjectandStudent db;

    TextView btnGS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SubjectandStudent(this);
        btnGS = findViewById(R.id.tvGS);


        View title = findViewById(R.id.appttendanc);
        View icon = findViewById(R.id.icon);
        View btnGS = findViewById(R.id.tvGS);

        ObjectAnimator anim = ObjectAnimator.ofFloat(title, "translationY", -100f, 70f);
        anim.setDuration(2000);
        anim.start();
        ObjectAnimator animm = ObjectAnimator.ofFloat(icon, "translationY", -100f, 70f);
        animm.setDuration(2000);
        animm.start();
        ObjectAnimator animmm = ObjectAnimator.ofFloat(btnGS, "rotation", 0f, 360f);
        animmm.setDuration(2000);
        animmm.start();


        btnGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.checkForAccount();
                if(res.getCount() == 0){
                    Intent i = new Intent(MainActivity.this,GetName.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(MainActivity.this,showSubjects.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure you want to go exit?");

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
