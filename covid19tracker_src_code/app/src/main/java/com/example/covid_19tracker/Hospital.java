package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {
    private RecyclerView hospitalRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        hospitalRecView=findViewById(R.id.hospital_Rec_View);

        getSupportActionBar().setTitle("Select Hospital");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ArrayList<Emergency> hospitals= new ArrayList<>();
        hospitals.add(new Emergency("Labaid Specialized Hospital","10606"));
        hospitals.add(new Emergency("Labaid Cardiac Hospital","01766660711"));
        hospitals.add(new Emergency("United Hospital Limited","0914001234"));
        hospitals.add(new Emergency("Evercare Hospital Dhaka","09666710678"));
        hospitals.add(new Emergency("Square Hospital","09610010602"));
        hospitals.add(new Emergency("Asgar Ali Hospital","096666710601"));
        hospitals.add(new Emergency("ICU Specialized Hospital Limited","01854560761"));
        hospitals.add(new Emergency("Renessa Hospital And Research Istitute Limited","10606"));
        hospitals.add(new Emergency("CKD & Urology Hospital(ICU & NEPHROLO","10606"));
        hospitals.add(new Emergency("Dhaka Paediatric-Neonatal & General Hospital","10606"));
        hospitals.add(new Emergency("City Hospital & Diagonostic Center","10606"));
        hospitals.add(new Emergency("Ibnesina Diagonostic Hospital","01924886672"));
        hospitals.add(new Emergency("Popular Diagonostic Center Limited","10607"));
        hospitals.add(new Emergency("National Heart Foundation, Sylhet.","0181434256"));
        hospitals.add(new Emergency("National Institute of Preventive and Social Medicine","0181434256"));
        hospitals.add(new Emergency("Samorita Hospital Ltd.","0181434256"));
        hospitals.add(new Emergency("The Medical College for Women and Hospital","0181434256"));




        HospitalRecViewAdapter adapter =new HospitalRecViewAdapter(this);
        adapter.setHospitals(hospitals);
        hospitalRecView.setAdapter(adapter);
        hospitalRecView.setLayoutManager(new LinearLayoutManager(this));





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }



}