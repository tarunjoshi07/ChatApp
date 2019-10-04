package com.example.sandeep.dmsat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ReqAdapter extends RecyclerView.Adapter<ReqAdapter.Holder> {
     private ArrayList<String> data;
     private Info info;
     public ReqAdapter(Info info,ArrayList<String> data) {
         this.data=data;
         this.info=info;
     }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.request,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
      String xid=data.get(i);
      holder.id.setText(xid);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class Holder extends RecyclerView.ViewHolder{
        TextView id;
        Button accept, reject;

        public Holder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.reqid);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String value=data.get(position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    info.onClickaccept(value);
                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String value=data.get(position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    info.onClickreject(value);
                }
            });

        }
    }
    public interface  Info{
         void onClickaccept(String data);
         void onClickreject(String data);
    }
}
