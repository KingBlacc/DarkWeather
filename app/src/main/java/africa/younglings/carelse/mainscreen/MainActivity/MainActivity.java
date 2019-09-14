package africa.younglings.carelse.mainscreen.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.DarkSkyPresenter;
import africa.younglings.carelse.mainscreen.DarkSkyAPI.IDarkSkyPresenter;
import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;
import africa.younglings.carelse.mainscreen.R;
import africa.younglings.carelse.mainscreen.WeatherActivity.WeatherActivity;

public class MainActivity extends AppCompatActivity implements IMainView{

    private final String GOOGLE_API_KEY ="Enter your google api key";
    private List<Place.Field> fields;
    private AutocompleteSupportFragment autocompleteFragment;
    private IDarkSkyPresenter presenter;
    private final String SHARED_PREFERENCE = "SHARED_PREFS";
    private final String CACHED_DATA = "CACHED_DATA";
    private String cityName;

    //Widgets
    private TextView tvCityName, tvTemperature, tvHistory;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "enter app center secret key",
                Analytics.class, Crashes.class);
        init();
    }

    @Override
    public void init() {
        tvHistory = findViewById(R.id.tvHistory);
        tvCityName = findViewById(R.id.tvCityName);
        tvTemperature = findViewById(R.id.tvCachedTemp);
        cardView = findViewById(R.id.cardView);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), GOOGLE_API_KEY);
        }

         autocompleteFragment = (AutocompleteSupportFragment)
                                getSupportFragmentManager().
                                findFragmentById(R.id.autocomplete_fragment);
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        autocompleteFragment.setPlaceFields(fields);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_LONG).show();
                presenter.getForecast(place.getLatLng());
                cityName = place.toString();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("Error", status.getStatusMessage());
            }
        });
    }

    @Override
    public void switchActivity(RootObject rootObject) {
        Gson gson = new Gson();
        Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
        intent.putExtra("RootObject", gson.toJson(rootObject));
        startActivity(intent);
    }

    @Override
    public void checkCached() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        RootObject object = gson.fromJson(sharedPreferences.getString(CACHED_DATA, null), RootObject.class);
        Log.d("cached", object.toString());
        if (object.getCurrently().getApparentTemperature() != null){
            cardView.setVisibility(View.VISIBLE);
            tvHistory.setVisibility(View.VISIBLE);
            tvTemperature.setText(String.valueOf(convertTemperature(Double.parseDouble(object.getCurrently().getApparentTemperature()))) + "\u2103");
            tvCityName.setText(convertLocation(new LatLng(Double.parseDouble(object.getLatitude()), Double.parseDouble(object.getLongitude()))));
        }
    }

    @Override
    public void showError(String message) {
        Log.d("Error", message);
    }

    public int convertTemperature(double temp){
        int tempNew = (int) Math.round((temp - 32)/1.8000);
        return tempNew;
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
        return finalString;
    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter = new DarkSkyPresenter();
        presenter.setView(this);
        checkCached();
    }
}
