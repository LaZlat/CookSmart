package com.example.smartcook;

import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.deanwild.flowtextview.FlowTextView;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {


    private ArrayList<Dish> dDishList;

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        public ImageView dImageView;
        public TextView dTextView;
        public FlowTextView ftw;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dImageView = itemView.findViewById(R.id.dishImageView);
            dTextView = itemView.findViewById(R.id.dishTextView);
            ftw = itemView.findViewById(R.id.ftv);
            ftw.setTextSize(40);
        }
    }

    public DishAdapter(ArrayList<Dish> dishArrayList){
        this.dDishList = dishArrayList;
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
            DishViewHolder dvh = new DishViewHolder(v);
            return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish currentItem = dDishList.get(position);

        Uri path = Uri.parse(currentItem.getImage());
        String sPath = path.toString();
        Picasso.get().load(sPath).into(holder.dImageView);

        holder.dTextView.setText(dDishList.get(position).getName());
//        Spanned dText = Html.fromHtml(dDishList.get(position).getDescription());
        Spanned dText = Html.fromHtml("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        holder.ftw.setText(dText);

    }

    @Override
    public int getItemCount() {
        return dDishList.size();
    }
}
