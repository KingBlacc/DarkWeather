package africa.younglings.carelse.mainscreen.WeatherActivity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

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
    private RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    @Override
    public void init() {

    }

    @Override
    public void updateWeather() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
