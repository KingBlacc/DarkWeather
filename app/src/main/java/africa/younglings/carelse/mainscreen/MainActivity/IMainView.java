package africa.younglings.carelse.mainscreen.MainActivity;

import africa.younglings.carelse.mainscreen.DarkSkyAPI.ModelClass.RootObject;

public interface IMainView {

    void init();
    void showError(String message);
    void switchActivity(RootObject rootObject);
    void checkCached();
}
