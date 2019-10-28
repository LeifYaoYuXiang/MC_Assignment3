package com.example.timetable;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PullToRefreshView mPullToRefreshView;
    private ListView moduleListView;
    private ModuleOpenHelper moduleOpenHelper;
    private List<Integer> idList=new ArrayList<>();
    private List<Module> moduleList=new ArrayList<>();
    private List<String> codeList=new ArrayList<>();
    private List<String> weekList=new ArrayList<>();
    private List<Boolean> lectureList=new ArrayList<>();
    private List<String>  locationList=new ArrayList<>();
    private List<Integer> startHourList=new ArrayList<>();
    private List<Integer> startMinuteList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_setting:
                   Intent intent=new Intent(MainActivity.this,PreferenceActivity.class);
                   startActivity(intent);
            }
            return false;
    }

    private void initDatabase(){
        this.moduleOpenHelper=new ModuleOpenHelper(this,"Module.db",null,2);
        SQLiteDatabase sqLiteDatabase=moduleOpenHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("Module",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                idList.add(id);
                String  moduleCode=cursor.getString(cursor.getColumnIndex("Code"));
                codeList.add(moduleCode);
                String week=cursor.getString(cursor.getColumnIndex("Week"));
                weekList.add(week);
                int lecture=cursor.getInt(cursor.getColumnIndex("Lecture"));
                if(lecture==1){
                    lectureList.add(true);
                }else{
                    lectureList.add(false);
                }
                String location=cursor.getString(cursor.getColumnIndex("ModuleLocation"));
                locationList.add(location);
                int startHour=cursor.getInt(cursor.getColumnIndex("StartHour"));
                startHourList.add(startHour);
                int startMinute=cursor.getInt(cursor.getColumnIndex("StartMinute"));
                startMinuteList.add(startMinute);

                if(lecture==1){
                    Module module=new Module(id,moduleCode,week,true,location,startHour,startMinute);
                    moduleList.add(module);
                }else{
                    Module module=new Module(id,moduleCode,week,false,location,startHour,startMinute);
                    moduleList.add(module);
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences=getSharedPreferences("themePre",MODE_PRIVATE);
        int themeID=sharedPreferences.getInt("themeID",-1);
        setTheme(themeID);

        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolBar);
        moduleListView=findViewById(R.id.module_list);

        setSupportActionBar(toolbar);
        mPullToRefreshView = this.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        FloatingActionButton floatingActionButton=findViewById(R.id.add_modules);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddModuleActivity.class);
                startActivity(intent);
            }
        });
        moduleOpenHelper=new ModuleOpenHelper(this,"Module.db",null,2);


        super.onResume();
        this.moduleList=new ArrayList<>();
        initDatabase();
        final ModuleAdapter moduleAdapter=new ModuleAdapter(MainActivity.this,R.layout.module_item,moduleList);
        moduleListView.setAdapter(moduleAdapter);
        moduleListView.setTextFilterEnabled(true);

        moduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Module temp = moduleAdapter.getItem(i);
                int id = temp.getId();
               Intent intent=new Intent(MainActivity.this,ModuleChangeActivity.class);
               Bundle bundle=new Bundle();
               bundle.putInt("id",id);
               intent.putExtras(bundle);
               startActivity(intent);
            }
        });
      moduleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int index, long l) {
              AlertDialog.Builder aBuider=new AlertDialog.Builder(MainActivity.this);
              aBuider.setTitle("Warning!");
              aBuider.setMessage("Attention! You are try to delete this Module");
              aBuider.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      Module temp=moduleAdapter.getItem(index);
                      int id=temp.getId();
                      SQLiteDatabase sqLiteDatabase=moduleOpenHelper.getWritableDatabase();
                      sqLiteDatabase.delete("Module","id=?",new String[]{id+""});
                      Intent intent=new Intent(MainActivity.this,MainActivity.class);
                      startActivity(intent);
                  }
              });
              aBuider.setNegativeButton("Cancel Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                  }
              });
              aBuider.show();
              return true;

          }
      });


    }

    @Override
    public void onBackPressed() {

    }
}
