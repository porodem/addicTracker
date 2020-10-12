package com.porodem.activitysample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter /*extends RecyclerView.Adapter<MyAdapter.IventHolder>*/ {

    String data1[], data2[];
    int images[];
    Context context;

    private List<Ivent> mIvents;

    public MyAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }
/*
    @NonNull
    @Override
    public IventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new IventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IventHolder holder, final int position) {
        holder.txtDesc.setText(data2[position]);
        holder.txtTile.setText(data1[position]);
        holder.imgView.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("img", images[position]);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }*/

    /*public class IventHolder extends RecyclerView.ViewHolder{

        TextView txtTile, txtDesc;
        ImageView imgView;
        ConstraintLayout mainLayout;

        public IventHolder(@NonNull View itemView) {
            super(itemView);

            txtTile = itemView.findViewById(R.id.txtMeal);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            imgView = itemView.findViewById(R.id.myImg);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }*/
}
