package com.paco.mother;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.Adapters.babyAdapter;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;

import java.util.ArrayList;
import java.util.List;

public class manageBaby extends AppCompatActivity {
    private FloatingActionButton addbby;
    private RecyclerView babyList;
    private dbHelper db;
    private babyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_baby);
        addbby = findViewById(R.id.add_baby);
        babyList = findViewById(R.id.baby_list);
        addbby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBaby = new Intent(manageBaby.this, addBaby.class);
                startActivity(addBaby);
                finish();
            }
        });
//        trying to get bay list
        try{
            babyList = findViewById(R.id.baby_list);
            db = new dbHelper(this);
            adapter = new babyAdapter(db.getAllBabies(),this);

            babyList.setLayoutManager(new LinearLayoutManager(this));
            babyList.setHasFixedSize(true);
            babyList.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }catch(Exception x){
            StyleableToast.makeText(manageBaby.this,"ERROR GETTING THE LIST"+x.getMessage(), R.style.error).show();

        }
    }
}
