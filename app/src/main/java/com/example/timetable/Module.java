package com.example.timetable;

public class Module {
    private int id;
    private String  moduleCode;
    private String week;
    private boolean lecture;
    private String location;
    private int startHour;
    private int startMinute;

    public Module(int id, String moduleCode, String week, boolean lecture, String location, int startHour, int startMinute) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.week = week;
        this.lecture = lecture;
        this.location = location;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    public void setLecture(boolean lecture) {
        this.lecture = lecture;
    }

    public boolean isLecture() {
        return lecture;
    }

    public int getId() {
        return id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getWeek() {
        return week;
    }


    public String getLocation() {
        return location;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }
}
