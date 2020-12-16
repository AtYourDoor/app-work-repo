package com.atyourdoorteam.atyourdoor.UI.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyourdoorteam.atyourdoor.API.APIService;
import com.atyourdoorteam.atyourdoor.API.RetrofitInstance;
import com.atyourdoorteam.atyourdoor.Adapters.CategoriesAdapter;
import com.atyourdoorteam.atyourdoor.Adapters.ShopsAdapter;
import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.UI.Activities.AllCategoriesActivity;
import com.atyourdoorteam.atyourdoor.UI.Activities.AllShopsActivity;
import com.atyourdoorteam.atyourdoor.models.Categories;
import com.atyourdoorteam.atyourdoor.models.Shop;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter shopsAdapter;
    private CategoriesAdapter categoriesAdapter;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private TextView viewAllCategories, viewAllShops;
    private GoogleMap googleMap;
    private SearchView searchView;
    private ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayout2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("AT YOUR DOOR");
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout2 = view.findViewById(R.id.shimmerLayout2);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout2.startShimmer();
        categoriesRecyclerView = view.findViewById(R.id.categoriesReyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        shopsRecyclerView = view.findViewById(R.id.shopsRecyclerView);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewAllCategories = view.findViewById(R.id.viewAllCategories);
        viewAllShops = view.findViewById(R.id.viewAllShops);
//        searchView = view.findViewById(R.id.searchView);


        viewAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCategory = new Intent(getContext(), AllCategoriesActivity.class);
                startActivity(openCategory);
            }
        });
        viewAllShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShops = new Intent(getContext(), AllShopsActivity.class);
                startActivity(openShops);
            }
        });
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Loading Categories And Shops");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getCategories();
                getShops();
            }
        }, 700);

//        getCategories();
//        getShops();


    }


    private void getCategories() {
        APIService apiService = RetrofitInstance.getService();

        Call<List<Categories>> categoriesList = apiService.getCategoriesList();

        categoriesList.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                List<Categories> categoriesList1 = response.body();

                ArrayList<Categories> categoriesArrayList = new ArrayList<>();

                for (int i = 0; i < categoriesList1.size(); i++) {

                    categoriesArrayList.add(new Categories(categoriesList1.get(i).getCategoryName(),
                            categoriesList1.get(i).getCategoriesImage()));
                }

                categoriesAdapter = new CategoriesAdapter(categoriesArrayList, getContext());
                categoriesRecyclerView.setAdapter(categoriesAdapter);

            }


            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });
    }

    private void getShops() {
        APIService apiService = RetrofitInstance.getService();

        Call<List<Shop>> shopsList = apiService.getShopsList();

        shopsList.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if (response.isSuccessful()) {
                    List<Shop> shopList = response.body();

                    ArrayList<Shop> shopArrayList = new ArrayList<>();

                    for (int i = 0; i < shopList.size(); i++) {
                        shopArrayList.add(new Shop(
                                shopList.get(i).getId(),
                                shopList.get(i).getShopName(),
                                shopList.get(i).getShopImageURL(),
                                shopList.get(i).getShopAddress()));
                    }

                    shopsAdapter = new ShopsAdapter(shopArrayList, getContext());
                    shopsRecyclerView.setAdapter(shopsAdapter);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout2.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout2.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {

            }
        });
    }
}
