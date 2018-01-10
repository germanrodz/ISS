package com.blovvme.iss.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.blovvme.iss.R;
import com.blovvme.iss.model.Response;
import com.blovvme.iss.view.adapters.RecyclerViewAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    public static final String TAG = "MainActivityTAG";
    private static final int MY_PERMISSIONS_REQUEST_READ_Location = 123;

    MainActivityPresenter mPresenter;
    @BindView(R.id.RecyclerView)
    android.support.v7.widget.RecyclerView RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Presenter Instantiation
        mPresenter = new MainActivityPresenter();
        mPresenter.attach(this);

        //Check for internet connection
        if (mPresenter.checkInternetConnection()) {
            mPresenter.getPermission();
        }
        else {
            AlertDialog.Builder mAlertBuilder = new AlertDialog.Builder(this);
            mAlertBuilder.setTitle("No Internet Connection");
            mAlertBuilder.setMessage("Please Check Internet Connection and Reopen the App");
            mAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    closeApp();
                }
            });
            mAlertBuilder.show();
        }
    }

    //Close App
    public void closeApp() {
        this.finish();
        System.exit(0);
    }

    //Show Error
    @Override
    public void showError(String MSG) {
        Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showError: " + MSG);
    }

    // Toast MSG
    @Override
    public void showToast(String MSG) {
        Toast.makeText(this, MSG, Toast.LENGTH_SHORT).show();
    }

    //Check for Location Permissions
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Do nothing
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_Location);
            }
        } else {
            mPresenter.getLocationCoord();
        }
    }

    //Get Current Location of Device
    @Override
    public void getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Do Nothing
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    mPresenter.getResults(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                } else {
                    Toast.makeText(MainActivity.this, "Unable to get Location from this device", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showError(e.getMessage());
            }
        });
    }

    //Show Recycler View
    @Override
    public void showRecyclerView(List<Response> responsesList) {
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(responsesList);
        RecyclerView.setAdapter(mAdapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //Check if Network Available
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager mConnectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
