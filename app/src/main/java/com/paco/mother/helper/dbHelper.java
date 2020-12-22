package com.paco.mother.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.R;
import com.paco.mother.model.babyModel;
import com.paco.mother.model.reminderModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class dbHelper extends SQLiteOpenHelper {

  private static final int DB_VERSION = 7;
  private static final String DB_NAME= "mother.db";
  //TABLE(S)
  private static final String TBL_NAME= "baby";
  //TABLE_COULUN
  private static final String ID= "id";
  private static final String NAME= "name";
  private static final String HEIGHT= "height";
  private static final String WEIGHT= "weight";
  private static final String BIRTHDAY= "birthday";
  private static final String GENDER= "gender";
  private static final String PIC= "pic";
  private SQLiteDatabase DB;
  private ByteArrayOutputStream outputStream;
  private byte[] imageBytes;
  Context context;
     private static String CREATE = "CREATE TABLE baby (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,height TEXT NOT NULL,weight TEXT NOT NULL,birthday TEXT NOT NULL,gender TEXT NOT NULL,pic BLOB)";
     private static String REMINDER = "CREATE TABLE reminder (id INTEGER PRIMARY KEY AUTOINCREMENT,rem_name TEXT NOT NULL,rem_time TEXT NOT NULL,rem_type TEXT NOT NULL,rem_date TEXT NOT NULL,baby_name TEXT NOT NULL)";
     private static String ANALYTICS = "CREATE TABLE analytic (id INTEGER PRIMARY KEY AUTOINCREMENT,weight TEXT NOT NULL,height TEXT NOT NULL,date TEXT NOT NULL,baby_id INTEGER)";
  //CONSTRUCTOR
  public dbHelper(Context context){
       super(context,DB_NAME,null, DB_VERSION);
    this.context =context;
}
    @Override
    public void onCreate(SQLiteDatabase db) {
    try{
        db.execSQL(CREATE);
        db.execSQL(REMINDER);
        db.execSQL(ANALYTICS);
        StyleableToast.makeText(context,"200 ok", R.style.succes).show();
    }catch(Exception e){
        StyleableToast.makeText(context,"Error Creating Databse Table"+e.getMessage(), R.style.error).show();
    }
   
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS baby");
        db.execSQL(" DROP TABLE IF EXISTS reminder");
        db.execSQL(" DROP TABLE IF EXISTS analytic");
        onCreate(db);
    }
    public void addBaby(babyModel boy){
        try{
            DB = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id",boy.getId());
            values.put("name",boy.getName());
            values.put("height", boy.getHeight());
            values.put("weight", boy.getWeight());
            values.put("birthday", boy.getbirthDay());
            values.put("gender", boy.getGender());
            Bitmap imageToStore = boy.getImage();
            outputStream = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,60,outputStream);
            imageBytes = outputStream.toByteArray();
            values.put(dbHelper.PIC, imageBytes);
            Toast.makeText(context, "Name  :"+boy.getName() +"\n Height :"+boy.getHeight()+"\n Weight :"+boy.getWeight()
                    +"\n Birthday :"+boy.getbirthDay()+"\n Gender:"+boy.getGender()+"\n Gender:"+boy.getImage(), Toast.LENGTH_LONG).show();
            long checkQuery =  DB.insert("baby",null, values);

           if(checkQuery!=-1){
               StyleableToast.makeText(context,"BABY ADDED", R.style.succes).show();
               DB.close();
           }else{
               StyleableToast.makeText(context,"FAILED ADDING BABY", R.style.warning).show();
           }
        }catch(Exception e){
            StyleableToast.makeText(context,"FAILED ADDING BABY"+e.getMessage(), R.style.error).show();
        }

    }
     public void addReminder(reminderModel rem){
         try{
             DB = this.getWritableDatabase();
             ContentValues values = new ContentValues();
             values.put("id",rem.getRem_id());
             values.put("rem_name",rem.getRem_name());
             values.put("rem_time", rem.getRem_time());
             values.put("rem_date", rem.getRem_date());
             values.put("rem_type", rem.getRem_type());
             values.put("baby_name", rem.getBaby_name());
             Toast.makeText(context, "Reminder Id  :"+rem.getRem_id() +"\n Rem name :"+rem.getRem_name()+"\n Reminder Time :"+rem.getRem_time()
                     +"\n Reminde Date :"+rem.getRem_date()+"\n Rem Type:"+rem.getRem_type()+"\n Baby Id:"+rem.getBaby_id(), Toast.LENGTH_LONG).show();
             long checkQuery =  DB.insert("reminder",null, values);

             if(checkQuery!=-1){
                 StyleableToast.makeText(context,"REMINDER ADDED", R.style.succes).show();
                 DB.close();
             }else{
                 StyleableToast.makeText(context,"FAILED ADDING REMINDER", R.style.warning).show();
             }
         }catch(Exception e){
             StyleableToast.makeText(context,"FAILED ADDING Reminder"+e.getMessage(), R.style.error).show();
         }

     }
    public ArrayList<babyModel>getAllBabies(){
         try{
             DB = this.getReadableDatabase();
             ArrayList<babyModel> babyList = new ArrayList<>();
             Cursor cursor = DB.rawQuery("select * from baby",null);
             if(cursor.getCount()>0){
                 while(cursor.moveToNext()){
                     int id = cursor.getInt(0);
                     String babyName = cursor.getString(1);
                     String babyHeight = cursor.getString(2);
                     String babyWeight = cursor.getString(3);
                     String babyBirthday = cursor.getString(4);
                     String babyGender = cursor.getString(5);
                     byte[] imageBytes = cursor.getBlob(6);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                     Log.d(babyBirthday,babyName);
                     babyList.add(new babyModel(id,babyName,babyBirthday,bitmap,babyHeight,babyWeight,babyGender));

                 }
                 cursor.close();
                 return babyList;

             }else{
                 StyleableToast.makeText(context,"NO BABIES FOUND!", R.style.error).show();
                 return null;
             }

         }catch(Exception e){
             StyleableToast.makeText(context,"No COLUMN FOUND!\n"+e.getMessage(), R.style.error).show();
             return  null;
         }
     }
    public ArrayList<reminderModel>getAllrems(){
        try{
            DB = this.getReadableDatabase();
            ArrayList<reminderModel> remList = new ArrayList<>();
            Cursor cursor = DB.rawQuery("select * from reminder",null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String remName = cursor.getString(1);
                    String remTime = cursor.getString(2);
                    String remType = cursor.getString(3);
                    String remDate = cursor.getString(4);
                    remList.add(new reminderModel(id,remName,remTime,remType,remDate));

                }
                cursor.close();
                return remList;

            }else{
                StyleableToast.makeText(context,"NO REMINDERS FOUND!", R.style.error).show();
                return null;
            }

        }catch(Exception e){
            StyleableToast.makeText(context,"No COLUMN FOUND!\n"+e.getMessage(), R.style.error).show();
            return  null;
        }
    }
     //getting baby names for spinner
     public ArrayList<babyModel>getBabyNames(){
         try{
             DB = this.getReadableDatabase();
             ArrayList<babyModel> nameList = new ArrayList<>();
             Cursor cursor = DB.rawQuery("select * from baby",null);
             if(cursor.getCount()>0){
                 while(cursor.moveToNext()){
                     int id = cursor.getInt(0);
                     String babyName = cursor.getString(1);
                     nameList.add(new babyModel(id,babyName));

                 }
                 cursor.close();
                 return nameList;

             }else{
                 StyleableToast.makeText(context,"NO BABIES FOUND!", R.style.error).show();
                 return null;
             }

         }catch(Exception e){
             StyleableToast.makeText(context,"No COLUMN FOUND!\n"+e.getMessage(), R.style.error).show();
             return  null;
         }
     }
    public int deleteBaby(Integer id) {
        DB = this.getWritableDatabase();
      return DB.delete("baby ", "id=?", new String[]{String.valueOf(id)});

     }
