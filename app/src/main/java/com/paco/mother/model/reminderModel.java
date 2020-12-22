package com.paco.mother.model;

public class reminderModel {
    int rem_id, baby_id;
    String rem_name,rem_type,rem_time, rem_date,baby_name;


    public reminderModel(int rem_id, String rem_name, String rem_type, String rem_time, String rem_date) {
        this.rem_id = rem_id;
        this.rem_name = rem_name;
        this.rem_type = rem_type;
        this.rem_time = rem_time;
        this.rem_date = rem_date;
    }

    public reminderModel(String rem_name, String rem_type, String rem_time, String rem_date, String baby_name) {
        this.rem_name = rem_name;
        this.rem_type = rem_type;
        this.rem_time = rem_time;
        this.rem_date = rem_date;
        this.baby_name = baby_name;
    }
    public String getBaby_name() {
        return baby_name;
    }

    public void setBaby_name(String baby_name) {
        this.baby_name = baby_name;
    }

    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public int getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(int baby_id) {
        this.baby_id = baby_id;
    }

    public String getRem_name() {
        return rem_name;
    }

    public void setRem_name(String rem_name) {
        this.rem_name = rem_name;
    }

    public String getRem_type() {
        return rem_type;
    }

    public void setRem_type(String rem_type) {
        this.rem_type = rem_type;
    }

    public String getRem_time() {
        return rem_time;
    }

    public void setRem_time(String rem_time) {
        this.rem_time = rem_time;
    }

    public String getRem_date() {
        return rem_date;
    }

    public void setRem_date(String rem_date) {
        this.rem_date = rem_date;
    }
}
