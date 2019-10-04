package com.example.sandeep.dmsat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdaptar extends RecyclerView.Adapter<ChatAdaptar.ViewHolder>{
    public ArrayList<SavedClass> messages;
    public  ChatAdaptar(ArrayList<SavedClass> messages)

    {
        this.messages=messages;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.message,viewGroup,false);
        return new ChatAdaptar.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String flag=messages.get(i).getMsj();
        viewHolder.text1.setVisibility(View.INVISIBLE);
        viewHolder.text2.setVisibility(View.INVISIBLE);
        if(messages.get(i).getCode()==1) {
            viewHolder.text1.setVisibility(View.VISIBLE);
            viewHolder.text1.setText(flag);
        }
        else if (messages.get(i).getCode()==0)
        {
            viewHolder.text2.setVisibility(View.VISIBLE);
            viewHolder.text2.setText(flag);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.textr);
            text2=itemView.findViewById(R.id.textl);
        }
    }
}
