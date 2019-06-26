package africa.younglings.carelse.mainscreen.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.DarkSkyPresenter;
import africa.younglings.carelse.mainscreen.DarkSkyAPI.IDarkSkyPresenter;
import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;
import africa.younglings.carelse.mainscreen.R;
import africa.younglings.carelse.mainscreen.WeatherActivity.WeatherActivity;

public class MainActivity extends AppCompatActivity implements IMainView{

    private final String GOOGLE_API_KEY ="AIzaSyCaJh5BI-mic34LOKtkYqAUM0lHoL8hLqg";
    private List<Place.Field> fields;
    private AutocompleteSupportFragment autocompleteFragment;
    private IDarkSkyPresenter presenter;
    private final String SHARED_PREFERENCE = "SHARED_PREFS";
    private final String CACHED_DATA = "CACHED_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init() {
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
                Log.d("Info", place.toString());
                presenter.getForecast(place.getLatLng());
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CACHED_DATA, gson.toJson(rootObject));
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
        intent.putExtra("RootObject", gson.toJson(rootObject));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Log.d("Error", message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new DarkSkyPresenter();
        presenter.setView(this);
    }
}
