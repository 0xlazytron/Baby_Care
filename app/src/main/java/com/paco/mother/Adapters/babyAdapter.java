package com.paco.mother.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.MessagePattern;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.paco.mother.R;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;
import com.paco.mother.updateBaby;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class babyAdapter extends RecyclerView.Adapter<babyAdapter.ViewHolder>{
    ArrayList<babyModel> baby;
    Context context;
    dbHelper Helper;

    public babyAdapter(ArrayList<babyModel>baby, Context context)
    {
        this.baby = baby;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new babyAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
               .inflate(R.layout.listbabies,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        final babyModel bby = baby.get(i);
        holder.bbyid.setText(String.valueOf(bby.getId()));
        holder.height.setText("Height : "+bby.getHeight()+" ft");
        holder.weight.setText("Weight : "+bby.getWeight()+" kgs");
        holder.gender.setText("Gender : "+bby.getGender());
        holder.name.setText(" "+bby.getName());
        holder.birthday.setText(bby.getbirthDay());
        holder.image.setImageBitmap(bby.getImage());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goUpdate = new Intent(context, updateBaby.class);
                goUpdate.putExtra("id", String.valueOf(bby.getId()));
                goUpdate.putExtra("name", String.valueOf(bby.getName()));
                goUpdate.putExtra("birthday", String.valueOf(bby.getbirthDay()));
                goUpdate.putExtra("weight", String.valueOf(bby.getWeight()));
                goUpdate.putExtra("height", String.valueOf(bby.getHeight()));
                goUpdate.putExtra("gender", String.valueOf(bby.getGender()));
                Bitmap b = bby.getImage();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 60, bs);
                byte[] byteArray = bs.toByteArray();
                goUpdate.putExtra("picture", byteArray);
                context.startActivity(goUpdate);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Are You Sure You Want to Delete "+bby.getName() + "?");
                deleteDialog.setIcon(android.R.drawable.ic_delete);
                deleteDialog.setCancelable(false);
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper helper = new dbHelper(context);
                        int result = helper.deleteBaby(bby.getId());
                        if(result > 0){
                            Toast.makeText(context, "Baby Deleted!", Toast.LENGTH_SHORT).show();
                            baby.remove(bby);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Failed No Baby to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                deleteDialog.setNegativeButton("No", null);
                deleteDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (baby == null) ? 0 : baby.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        Button edit,delete;
        TextView name,birthday,height,weight,gender,bbyid;
        ImageView image;
        public ViewHolder(@NonNull View x) {
            super(x);
            edit = x.findViewById(R.id.edit_baby);
            delete = x.findViewById(R.id.delete_baby);
            name = x.findViewById(R.id.name);
            birthday = x.findViewById(R.id.birthday);
            image = x.findViewById(R.id.image);
            bbyid = x.findViewById(R.id.babyId);
            height = x.findViewById(R.id.height);
            weight = x.findViewById(R.id.weight);
            gender = x.findViewById(R.id.gender);

        }
    }

}
