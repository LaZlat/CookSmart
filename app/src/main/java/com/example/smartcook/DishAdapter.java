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


public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {


    private ArrayList<Dish> dDishList = new ArrayList<>();
    private OnItemClickListiner dListiner;

    public interface OnItemClickListiner {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListiner(OnItemClickListiner listiner){
        dListiner = listiner;
    }


    public static class DishViewHolder extends RecyclerView.ViewHolder {
        public ImageView dImageView;
        public TextView dTextView;
//        public FlowTextView ftw;

        public DishViewHolder(@NonNull View itemView, final OnItemClickListiner listiner) {
            super(itemView);
            dImageView = itemView.findViewById(R.id.dishImageView);
            dTextView = itemView.findViewById(R.id.dishTextView);
//            ftw = itemView.findViewById(R.id.ftv);
//            ftw.setTextSize(40);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listiner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listiner.onItemClick(v,position);
                        }
                    }
                }
            });
        }
    }

    public DishAdapter(ArrayList<Dish> dishArrayList){
//        for (Dish x : dishArrayList) {
//            if(x.isChoose() == true) {
//                this.dDishList.add(x);
////            }
////        }
        this.dDishList = dishArrayList;
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
            DishViewHolder dvh = new DishViewHolder(v, dListiner);
            return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish currentItem = dDishList.get(position);

        Uri path = Uri.parse(currentItem.getImage());
        String sPath = path.toString();
        Picasso.get().load(sPath).centerCrop().resize(400,300).error(R.drawable.ic_error).into(holder.dImageView);


        holder.dTextView.setText(dDishList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return dDishList.size();
    }
}