//     public int BabyId(String name2) {
//
//         DB = this.getWritableDatabase();
////         String query = "SELECT id FROM baby WHERE name = "+name2;
//         String query = "SELECT  id FROM baby WHERE name ='+name2'";
//         Cursor c = DB.rawQuery(query,null);
//             while (c.moveToNext()) {
//                 baby_id = c.getColumnIndex("id");
//             }
//         return baby_id; }
     public void updateBaby(babyModel boy, String id){
      try{
          DB = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put("name",boy.getName());
          values.put("height", boy.getHeight());
          values.put("weight", boy.getWeight());
          values.put("birthday", boy.getbirthDay());
          values.put("gender", boy.getGender());
          Bitmap imageToStore = boy.getImage();
          outputStream = new ByteArrayOutputStream();
          imageToStore.compress(Bitmap.CompressFormat.JPEG,60,outputStream);
          imageBytes = outputStream.toByteArray();
          values.put(dbHelper.PIC, imageBytes);
          long results = DB.update("baby", values,"id=?",new String[]{String.valueOf(id)});
          if (results==-1){
              StyleableToast.makeText(context,"UPDATION FAILED", R.style.error).show();
          }else{
              StyleableToast.makeText(context,"BABY UPDATED", R.style.succes).show();
          }
      }catch(Exception x){
          StyleableToast.makeText(context,"UPDATION FAILED"+x.getMessage(), R.style.error).show();
      }

     }
    public int deleteRem(int rem_id) {
        DB = this.getWritableDatabase();
        return DB.delete("reminder ", "id=?", new String[]{String.valueOf(rem_id)});
    }
}
