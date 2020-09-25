package com.example.sixthclass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {

    private List<Model> richList;
    private ClickInterface clickInterface;

    public MyAdapter(List<Model> richList,ClickInterface clickInterface) {

        this.richList = richList;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.circleImageView.setImageResource(richList.get(position).getPosition());
        holder.textViewName.setText(richList.get(position).getName());
        holder.textViewTop.setText(richList.get(position).getTop());
    }

    @Override
    public int getItemCount() {

        return richList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView textViewName;
        private TextView textViewTop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.circleImageId);
            textViewName = itemView.findViewById(R.id.singleNameId);
            textViewTop = itemView.findViewById(R.id.singleTopId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
