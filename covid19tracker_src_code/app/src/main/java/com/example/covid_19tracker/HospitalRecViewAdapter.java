package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class HospitalRecViewAdapter extends RecyclerView.Adapter<HospitalRecViewAdapter.ViewHolder> {
    ArrayList<  Emergency> hospitals= new ArrayList<>();
    private Context context;
    public HospitalRecViewAdapter(Context context) {
        this.context=context;

    }

    @NonNull
    @org.jetbrains.annotations.NotNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_emergency,parent,false);
        ViewHolder holder=new ViewHolder((view));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull HospitalRecViewAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(hospitals.get(position).getName());
        holder.txtPhone.setText(hospitals.get(position).getPhone());


        holder.parent.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(Intent.ACTION_DIAL);
                                                 intent.setData(Uri.parse("tel:" + Uri.encode(hospitals.get(position).getPhone())));

                                                 startActivity(context, intent, null);
                                             }
                                         }

        );
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public void setHospitals(ArrayList<Emergency> hospitals) {
        this.hospitals = hospitals;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtName,txtPhone;
        private RelativeLayout parent;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            txtName=itemView.findViewById(R.id.hospitalName);
            txtPhone=itemView.findViewById(R.id.hospitalPhone);


        }
    }

}

