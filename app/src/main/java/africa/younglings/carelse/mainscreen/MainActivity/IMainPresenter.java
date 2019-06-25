package africa.younglings.carelse.mainscreen.MainActivity;

public interface IMainPresenter {

    void checkCityExist(String cityName);
    void setView(IMainView view);
}
