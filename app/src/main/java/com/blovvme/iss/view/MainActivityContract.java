package com.blovvme.iss.view;

import com.blovvme.iss.BasePresenter;
import com.blovvme.iss.BaseView;
import com.blovvme.iss.model.Response;

import java.util.List;

/**
 * Created by Blovvme on 1/9/18.
 */

public interface MainActivityContract {
    interface View extends BaseView {
        void checkLocationPermission();
        void getLocation();
        void showRecyclerView(List<Response> responsesList);
        boolean isNetworkAvailable();
    }
    interface Presenter extends BasePresenter<View> {
        void getPermission();
        void getLocationCoord();
        void getResults(String Lat, String Long);
        boolean checkInternetConnection();
    }
}
