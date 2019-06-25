package africa.younglings.carelse.mainscreen.MainActivity;

public class MainPresenter implements IMainPresenter {

    IMainView view;

    @Override
    public void checkCityExist(String cityName) {

    }

    @Override
    public void setView(IMainView view) {
        this.view = view;
    }
}
