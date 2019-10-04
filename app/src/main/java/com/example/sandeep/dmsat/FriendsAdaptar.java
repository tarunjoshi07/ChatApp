package com.example.sandeep.dmsat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsAdaptar extends RecyclerView.Adapter<FriendsAdaptar.Holder> {
    ArrayList<String> data;
    Info info;
    public FriendsAdaptar(Info info,ArrayList<String> data) {
        this.data = data;
        this.info=info;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.myfriends,viewGroup,false);
        return new FriendsAdaptar.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
     String id= data.get(i);
     holder.friend.setText(id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView friend;
        Button dltchat;
        View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.myfriends);
            dltchat=itemView.findViewById(R.id.dltchat);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    info.onClickInfo(data.get(position));
                }
            });
            friend = itemView.findViewById(R.id.dost);
        }
    }
        public interface  Info {
            void onClickInfo(String data);
        }
}