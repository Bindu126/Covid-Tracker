package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.Adapter.DistrictWiseAdapter;
import com.example.covid_19tracker.Model.DistrictWiseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictActivity extends AppCompatActivity {

    private RecyclerView rv_state_wise;
    private DistrictWiseAdapter districtWiseAdapter;
    private ArrayList<DistrictWiseModel> districtWiseModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;

    private String str_state, str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_lastupdatedate;

    private MainActivity activity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        //setting up the title to actionbar
        getSupportActionBar().setTitle("Select District");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise all views
        Init();

        //Fetch Statewise data
        FetchStateWiseData();

        //Setting swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchStateWiseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Search
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });
    }

    private void Filter(String text) {
        ArrayList<DistrictWiseModel> filteredList = new ArrayList<>();
        for (DistrictWiseModel item : districtWiseModelArrayList) {
            if (item.getDistrict().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        //DistrictWiseAdapter.filterList(filteredList, text);
    }

    private void FetchStateWiseData() {
        //Show progress dialog
        //activity.ShowDialog(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://data.covid19india.org/data.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            districtWiseModelArrayList.clear();

                            for (int i = 1; i < jsonArray.length() ; i++){
                                JSONObject districtwise = jsonArray.getJSONObject(i);

                                //After fetching, storing the data into strings
                                str_state = districtwise.getString("state");

                                str_confirmed = districtwise.getString("confirmed");
                                str_confirmed_new = districtwise.getString("deltaconfirmed");

                                str_active = districtwise.getString("active");

                                str_death = districtwise.getString("deaths");
                                str_death_new = districtwise.getString("deltadeaths");

                                str_recovered = districtwise.getString("recovered");
                                str_recovered_new = districtwise.getString("deltarecovered");
                                str_lastupdatedate = districtwise.getString("lastupdatedtime");

                                //Creating an object of our statewise model class and passing the values in the constructor
                                DistrictWiseModel districtWiseModel = new DistrictWiseModel(str_state, str_confirmed, str_confirmed_new, str_active,
                                        str_death, str_death_new, str_recovered, str_recovered_new, str_lastupdatedate);
                                //adding data to our arraylist
                              districtWiseModelArrayList.add( districtWiseModel );
                            }

                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    districtWiseAdapter.notifyDataSetChanged();
                                    //activity.DismissDialog();
                                }
                            }, 1000);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activity_state_wise_swipe_refresh_layout);
        et_search = findViewById(R.id.activity_state_wise_search_editText);

        rv_state_wise = findViewById(R.id.activity_state_wise_recyclerview);
        rv_state_wise.setHasFixedSize(true);
        rv_state_wise.setLayoutManager(new LinearLayoutManager(this));

       districtWiseModelArrayList = new ArrayList<>();
        districtWiseAdapter = new DistrictWiseAdapter(DistrictActivity.this, districtWiseModelArrayList);
        rv_state_wise.setAdapter(districtWiseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}