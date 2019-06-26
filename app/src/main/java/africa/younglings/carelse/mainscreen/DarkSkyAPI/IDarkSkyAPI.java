package africa.younglings.carelse.mainscreen.DarkSkyAPI;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDarkSkyAPI {

    @GET("forecast/{key}/{latitude},{longitude}")
    Call<RootObject> getForecast(@Path("key") String apiKey,
                                 @Path("latitude") double latitude,
                                 @Path("longitude") double longitude);
}
