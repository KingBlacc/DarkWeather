package africa.younglings.carelse.mainscreen.DarkSkyAPI;

import com.google.android.gms.maps.model.LatLng;

import africa.younglings.carelse.mainscreen.MainActivity.IMainView;

public interface IDarkSkyPresenter {

    void getForecast(LatLng latLng);
    void setView(IMainView view);

}
