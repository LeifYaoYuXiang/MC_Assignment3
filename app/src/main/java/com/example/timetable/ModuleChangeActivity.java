package com.example.timetable;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ModuleChangeActivity extends AppCompatActivity {
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

    public int id;
    private int choiceTemp=-1;
    private int startHour=0;
    private int startMinute=0;
    private int endHour=0;
    private int endMinute=0;

    private String moduleCode="";
    private String moduleName="";
    private int lecture=0;
    private String week="";
    private String moduleLocation="";
    private String moduleComment="";
    private int notification=0;
    private int earlierTime=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences=getSharedPreferences("themePre",MODE_PRIVATE);
        int themeID=sharedPreferences.getInt("themeID",-1);
        setTheme(themeID);

        setContentView(R.layout.activity_module_change);

        ModuleOpenHelper moduleOpenHelper=new ModuleOpenHelper(this,"Module.db",null,2);
        sqLiteDatabase=moduleOpenHelper.getWritableDatabase();

        this.moduleCodeEdit=findViewById(R.id.codeInformationChange);
        this.moduleNameEdit=findViewById(R.id.nameInformationChange);
        this.moduleLocationEdit=findViewById(R.id.locationsInformation_change);
        this.moduleCommentEdit=findViewById(R.id.comment_change);

        this.startTime=findViewById(R.id.startTime_change);
        this.endTime=findViewById(R.id.endTime_change);
        this.practicalButton=findViewById(R.id.practical_lecture_change);
        this.notificationButton=findViewById(R.id.notification_change);
        this.save=findViewById(R.id.saveModuleChange);
        this.spinner=findViewById(R.id.chooseWeek_change);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final int id=bundle.getInt("id");
        this.id=id;



        String stringID = id+"";
        Cursor cursor = sqLiteDatabase.query("Module",new String[]{
                "Code",
                "Name",
                "Lecture",
                "Week",
                "StartHour",
                "StartMinute",
                "EndHour",
                "EndMinute",
                "ModuleLocation",
                "ModuleComment",
                "Notification",
                "NotificationTime"
        }, "id like ?",new String[]{stringID}, null, null,null);
        if (cursor.moveToFirst()) {
            do {
                this.moduleCode= cursor.getString(0);
                this.moduleName=cursor.getString(1);
                this.lecture=cursor.getInt(2);
                this.week=cursor.getString(3);
                this.startHour=cursor.getInt(4);
                this.startMinute=cursor.getInt(5);
                this.endHour=cursor.getInt(6);
                this.endMinute=cursor.getInt(7);
                this.moduleLocation=cursor.getString(8);
                this.moduleComment=cursor.getString(9);
                this.notification=cursor.getInt(10);
                this.earlierTime=cursor.getInt(11);

                this.moduleCodeEdit.setText(this.moduleCode);
                this.moduleNameEdit.setText(this.moduleName);
                this.moduleLocationEdit.setText(this.moduleLocation);
                this.moduleCommentEdit.setText(this.moduleComment);

                if(this.lecture==0){
                    practicalButton.setChecked(false);
                }else{
                    practicalButton.setChecked(true);
                }

                if(startMinute<10){
                    startTime.setText("Start Time:"+startHour+":0"+startMinute);
                }else{
                    startTime.setText("Start Time:"+startHour+":"+startMinute);
                }

                if(endMinute<10){
                    endTime.setText("End Time:"+endHour+":0"+endMinute);
                }else{
                    endTime.setText("End Time:"+endHour+":"+endMinute);
                }

                if(this.notification==0){
                    notificationButton.setChecked(false);
                }else{
                    notificationButton.setChecked(true);
                    notificationButton.setText("Notification ON:"+earlierTime+" min earlier");
                }
                switch (week){
                    case "Mon":
                        spinner.setSelection(0);
                        break;
                    case "Tue":
                        spinner.setSelection(1);
                        break;
                    case "Wed":
                        spinner.setSelection(2);
                        break;
                    case "Thur":
                        spinner.setSelection(3);
                        break;
                    case "Fri":
                        spinner.setSelection(4);
                        break;
                    case "Sat":
                        spinner.setSelection(5);
                        break;
                    case "Sun":
                        spinner.setSelection(6);
                        break;
                    default:
                        break;
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();

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
                new TimePickerDialog(ModuleChangeActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                new TimePickerDialog(ModuleChangeActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        practicalButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lecture=1;
                }else{
                    lecture=0;
                }
            }
        });

        notificationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    notification=1;
                    final String[] items = { "0 min Earlier","5 min Earlier","10 min Earlier","20 min Earlier" };
                    AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(ModuleChangeActivity.this);
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
                }else {
                    notification=0;
                }
            }
        });

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
                    if(lecture==1){
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
                    if(notification==1){
                        contentValues.put("Notification",1);
                    }else{
                        contentValues.put("Notification",0);
                    }
                    contentValues.put("NotificationTime",earlierTime);
                    sqLiteDatabase.update("Module",contentValues,"id=?",new String[]{id+""});
                    sqLiteDatabase.close();
                    Toast.makeText(ModuleChangeActivity.this, "Change One Module Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    if(!earlier(startHour,startMinute,endHour,endMinute)){
                        Toast.makeText(ModuleChangeActivity.this, "Start Time is behind End Time!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ModuleChangeActivity.this, "Please Fill Information Fully", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(ModuleChangeActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
