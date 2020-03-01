package com.adityaandroid.weatherviewapp;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaandroid.weatherviewapp.adapter.ForecastDataAdapter;
import com.adityaandroid.weatherviewapp.model.Forecast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ForecastDataAdapter mForecastAdapter;
    private ArrayList<Forecast> mForecastList;
    private RequestQueue mRequestQueue;
    private EditText userLocationText;
    private String userEnteredString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocationText = findViewById(R.id.locationTextView);
        userLocationText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    userEnteredString = userLocationText.getText().toString();
                    closeKeyboard();
                    parseJSON(userEnteredString);

                    return true;
                }
                return false;
            }


        });

        mForecastList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.weatherRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(itemDecoration);


        mForecastAdapter = new ForecastDataAdapter(MainActivity.this, mForecastList);
        mRecyclerView.setAdapter(mForecastAdapter);

        mRequestQueue = Volley.newRequestQueue(this);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void parseJSON(String locationText) {
        String urlLeft = "http://api.openweathermap.org/data/2.5/forecast?q=";
        String urlRight = "&APPID={API key}&units=metric"; // To get API key log into: https://openweathermap.org/appid
        String url = urlLeft+locationText+urlRight;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject forecastObject = jsonArray.getJSONObject(i);
                        long dayTimeStamp = forecastObject.getLong(  "dt");
                        String forecastTime = forecastObject.getString("dt_txt");

                        JSONObject main = forecastObject.getJSONObject("main");
                        double minTemp = main.getDouble("temp_min");
                        double maxTemp = main.getDouble("temp_max");
                        int humidity = main.getInt("humidity");

                        JSONObject wind = forecastObject.getJSONObject( "wind");
                        double windSpeed = wind.getDouble(  "speed");

                        JSONObject weather = forecastObject.getJSONArray(  "weather").getJSONObject(0);
                        String weatherDescription = weather.getString("description");
                        String iconName = weather.getString( "icon");

                        mForecastList.add(new Forecast(dayTimeStamp, weatherDescription, minTemp, maxTemp, humidity, windSpeed, iconName , forecastTime));

                    }

                    mForecastAdapter.notifyDataSetChanged(); //Rebind to recycler view



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

}
