package com.atyourdoorteam.atyourdoor.UI.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import com.atyourdoorteam.atyourdoor.API.APIService;
import com.atyourdoorteam.atyourdoor.API.RetrofitInstance;
import com.atyourdoorteam.atyourdoor.Adapters.SearchViewAdapter;
import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.UI.Activities.ProductsActivity;
import com.atyourdoorteam.atyourdoor.models.Products;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private Toolbar toolbar;
    private TextView toolbar_title;
    private SearchView searchView;
    private RecyclerView searchRecyclerView;
    private SearchViewAdapter searchViewAdapter;
    private ProgressDialog progressDialog;
    private ShimmerFrameLayout shimmerFrameLayout;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("SEARCH PRODUCTS");
        searchView = view.findViewById(R.id.searchView);
        searchView.setVisibility(View.INVISIBLE);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutForSearch);
        shimmerFrameLayout.startShimmer();

        searchRecyclerView = view.findViewById(R.id.searchProductsRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Loading Categories");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getProducts();
            }
        }, 700);


    }

    private void getProducts() {
        APIService apiService = RetrofitInstance.getService();

        Call<List<Products>> getAllProductsForSearch = apiService.getAllProductsForSearch();

        getAllProductsForSearch.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productsList = response.body();

                    ArrayList<Products> productsArrayList = new ArrayList<>();

                    for (int i = 0; i < productsList.size(); i++) {
                        productsArrayList.add(new Products(
                                productsList.get(i).getId(),
                                productsList.get(i).getShopId(),
                                productsList.get(i).getMainCategoryId(),
                                productsList.get(i).getSubCategoryId(),
                                productsList.get(i).getProductImageURL(),
                                productsList.get(i).getProductName(),
                                productsList.get(i).getProductPrice()));
                    }

                    searchViewAdapter = new SearchViewAdapter(productsArrayList, getContext());
                    searchRecyclerView.setAdapter(searchViewAdapter);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    searchView.setVisibility(View.VISIBLE);
//                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
//                progressDialog.dismiss();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }
}