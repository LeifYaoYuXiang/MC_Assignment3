package com.example.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ModuleAdapter extends ArrayAdapter<Module> {
    private int resourceId;

    public ModuleAdapter(Context context, int resource, List<Module> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Module module=getItem(position);
        View view =  LayoutInflater.from(getContext()).inflate(resourceId, parent,false);

        TextView textView = view.findViewById(R.id.code_item);
        textView.setText(module.getModuleCode());
        TextView textView1=view.findViewById(R.id.lecture_item);
        if(module.isLecture()){
            textView1.setText("L");
        }else{
            textView1.setText("P");
        }
        TextView textView2 = view.findViewById(R.id.time_item);
        int hour=module.getStartHour();
        int minute=module.getStartMinute();
        String week=module.getWeek();

        textView2.setText(week+","+hour+":"+minute);
        TextView textView3 = view.findViewById(R.id.position_item);
        textView3.setText(module.getLocation());


        return view;
    }
}
