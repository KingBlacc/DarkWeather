package africa.younglings.carelse.mainscreen.DarkSkyAPI;

import com.google.android.gms.maps.model.LatLng;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;
import africa.younglings.carelse.mainscreen.MainActivity.IMainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DarkSkyPresenter implements IDarkSkyPresenter {

    IMainView view;
    private final String DARK_SKY_KEY = "f0c4fdeec618a4da3c0adc2702cc73bf";
    @Override
    public void getForecast(LatLng latLng) {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.darksky.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        IDarkSkyAPI darkSkyAPI = retrofit.create(IDarkSkyAPI.class);

        Call<RootObject> callForecast = darkSkyAPI.getForecast(DARK_SKY_KEY, latLng.latitude, latLng.longitude);

        callForecast.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                view.switchActivity(response.body());
            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public void setView(IMainView view) {
        this.view = view;
    }
}
