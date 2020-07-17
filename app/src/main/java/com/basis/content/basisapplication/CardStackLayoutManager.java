package com.basis.content.basisapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardStackLayoutManager extends RecyclerView.Adapter<CardStackLayoutManager.ViewHolder> {

    private ArrayList<String> mContent = new ArrayList<>();
    private Context mContext;

    public CardStackLayoutManager(ArrayList<String> mContent, Context mContext) {
        this.mContent = mContent;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtContent.setText(mContent.get(position));

    }

    @Override
    public int getItemCount() {
      return   mContent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtContent;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.content_txt);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
