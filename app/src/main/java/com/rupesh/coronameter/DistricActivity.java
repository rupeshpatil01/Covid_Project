package com.rupesh.coronameter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistricActivity extends AppCompatActivity {

    ArrayList<Data> arrayNames;
    ProgramminAdapter adapter;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distric);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayNames = new ArrayList<>();
        adapter= new ProgramminAdapter();
        recyclerView.setAdapter(adapter);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                try {


                    JSONObject jsonObject= response.getJSONObject(20);

                    JSONArray jsonArray= jsonObject.getJSONArray( "districtData");


                    for (int i = 0; i <36; i++) {

                        JSONObject akola = jsonArray.getJSONObject(i);

                        Data data =new Data();

                        data.setName(akola.getString( "district"));
                        data.setTotal(String.valueOf(akola.getInt("confirmed")));
                        data.setDeaths(String.valueOf(akola.getInt("deceased")));
                        data.setRecover(String.valueOf(akola.getInt("recovered")));
                        arrayNames.add(data);

                        Log.d("lllllllllll" , "lkkkkk"+akola.getString("district"));

                    }
                    adapter.setData(arrayNames);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Rupesh", "Something went wrong111");

            }


        });
        requestQueue.add(jsonArrayRequest);



    }
}

