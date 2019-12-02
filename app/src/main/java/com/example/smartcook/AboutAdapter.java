package com.example.smartcook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {

    private ArrayList<Product> aProductList;
    private OnItemClickListiner aListiner;

    public interface OnItemClickListiner{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListiner(OnItemClickListiner listiner){
        aListiner = listiner;
    }


    public static class AboutViewHolder extends RecyclerView.ViewHolder{
        public ImageView aImage;
        public TextView aText;
        public ImageView aboutCheck;

        public AboutViewHolder(@NonNull View itemView, final OnItemClickListiner listiner) {
            super(itemView);
            aImage = itemView.findViewById(R.id.ingridientImageView);
            aText = itemView.findViewById(R.id.ingridientTextView);
            aboutCheck = itemView.findViewById(R.id.aboutCheck);

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

    public AboutAdapter(ArrayList<Product> aProductList){
        this.aProductList = aProductList;
    }

    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_item, parent, false);
        AboutViewHolder avh = new AboutViewHolder(v, aListiner);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        Product currentItem = aProductList.get(position);

        if(aProductList.get(position).isChoose() == true){
            holder.aboutCheck.setVisibility(View.VISIBLE);
        }
        else {
            holder.aboutCheck.setVisibility(View.INVISIBLE);
        }
        Uri path = Uri.parse(currentItem.getImage());
        String sPath = path.toString();
        Picasso.get().load(sPath).into(holder.aImage);


        holder.aText.setText(aProductList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return aProductList.size();
    }
}
