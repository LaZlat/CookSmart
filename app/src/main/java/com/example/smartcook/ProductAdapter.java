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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> mProductList;
    private OnItemClickListiner mListiner;

    public interface OnItemClickListiner {
        void onItemClick(int position);
    }

    public void setOnItemClickListiner(OnItemClickListiner listiner){
        mListiner = listiner;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;

        public ProductViewHolder(@NonNull View itemView, final OnItemClickListiner listiner) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listiner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listiner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ProductAdapter(ArrayList<Product> productList){
        mProductList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v,mListiner);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentItem = mProductList.get(position);

        Uri path = Uri.parse(currentItem.getImage());
        String sPath = path.toString();
        Picasso.get().load(sPath).into(holder.mImageView);

        holder.mTextView.setText(currentItem.getName());



    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
