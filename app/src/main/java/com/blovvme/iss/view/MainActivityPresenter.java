package com.blovvme.iss.view;

import android.view.View;

import com.blovvme.iss.datasource.RetrofitHelper;
import com.blovvme.iss.model.ResultClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Blovvme on 1/9/18.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View mView;

    @Override
    public void attach(MainActivityContract.View view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }

    @Override
    public void getPermission() {
        mView.checkLocationPermission();
    }

    @Override
    public void getLocationCoord() {
        mView.getLocation();
    }

    @Override
    public void getResults(String Lat, String Long) {
        retrofit2.Call<ResultClass> getResult = RetrofitHelper.getResults(Lat,Long);
        getResult.enqueue(new Callback<ResultClass>() {
            @Override
            public void onResponse(Call<ResultClass> call, Response<ResultClass> response) {
                mView.showRecyclerView(response.body().getResponse());
            }

            @Override
            public void onFailure(Call<ResultClass> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }

    @Override
    public boolean checkInternetConnection() {
        return mView.isNetworkAvailable();
    }
}
