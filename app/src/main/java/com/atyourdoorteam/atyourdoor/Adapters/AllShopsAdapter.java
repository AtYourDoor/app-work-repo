package com.atyourdoorteam.atyourdoor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.atyourdoorteam.atyourdoor.R;
import com.atyourdoorteam.atyourdoor.UI.Activities.ShopCategoriesActivity;
import com.atyourdoorteam.atyourdoor.models.Shop;
import com.bumptech.glide.Glide;

import java.util.List;

public class AllShopsAdapter extends RecyclerView.Adapter<AllShopsAdapter.AllShopsViewHolder> {

    private List<Shop> shopList;
    private Context context;
    double userlatitude;
    double userlongitude;


    public AllShopsAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    public AllShopsAdapter(List<Shop> shopList, Context context, double userlatitude, double userlongitude) {
        this.shopList = shopList;
        this.context = context;
        this.userlatitude = userlatitude;
        this.userlongitude = userlongitude;
    }

    @NonNull
    @Override
    public AllShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_shop, parent, false);
        AllShopsViewHolder allShopsViewHolder = new AllShopsViewHolder(listItem);
        return allShopsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllShopsViewHolder holder, int position) {
        final Shop shop = shopList.get(position);

        Glide.with(context).load(shop.getShopImageURL()).into(holder.shopsImage);
        holder.shopsTitle.setText(shop.getShopName());
        holder.shopsAddress.setText(shop.getShopAddress());

        double latitude = shop.getShopLocation().get(0);
        double longitude = shop.getShopLocation().get(1);

        holder.distance.setText(Math.round(distance(userlatitude, latitude, userlongitude, longitude) * 100.0) / 100.0 + " Kms Far");


        holder.shopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShopCategoriesActivity.class);
                intent.putExtra("SHOPNAME", shop.getShopName());
                intent.putExtra("SHOPID", shop.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }

    public static class AllShopsViewHolder extends RecyclerView.ViewHolder {
        public ImageView shopsImage;
        public TextView shopsTitle;
        public TextView shopsAddress;
        public CardView shopCardView;
        public TextView distance;

        public AllShopsViewHolder(@NonNull View itemView) {
            super(itemView);


            shopsImage = itemView.findViewById(R.id.shopImage);
            shopsTitle = itemView.findViewById(R.id.shopName);
            shopsAddress = itemView.findViewById(R.id.shopAddress);
            shopCardView = itemView.findViewById(R.id.shopCardView);
            distance = itemView.findViewById(R.id.shopDistance);
        }
    }
}
