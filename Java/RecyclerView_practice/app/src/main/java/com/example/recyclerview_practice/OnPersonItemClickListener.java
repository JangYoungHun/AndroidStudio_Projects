package com.example.recyclerview_practice;

import android.view.View;

public interface OnPersonItemClickListener {

    public void onItemClick(MyAdapter.MyViewHolder viewholder, View view, int position);

}
