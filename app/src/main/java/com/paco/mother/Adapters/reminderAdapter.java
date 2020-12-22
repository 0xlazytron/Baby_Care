package com.paco.mother.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.paco.mother.model.reminderModel;
import com.paco.mother.updateBaby;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class reminderAdapter extends RecyclerView.Adapter<reminderAdapter.ViewHolder> {
    ArrayList<reminderModel> reminder;
    Context context;
    dbHelper Helper;

    public reminderAdapter(ArrayList<reminderModel>rem,Context context){
        this.reminder = rem;
        this.context = context;
}
    @NonNull
    @Override
    public reminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reminders,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull reminderAdapter.ViewHolder holder, int position) {
        final reminderModel r = reminder.get(position);
        holder.id.setText(String.valueOf(r.getRem_id()));
        holder.title.setText(String.valueOf(r.getRem_name()));
        holder.time.setText(String.valueOf(r.getRem_time()));
        holder.date.setText(String.valueOf(r.getRem_date()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Are You Sure You Want to Delete "+r.getRem_name() + "?");
                deleteDialog.setIcon(android.R.drawable.ic_delete);
                deleteDialog.setCancelable(false);
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper helper = new dbHelper(context);
                        int result = helper.deleteRem(r.getRem_id());
                        if(result > 0){
                            Toast.makeText(context, "Reminder Deleted!", Toast.LENGTH_SHORT).show();
                            reminder.remove(r);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Failed No Baby to Reminder", Toast.LENGTH_SHORT).show();
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
        return (reminder == null) ? 0 : reminder.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,id,time;
        ImageView alarm_icon,delete;
        public ViewHolder(@NonNull View x) {
             super(x);
             id = x.findViewById(R.id.reminder_id);
            delete = x.findViewById(R.id.delete_reminder);
            title = x.findViewById(R.id.reminder_title);
            time = x.findViewById(R.id.reminder_time);
            date = x.findViewById(R.id.reminder_date);
            alarm_icon = x.findViewById(R.id.alarm_active);

        }
    }
}
