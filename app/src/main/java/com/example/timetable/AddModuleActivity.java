package com.example.timetable;


import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;

public class AddModuleActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Button startTime;
    private Button endTime;
    private Spinner spinner;
    private Button save;
    private ToggleButton practicalButton;
    private ToggleButton notificationButton;

    private EditText moduleCodeEdit;
    private EditText moduleNameEdit;
    private EditText moduleLocationEdit;
    private EditText moduleCommentEdit;

    private int choiceTemp=-1;
    private int startHour=0;
    private int startMinute=0;
    private int endHour=0;
    private int endMinute=0;

    private String moduleCode="";
    private String moduleName="";
    private boolean lecture=false;
    private String week="";
    private String moduleLocation="";
    private String moduleComment="";
    private boolean notification=false;
    private int earlierTime=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences=getSharedPreferences("themePre",MODE_PRIVATE);
        int themeID=sharedPreferences.getInt("themeID",-1);
        setTheme(themeID);

        setContentView(R.layout.activity_add_module);
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        spinner=findViewById(R.id.chooseWeek);
        save=findViewById(R.id.saveModule);

        moduleNameEdit=findViewById(R.id.nameInformation);
        moduleCodeEdit=findViewById(R.id.codeInformation);
        moduleLocationEdit=findViewById(R.id.locationsInformation);
        moduleCommentEdit=findViewById(R.id.comment);
        ModuleOpenHelper moduleOpenHelper=new ModuleOpenHelper(this,"Module.db",null,2);
        sqLiteDatabase=moduleOpenHelper.getWritableDatabase();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        week="Mon";
                        break;
                    case 1:
                        week="Tue";
                        break;
                    case 2:
                        week="Wed";
                        break;
                    case 3:
                        week="Thur";
                        break;
                    case 4:
                        week="Fri";
                        break;
                    case 5:
                        week="Sat";
                        break;
                    case 6:
                        week="Sun";
                        break;
                    default:
                        week="";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddModuleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startHour = hourOfDay;
                        startMinute = minute;
                        if(startMinute<10){
                            startTime.setText("Start Time:"+startHour+":0"+startMinute);
                        }else{
                            startTime.setText("Start Time:"+startHour+":"+startMinute);
                        }
                    }
                }, startHour, startMinute, true).show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddModuleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endHour = hourOfDay;
                        endMinute = minute;
                        if(endMinute<10){
                            endTime.setText("End Time:"+endHour+":0"+endMinute);
                        }else{
                            endTime.setText("End Time:"+endHour+":"+endMinute);
                        }
                    }
                }, endHour, endMinute, true).show();
            }
        });


        practicalButton=findViewById(R.id.practical_lecture);
        practicalButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lecture=b;
            }
        });

        notificationButton=findViewById(R.id.notification);
        notificationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                notification=b;
                if(notification){
                    final String[] items = { "0 min Earlier","5 min Earlier","10 min Earlier","20 min Earlier" };
                    AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(AddModuleActivity.this);
                    singleChoiceDialog.setTitle("When do you want it to alarm you?");
                    singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            choiceTemp=which;
                        }
                    });
                    singleChoiceDialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (choiceTemp != -1) {
                                switch (choiceTemp){
                                    case 0:
                                      earlierTime=0;
                                      notificationButton.setText("Notification ON:0 min earlier");
                                      break;
                                    case 1:
                                        earlierTime=5;
                                        notificationButton.setText("Notification ON:5 min earlier");
                                        break;
                                    case 2:
                                        earlierTime=10;
                                        notificationButton.setText("Notification ON:10 min earlier");
                                        break;
                                    case 3:
                                        earlierTime=20;
                                        notificationButton.setText("Notification ON:20 min earlier");
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    });
                    singleChoiceDialog.show();
                }
            }
        });

        save=findViewById(R.id.saveModule);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moduleCode=moduleCodeEdit.getText().toString();
                moduleName=moduleNameEdit.getText().toString();
                moduleLocation=moduleLocationEdit.getText().toString();
                moduleComment=moduleCommentEdit.getText().toString();
                if(logical()){
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("Code",moduleCode);
                    contentValues.put("Name",moduleName);
                    if(lecture){
                        contentValues.put("Lecture",1);
                    }else{
                        contentValues.put("Lecture",0);
                    }
                    contentValues.put("Week",week);
                    contentValues.put("StartHour",startHour);
                    contentValues.put("StartMinute",startMinute);
                    contentValues.put("EndHour",endHour);
                    contentValues.put("EndMinute",endMinute);
                    contentValues.put("ModuleLocation",moduleLocation);
                    contentValues.put("ModuleComment",moduleComment);
                    if(notification){
                        contentValues.put("Notification",1);
                    }else{
                        contentValues.put("Notification",0);
                    }
                    contentValues.put("NotificationTime",earlierTime);
                    sqLiteDatabase.insert("Module",null,contentValues);
                    sqLiteDatabase.close();
                    Toast.makeText(AddModuleActivity.this, "Make One Module Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    if(!earlier(startHour,startMinute,endHour,endMinute)){
                        Toast.makeText(AddModuleActivity.this, "Start Time is behind End Time!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddModuleActivity.this, "Please Fill Information Fully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    private boolean logical(){
        if(!moduleCode.equals("")&&!moduleName.equals("")&&!week.equals("")&&!moduleLocation.equals("")){
            if(earlier(startHour,startMinute,endHour,endMinute)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean earlier(int startHour,int startMinute,int endHour,int endMinute){
        if(startHour>endHour){
            return false;
        }else{
            if(startHour==endHour){
               if(startMinute<endMinute){
                   return true;
               } else{
                   return false;
               }
            }else{
                return true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddModuleActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
