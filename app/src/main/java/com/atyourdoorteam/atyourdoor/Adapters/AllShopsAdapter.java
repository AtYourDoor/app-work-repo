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

    public AllShopsAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
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

    public static class AllShopsViewHolder extends RecyclerView.ViewHolder {
        public ImageView shopsImage;
        public TextView shopsTitle;
        public TextView shopsAddress;
        public CardView shopCardView;

        public AllShopsViewHolder(@NonNull View itemView) {
            super(itemView);


            shopsImage = itemView.findViewById(R.id.shopImage);
            shopsTitle = itemView.findViewById(R.id.shopName);
            shopsAddress = itemView.findViewById(R.id.shopAddress);
            shopCardView = itemView.findViewById(R.id.shopCardView);
        }
    }
}
