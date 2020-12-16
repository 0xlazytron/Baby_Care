package com.paco.mother.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paco.mother.R;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;
import com.paco.mother.model.reminderModel;

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
        holder.date.setText(String.valueOf(r.getRem_time()));
        holder.title.setText(String.valueOf(r.getRem_name()));
        holder.title.setText(String.valueOf(r.getRem_name()));
    }

    @Override
    public int getItemCount() {
        return (reminder == null) ? 0 : reminder.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Button edit,delete;
        TextView title,date,id;
        ImageView alarm_icon;
        public ViewHolder(@NonNull View x) {
             super(x);
             id = x.findViewById(R.id.reminder_id);
            edit = x.findViewById(R.id.edit_reminder);
            delete = x.findViewById(R.id.delete_reminder);
            title = x.findViewById(R.id.reminder_title);
            date = x.findViewById(R.id.reminder_time);
            alarm_icon = x.findViewById(R.id.alarm_active);

        }
    }
}
