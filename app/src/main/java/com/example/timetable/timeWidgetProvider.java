package com.example.timetable;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class timeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /*
     * 删除一个AppWidget所调用的方法
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /*
     * 接收广播事件
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    /*
     * 创建第一个AppWiget实例所调用的方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /*
     * 删除最后一个Appwidget所调用的方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

}
