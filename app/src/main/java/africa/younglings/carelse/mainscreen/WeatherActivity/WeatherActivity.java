package africa.younglings.carelse.mainscreen.WeatherActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import africa.younglings.carelse.mainscreen.R;

public class WeatherActivity extends AppCompatActivity implements IWeatherView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    @Override
    public void init() {

    }
}
