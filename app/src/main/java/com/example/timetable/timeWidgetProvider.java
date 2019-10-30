package com.example.timetable;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class timeWidgetProvider extends AppWidgetProvider {
    private final String ACTION_UPDATE_ALL = "UPDATE_ALL";
    private static Set idsSet = new HashSet();
    public static int mIndex;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            idsSet.add(Integer.valueOf(appWidgetId));
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();

        if (ACTION_UPDATE_ALL.equals(action)) {
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        } else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
            mIndex = 0;
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(context, WidgetService.class);
        context.startService(intent);
        super.onEnabled(context);
    }

    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {
        // widget 的id
        int appID;
        // 迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();
        // 要显示的那个数字，每更新一次 + 1
        String codeTemp="";
        String nameTemp="";
        int lectureTemp=0;
        ModuleOpenHelper moduleOpenHelper=new ModuleOpenHelper(context,"Module.db",null,2);
        SQLiteDatabase sqLiteDatabase=moduleOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Module",new String[]{
                "Code",
                "Name",
                "Lecture",
        }, null,null, null, null,null);
        if (cursor.moveToFirst()) {
            do {
                codeTemp= cursor.getString(0);
                nameTemp=cursor.getString(1);
                lectureTemp=cursor.getInt(2);
            }
            while(cursor.moveToNext());
        }
        cursor.close();


        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();
            // 获取 example_appwidget.xml 对应的RemoteViews
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
            // 设置显示数字
            String shown=nameTemp+":"+codeTemp;
            if(lectureTemp==1){
                shown=shown+"\n"+"L";
            }else{
                shown=shown+"\n"+"P";
            }
            remoteView.setTextViewText(R.id.widget_txt,shown);
            // 设置点击按钮对应的PendingIntent：即点击按钮时，发送广播。
            remoteView.setOnClickPendingIntent(R.id.widget_open, getOpenPendingIntent(context));
            // 更新 widget
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }

    private PendingIntent getOpenPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("main", "这句话是我从桌面点开传过去的。");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }

    @Override
    public void onDisabled(Context context) {
        // 在最后一个 widget 被删除时，终止服务
        Intent intent = new Intent(context, WidgetService.class);
        context.stopService(intent);
        super.onDisabled(context);
    }


}
