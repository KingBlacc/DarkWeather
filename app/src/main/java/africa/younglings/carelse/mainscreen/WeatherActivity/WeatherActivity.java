package africa.younglings.carelse.mainscreen.WeatherActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.Currently;
import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;
import africa.younglings.carelse.mainscreen.R;

public class WeatherActivity extends AppCompatActivity implements IWeatherView{

    //Layouts
    private ConstraintLayout layoutWeather;
    private LinearLayout layoutRain;

    //Widgets
    private TextView tvLocation;
    private TextView tvTime;
    private TextView tvTemperature;
    private ImageView weatherIcon;
    private TextView tvConditions;
    private TextView tvWind;
    private TextView tvWindConditions;
    private TextView tvPrecipitation;
    private TextView tvPrecipitationConditions;
    private TextView tvHumidity;
    private TextView tvHumidityConditions;
    private TextView tvSorry;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RootObject rootObject;

    //Variables
    private String location;
    private final String SHARED_PREFERENCE = "SHARED_PREFS";
    private final String CACHED_DATA = "CACHED_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        init();
    }

    @Override
    public void init() {
        layoutWeather = findViewById(R.id.layoutWeather);
        layoutRain = findViewById(R.id.layoutRain);
        tvLocation = findViewById(R.id.tvLocation);
        tvTime = findViewById(R.id.tvCurrentTime);
        tvTemperature = findViewById(R.id.tvTemperature);
        weatherIcon = findViewById(R.id.weatherIcon);
        tvConditions = findViewById(R.id.tvConditions);
        tvWind = findViewById(R.id.tvWind);
        tvWindConditions = findViewById(R.id.tvWindConditions);
        tvPrecipitation = findViewById(R.id.tvPrecipitation);
        tvPrecipitationConditions = findViewById(R.id.tvPrecipitationConditions);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvHumidityConditions = findViewById(R.id.tvHumidityConditions);
        tvSorry = findViewById(R.id.tvSorry);
        recyclerView = findViewById(R.id.recyclerView);
        Gson gson = new Gson();
        Intent intent = getIntent();
        String JSON = intent.getStringExtra("RootObject");
        rootObject = gson.fromJson(JSON, RootObject.class);
    }

    @Override
    public void updateWeather() {
        try{
            //Update Current Weather
            Currently currentWeather = rootObject.getCurrently();
            tvLocation.setText(convertLocation(new LatLng(Double.parseDouble(rootObject.getLatitude()),
                    Double.parseDouble(rootObject.getLongitude()))));

            if(rootObject.getDaily().getData().length != 0){
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new DailyAdapter(rootObject.getDaily().getData(), this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                layoutWeather.setVisibility(View.VISIBLE);
                tvSorry.setVisibility(View.INVISIBLE);
            }else{
                recyclerView.setVisibility(View.INVISIBLE);
                tvSorry.setVisibility(View.VISIBLE);
                tvSorry.setText("Sorry, but " + location +" currently does not have a daily weather report");
            }

            tvTemperature.setText(String.valueOf(convertTemperature(Double.parseDouble(currentWeather.getTemperature()))) + "\u2103");
            tvConditions.setText(currentWeather.getSummary());

            //Setting Summary
            if(Integer.parseInt(currentWeather.getPrecipIntensity()) == 0){
                layoutRain.setVisibility(View.GONE);
            }else{
                layoutRain.setVisibility(View.VISIBLE);
                tvPrecipitation.setText(currentWeather.getPrecipType());
                tvPrecipitationConditions.setText(convertToPercentage(Double.parseDouble(currentWeather.getPrecipIntensity())));
            }
            tvWindConditions.setText(DoubletoInt(Double.parseDouble(currentWeather.getWindSpeed())));
            tvHumidityConditions.setText(convertToPercentage(Double.parseDouble(currentWeather.getHumidity())));
            Date date = new Date(currentWeather.getTime() * 1000);
            convertDate(date);
            cacheData();
        }catch(Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    public int convertTemperature(double temp){
        int tempNew = (int) Math.round((temp - 32)/1.8000);
        return tempNew;
    }

    public String convertToPercentage(double value){
        int convertedValue;
        convertedValue = (int) Math.round(value*100);
        return String.valueOf(convertedValue);
    }

    public String DoubletoInt(double num){
        int newNum = (int) num;
        return String.valueOf(newNum);
    }

    public void convertDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
//        Date date = new Date(time * 1000);
        tvTime.setText(dateFormat.format(date));
    }

    private String convertLocation(LatLng latLng){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        String finalString = cityName+", "+ addresses.get(0).getCountryCode();
        this.location = finalString;
        return finalString;
    }

    @Override
    public void cacheData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(CACHED_DATA, gson.toJson(rootObject));
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWeather();
    }
}
