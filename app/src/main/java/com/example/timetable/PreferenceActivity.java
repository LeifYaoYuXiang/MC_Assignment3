package com.example.timetable;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ldoublem.loadingviewlib.LVChromeLogo;

public class PreferenceActivity extends AppCompatActivity {
    private int mThemeId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences("themePre",MODE_PRIVATE);
        int themeID=sharedPreferences.getInt("themeID",-1);
        setTheme(themeID);
        setContentView(R.layout.activity_preference);
        LVChromeLogo chromeLogo=findViewById(R.id.chrome);
        chromeLogo.startAnim();

        Button button=findViewById(R.id.themeBlue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheme(R.style.AppTheme);
                SharedPreferences.Editor editor=getSharedPreferences("themePre",MODE_PRIVATE).edit();
                editor.putInt("themeID",mThemeId);
                editor.apply();
            }
        });

        Button button1=findViewById(R.id.themePink);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheme(R.style.PinkTheme);
                SharedPreferences.Editor editor=getSharedPreferences("themePre",MODE_PRIVATE).edit();
                editor.putInt("themeID",mThemeId);
                editor.apply();
            }
        });

        Button button2=findViewById(R.id.themeGray);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheme(R.style.GrayTheme);
                SharedPreferences.Editor editor=getSharedPreferences("themePre",MODE_PRIVATE).edit();
                editor.putInt("themeID",mThemeId);
                editor.apply();
            }
        });

        Button button3=findViewById(R.id.themeOrange);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTheme(R.style.OrangeTheme);
                SharedPreferences.Editor editor=getSharedPreferences("themePre",MODE_PRIVATE).edit();
                editor.putInt("themeID",mThemeId);
                editor.apply();
            }
        });

    }

    private void onTheme(int iThemeId){
        mThemeId = iThemeId;
        this.recreate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", mThemeId);
    }
}
