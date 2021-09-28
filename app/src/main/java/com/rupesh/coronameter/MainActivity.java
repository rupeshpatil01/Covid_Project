package com.rupesh.coronameter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaSync;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> countOfTotalArray = new ArrayList<String>();
    ArrayList<String> countOfDeathsArray = new ArrayList<String>();
    ArrayList<String> countOfRecoverArray = new ArrayList<>();
    ArrayList<String> timeArray = new ArrayList<String>();

    TextView countOfTotal;
    TextView countOfDeaths;
    TextView countOfRecover;
    TextView countOfTotalStates;
    TextView countOfDeathsStates;
    TextView countOfRecoverStates;
    TextView countOfTotalWorld;
    TextView countOfDeathsWorld;
    TextView countOfRecoverWorld;
    Spinner statesSpinner;
    String[] states;
    Button button;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countOfTotal = findViewById(R.id.countOfTotal);
        countOfDeaths = findViewById(R.id.countOfDeaths);
        countOfRecover = findViewById(R.id.countOfRecover);
        countOfTotalStates = findViewById(R.id.countOfTotalStates);
        countOfDeathsStates = findViewById(R.id.countOfDeathsStates);
        countOfRecoverStates = findViewById(R.id.countOfRecoverStates);
        countOfTotalWorld = findViewById(R.id.countOfTotalWorld);
        countOfDeathsWorld = findViewById(R.id.countOfDeathsWorld);
        countOfRecoverWorld = findViewById(R.id.countOfRecoverWorld);
        statesSpinner = findViewById(R.id.spinner2);
        button =findViewById(R.id.button);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        checkConnection();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,DistricActivity.class);
                startActivity(intent);
            }
        });

        states = getResources().getStringArray(R.array.statesNames);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, states);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statesSpinner.setAdapter(arrayAdapter);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET, "https://api.thevirustracker.com/free-api?global=stats", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    JSONObject info = jsonArray.getJSONObject(0);

                    countOfTotalWorld.setText(info.getString("total_cases"));
                    countOfRecoverWorld.setText(info.getString("total_recovered"));
                    countOfDeathsWorld.setText(info.getString("total_deaths"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Rupesh", "Something went wrong222");

            }
        });
        requestQueue.add(jsonObjectReq);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject statewise = jsonArray.getJSONObject(i);

                        countOfTotalArray.add(statewise.getString("confirmed"));
                        countOfDeathsArray.add(statewise.getString("deaths"));
                        countOfRecoverArray.add(statewise.getString("recovered"));
                    }

                    countOfTotal.setText(countOfTotalArray.get(0));
                    countOfDeaths.setText(countOfDeathsArray.get(0));
                    countOfRecover.setText(countOfRecoverArray.get(0));
                    countOfTotalStates.setText(countOfTotalArray.get(1));
                    countOfDeathsStates.setText(countOfDeathsArray.get(1));
                    countOfRecoverStates.setText(countOfRecoverArray.get(1));
                    statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            countOfTotalStates.setText(countOfTotalArray.get((position + 1)));
                            countOfDeathsStates.setText(countOfDeathsArray.get((position + 1)));
                            countOfRecoverStates.setText(countOfRecoverArray.get((position + 1)));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Rupesh", "Something went wrong33");

            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    public void checkConnection(){

        ConnectivityManager manager =(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if( null !=activeNetwork){

            if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Log.d("Interenet","connected");

            }
            else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){

                Log.d("Interenet","connected");

            }
            else{

                Toast.makeText(this, "Please connect to internet ", Toast.LENGTH_SHORT).show();

            }
        }

    }
}