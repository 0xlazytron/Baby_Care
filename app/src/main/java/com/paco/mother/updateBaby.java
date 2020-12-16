package com.paco.mother;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;
import java.util.Calendar;
public class updateBaby extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 404;
    private Uri imagePath;
    Button updatBby;
    Bitmap bmp;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Bitmap storeImage;
    EditText updateName,updateHeight,updateWeight,updateBirthday;
    String id, name, height, weight, gender,birthday;
    RadioButton male, female;
    ImageView pic;
    dbHelper helper = new dbHelper(updateBaby.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_baby);
        updatBby = findViewById(R.id.btn_update);
        updateName = findViewById(R.id.update_name);
        updateHeight = findViewById(R.id.update_height);
        updateWeight = findViewById(R.id.update_weight);
        updateBirthday = findViewById(R.id.update_bday);
        pic = findViewById(R.id.update_pic);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        getIntentData();
        updatBby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInfo();
            }
        });
        updateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateBaby.this,
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
                updateBirthday.setText(birthday);
            }
        };
    }
    public void imagePick(View gallery){
        try{
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(galleryIntent,IMAGE_PICK_CODE);
        }catch(Exception e){
            StyleableToast.makeText(updateBaby.this,"Error Uploading Image!", R.style.error).show(); }}
    void getIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("name")
                && getIntent().hasExtra("birthday")
                && getIntent().hasExtra("height")
                && getIntent().hasExtra("weight")
                && getIntent().hasExtra("gender")){

            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            height = getIntent().getStringExtra("height");
            weight = getIntent().getStringExtra("weight");
            birthday = getIntent().getStringExtra("birthday");
            gender = getIntent().getStringExtra("gender");
            byte[] byteArray = getIntent().getByteArrayExtra("picture");
             bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//            Bitmap b = BitmapFactory.decodeByteArray(
//                    getIntent().getByteArrayExtra("picture"),
//                    0,
//                    getIntent().getByteArrayExtra("picture").length);
            pic.setImageBitmap(bmp);
            Toast.makeText(this, "IMage"+bmp.toString(), Toast.LENGTH_SHORT).show();

            //setting Intent Data
            updateName.setText(name);
            updateHeight.setText(height);
            updateWeight.setText(weight);
            updateBirthday.setText(birthday);

            if(gender=="male"){
            male.setChecked(true);
            }else{
            female.setChecked(false);
            }
            if(gender=="female"){
                female.setChecked(true);
            }else{
                male.setChecked(false);
            }

        }else{
            StyleableToast.makeText(updateBaby.this,"NO DATA FOUND!", R.style.error).show();
        }
    }
    @SuppressLint("ResourceAsColor")
    private void validateInfo() {
        if(pic.getDrawable()==null) {
            StyleableToast.makeText(updateBaby.this,"Please Select Image For Baby", R.style.warning).show();
        }else if(TextUtils.isEmpty(updateName.getText())){
            updateName.setError("Name should not be empty!");
            updateName.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(updateWeight.getText())){
            updateWeight.setError("weight should not be empty!");
            updateWeight.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(updateHeight.getText())){
            updateHeight.setError("Height should not be empty!");
            updateHeight.setTextColor(R.color.chili);
        }else if(TextUtils.isEmpty(updateBirthday.getText())){
            updateBirthday.setError("Birthday should not be empty!");
            updateBirthday.setTextColor(R.color.chili);
        }else{
            final String name, height,weight,gender;
            name = updateName.getText().toString();
            height = updateHeight.getText().toString();
            weight = updateWeight.getText().toString();
            birthday = updateBirthday.getText().toString();
            if(male.isChecked()){
                gender = "male";
            }else{
                gender = "female";
            }
            if(storeImage==null){
                babyModel baby = new babyModel(name,weight,height,birthday,gender,bmp);
                helper.updateBaby(baby,id);


            }else{
                babyModel baby = new babyModel(name,weight,height,birthday,gender,storeImage);
                helper.updateBaby(baby,id);

            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==IMAGE_PICK_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imagePath = data.getData();
                storeImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                pic.setImageBitmap(storeImage);
            }
        }catch (Exception e){
            Toast.makeText(this, "[EXCEPTION] :"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(updateBaby.this, manageBaby.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}