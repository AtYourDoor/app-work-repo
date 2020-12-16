package com.atyourdoorteam.atyourdoor.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.atyourdoorteam.atyourdoor.API.APIService;
import com.atyourdoorteam.atyourdoor.API.RetrofitInstance;
import com.atyourdoorteam.atyourdoor.Adapters.AllShopsAdapter;
import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.models.Shop;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllShopsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_title;
    private RecyclerView allShopsRecyclerView;
    private AllShopsAdapter allShopsAdapter;
    private ProgressDialog progressDialog;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shops);

        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("ALL SHOPS OF YOUR CITY");

        shimmerFrameLayout = findViewById(R.id.shimmerLayoutForAllShops);
        shimmerFrameLayout.startShimmer();

        allShopsRecyclerView = findViewById(R.id.shopsActivityRecyclerView);
        allShopsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getShops();
            }
        }, 700);


    }

    private void getShops() {
        APIService apiService = RetrofitInstance.getService();
        Call<List<Shop>> shopCall = apiService.getShopsList();
//        progressDialog = new ProgressDialog(AllShopsActivity.this);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Loading Shops Of Your City");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        shopCall.enqueue(new Callback<List<Shop>>() {
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

                    allShopsAdapter = new AllShopsAdapter(shopArrayList, AllShopsActivity.this);
                    allShopsRecyclerView.setAdapter(allShopsAdapter);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }
}