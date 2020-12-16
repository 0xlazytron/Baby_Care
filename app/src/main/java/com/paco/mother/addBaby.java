package com.paco.mother;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;

import java.net.URI;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Boolean.FALSE;
public class addBaby extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 404;
    private Uri imagePath;
//    String gender;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Bitmap storeImage;
    String birthday;
    EditText txt_name, txt_height, txt_weight, txt_bday;
    RadioButton txt_male, txt_female,radioBtn;
    CircleImageView txt_pic;
    Button txt_add;
    RadioGroup radio;
    dbHelper helper = new dbHelper(addBaby.this);
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_baby);
        txt_name = findViewById(R.id.name);
        txt_weight = findViewById(R.id.weight);
        txt_height = findViewById(R.id.height);
        txt_bday = findViewById(R.id.bday);
        txt_male = findViewById(R.id.male);
        txt_female = findViewById(R.id.female);
        txt_pic = findViewById(R.id.pic);
        txt_add = findViewById(R.id.addbby);
        radio = (RadioGroup) findViewById(R.id.gender);
        int radioId = radio.getCheckedRadioButtonId();
        radioBtn = findViewById(radioId);
        LoadingBar = new ProgressDialog(this);
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateInfo();
            }

        });
        txt_bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addBaby.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                birthday = month + "/" + day + "/" + year;
                txt_bday.setText(birthday);
            }
        };
    }
    @SuppressLint("ResourceAsColor")
    private void validateInfo() {
//
        if(txt_pic.getDrawable()==null) {
            StyleableToast.makeText(addBaby.this,"Please Select Image For Baby", R.style.error).show();
            return;
        }else if(TextUtils.isEmpty(txt_name.getText())){
            txt_name.setError("Name should not be empty!");
            txt_name.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(txt_weight.getText())){
            txt_weight.setError("weight should not be empty!");
            txt_weight.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(txt_height.getText())){
            txt_height.setError("Height should not be empty!");
            txt_height.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(txt_bday.getText())){
            txt_bday.setError("Birthday should not be empty!");
            txt_bday.setTextColor(R.color.chili);
        }else{
            final String name, height,weight,gender;

            name = txt_name.getText().toString();
            height = txt_height.getText().toString();
            weight = txt_weight.getText().toString();
            birthday = txt_bday.getText().toString();

            if(txt_male.isChecked()){
                gender = "male";
            }else{
                gender = "female";
            }

            LoadingBar.setTitle("Adding");
            LoadingBar.setMessage("Please Wait, While we are Adding Details....");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            babyModel baby = new babyModel(name,weight,height,birthday,gender,storeImage);
            Toast.makeText(addBaby.this, "Gender "+gender, Toast.LENGTH_SHORT).show();
            helper.addBaby(baby);
            LoadingBar.dismiss();
            finish();
            startActivity(getIntent());
        }
    }
    public void imagePick(View gallery){
        try{
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(galleryIntent,IMAGE_PICK_CODE);

        }catch(Exception e){
            StyleableToast.makeText(addBaby.this,"Gallery Pick Failed", R.style.error).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==IMAGE_PICK_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imagePath = data.getData();
                storeImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                txt_pic.setImageBitmap(storeImage);
            }
        }catch (Exception e){
            StyleableToast.makeText(addBaby.this,"Image Upload Failed"+e.getMessage(), R.style.error).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(addBaby.this, manageBaby.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
