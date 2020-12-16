package com.atyourdoorteam.atyourdoor.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.IntentSender;
import android.os.Bundle;
import android.view.MenuItem;

import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.UI.Fragments.GroceyListFragment;
import com.atyourdoorteam.atyourdoor.UI.Fragments.SearchFragment;
import com.atyourdoorteam.atyourdoor.UI.Fragments.ShoppingCartFragment;
import com.atyourdoorteam.atyourdoor.UI.Fragments.AccountFragment;
import com.atyourdoorteam.atyourdoor.UI.Fragments.HomeFragment;
import com.atyourdoorteam.atyourdoor.UI.Fragments.MapsFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
//    private final int REQUEST_CHECK_CODE = 8989;
//    private LocationSettingsRequest.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        // Location Dialog box permission
//        LocationRequest request = new LocationRequest()
//                .setFastestInterval(1500)
//                .setInterval(3000)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(request);
//
//        Task<LocationSettingsResponse> result =
//                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//                try {
//                    task.getResult(ApiException.class);
//                } catch (ApiException e) {
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes
//                                .RESOLUTION_REQUIRED:
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_CODE);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            } catch (ClassCastException ex) {
//
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
//                            break;
//                        }
//
//                    }
//                }
//            }
//        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_map:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_shoppingcart:
                            selectedFragment = new ShoppingCartFragment();
                            break;
                        case R.id.nav_grocery_list:
                            selectedFragment = new GroceyListFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}