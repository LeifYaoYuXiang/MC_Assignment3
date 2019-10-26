package com.example.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ModuleOpenHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_WORD = "create table Module (" +
            "id integer primary key autoincrement, " +
            "Code String, " +
            "Name String,"+
            "Lecture integer,"+
            "Week String,"+
            "StartHour integer,"+
            "StartMinute integer,"+
            "EndHour integer,"+
            "EndMinute integer,"+
            "ModuleLocation String,"+
            "ModuleComment String,"+
            "Notification integer,"+
            "NotificationTime integer)";

    public ModuleOpenHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_WORD);
        Toast.makeText(context, "Success Make one Database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
