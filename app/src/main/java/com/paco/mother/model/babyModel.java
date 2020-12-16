package com.paco.mother.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class babyModel {
    Integer id;
    String name, height,weight,birthDay,gender,imageName;
    Bitmap image;
    public babyModel(int id ,String name, String birthDay,Bitmap image,String height, String weight,String gender) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public babyModel(String name, String height, String weight, String birthDay, String gender, Bitmap image ) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthDay = birthDay;
        this.gender = gender;
        this.image = image;


    }

    public babyModel(Integer id, String name, String height, String weight, String birthDay, String gender, String imageName, Bitmap image) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthDay = birthDay;
        this.gender = gender;
        this.imageName = imageName;
        this.image = image;
    }

    public babyModel(String name, String weight, String height, String birthday, String gender, ImageView pic) {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getbirthDay() {
        return birthDay;
    }

    public void setbirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
