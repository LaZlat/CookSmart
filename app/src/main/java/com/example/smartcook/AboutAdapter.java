package com.example.smartcook;

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


    public static class AboutViewHolder extends RecyclerView.ViewHolder{
        public ImageView aImage;
        public TextView aText;

        public AboutViewHolder(@NonNull View itemView) {
            super(itemView);
            aImage = itemView.findViewById(R.id.ingridientImageView);
            aText = itemView.findViewById(R.id.ingridientTextView);
        }
    }

    public AboutAdapter(ArrayList<Product> aProductList){
        this.aProductList = aProductList;
    }

    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_item, parent, false);
        AboutViewHolder avh = new AboutViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
        Product currentItem = aProductList.get(position);

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
