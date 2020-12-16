package com.atyourdoorteam.atyourdoor.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.atyourdoorteam.atyourdoor.API.APIService;
import com.atyourdoorteam.atyourdoor.API.RetrofitInstance;
import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.UI.Activities.LoginActivity;
import com.atyourdoorteam.atyourdoor.models.Categories;
import com.atyourdoorteam.atyourdoor.models.SubCategories;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCategoriesAdapter extends RecyclerView.Adapter<DetailCategoriesAdapter.DetailsCategoriesViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Categories> categoriesList;
    SubCategoriesAdapter subCategoriesAdapter;
    Context context;

    public DetailCategoriesAdapter(List<Categories> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_categories_detail, parent, false);
        DetailsCategoriesViewHolder detailsCategoriesViewHolder = new DetailsCategoriesViewHolder(listItem);
        return detailsCategoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsCategoriesViewHolder holder, int position) {
        final Categories categories = categoriesList.get(position);

        Glide.with(context)
                .load(categories.getCategoriesImage())
                .into(holder.categoryImage);

        holder.categoryName.setText(categories.getCategoryName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.subCategoriesRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        holder.subCategoriesRecyclerView.setLayoutManager(layoutManager);

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.subCategoriesRecyclerView.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.subCategoriesRecyclerView.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ic_down);
                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.subCategoriesRecyclerView.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_up);

                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Loading Sub Categories");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    APIService apiService = RetrofitInstance.getService();
                    Call<List<SubCategories>> subCategoriesCall = apiService.getSubCategoriesList(categories.getId());

                    subCategoriesCall.enqueue(new Callback<List<SubCategories>>() {
                        @Override
                        public void onResponse(Call<List<SubCategories>> call, Response<List<SubCategories>> response) {
                            if (response.isSuccessful()) {
                                List<SubCategories> subCategoriesList = response.body();

                                ArrayList<SubCategories> subCategoriesArrayList = new ArrayList<>();

                                for (int i = 0; i < subCategoriesList.size(); i++) {

                                    subCategoriesArrayList.add(new SubCategories(subCategoriesList.get(i).getId(), subCategoriesList.get(i).getSubCategoryName()));
                                }
                                subCategoriesAdapter = new SubCategoriesAdapter(subCategoriesArrayList, context);
                                progressDialog.dismiss();
                                holder.subCategoriesRecyclerView.setAdapter(subCategoriesAdapter);
                                holder.subCategoriesRecyclerView.setRecycledViewPool(viewPool);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SubCategories>> call, Throwable t) {
                            Log.d("ERROR", t.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(v.getContext(), "Please Try Again ! Serve Side Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.subCategoriesRecyclerView.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.subCategoriesRecyclerView.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ic_down);
                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.subCategoriesRecyclerView.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_up);

                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Loading Sub Categories");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    APIService apiService = RetrofitInstance.getService();
                    Call<List<SubCategories>> subCategoriesCall = apiService.getSubCategoriesList(categories.getId());

                    subCategoriesCall.enqueue(new Callback<List<SubCategories>>() {
                        @Override
                        public void onResponse(Call<List<SubCategories>> call, Response<List<SubCategories>> response) {
                            if (response.isSuccessful()) {
                                List<SubCategories> subCategoriesList = response.body();

                                ArrayList<SubCategories> subCategoriesArrayList = new ArrayList<>();

                                for (int i = 0; i < subCategoriesList.size(); i++) {

                                    subCategoriesArrayList.add(new SubCategories(subCategoriesList.get(i).getId(), subCategoriesList.get(i).getSubCategoryName()));
                                }
                                subCategoriesAdapter = new SubCategoriesAdapter(subCategoriesArrayList, context);
                                progressDialog.dismiss();
                                holder.subCategoriesRecyclerView.setAdapter(subCategoriesAdapter);
                                holder.subCategoriesRecyclerView.setRecycledViewPool(viewPool);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SubCategories>> call, Throwable t) {
                            Log.d("ERROR", t.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(v.getContext(), "Please Try Again ! Serve Side Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class DetailsCategoriesViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryName;
        private RecyclerView subCategoriesRecyclerView;
        private CardView cardView;
        private ImageView arrow;

        public DetailsCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
            subCategoriesRecyclerView = itemView.findViewById(R.id.subCategoryRecyclerView);
            arrow = itemView.findViewById(R.id.arrow);
        }
    }
}
