package com.example.sandeep.dmsat;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Friends extends AppCompatActivity implements ReqAdapter.Info {
    String myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        RecyclerView recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final EditText id = findViewById(R.id.friendid);
        Button add = findViewById(R.id.makereq);
        SharedPreferences preferences = getSharedPreferences("tarun", MODE_PRIVATE);
        final String myid = preferences.getString("key", "");
        final ArrayList<String> record = new ArrayList<>();
        final ReqAdapter adapter = new ReqAdapter(this, record);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DMSAT");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty()) {
                    Toast.makeText(Friends.this, "enter ID", Toast.LENGTH_SHORT).show();
                } else {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        String friendid = id.getText().toString();

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(friendid).exists()) {
                                if (myid.isEmpty()) {
                                    Toast.makeText(Friends.this, "no cache", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (dataSnapshot.child(friendid).child("request").child(myid).exists()) {
                                        Toast.makeText(Friends.this, "request already sent", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(dataSnapshot.child(myid).child("request").child(friendid).exists()){
                                        Toast.makeText(Friends.this, friendid+"already sent you request", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(dataSnapshot.child(myid).child("friends").child(friendid).exists()){
                                        Toast.makeText(Friends.this, "you are already friends", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(myid.equals(friendid)){
                                    }
                                    else {
                                        reference.child(friendid).child("request").child(myid).setValue(myid);
                                        reference.child(myid).child("myrequest").child(friendid).setValue(friendid);
                                        Toast.makeText(Friends.this, "request sent", Toast.LENGTH_SHORT).show();
                                        id.setText(null);
                                    }
                                }
                            } else {
                                Toast.makeText(Friends.this, "wrong id!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                if (dataSnapshot.child(myid).child("request").hasChildren()) {
                    Map<String, Object> objectHashMap = dataSnapshot.child(myid).child("request").getValue(objectsGTypeInd);
                    ArrayList<Object> objectArrayList = new ArrayList<Object>(objectHashMap.values());
                    for (int i = 0; i < objectArrayList.size(); i++) {
                        String value = objectArrayList.get(i).toString();
                        record.add(value);
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickaccept(String id) {
        SharedPreferences preferences = getSharedPreferences("tarun", MODE_PRIVATE);
        final String myid = preferences.getString("key", "");
        DatabaseReference myref=FirebaseDatabase.getInstance().getReference("DMSAT");
        myref.child(myid).child("friends").child(id).setValue(id);
        myref.child(myid).child("request").child(id).setValue(null);
        myref.child(id).child("friends").child(myid).setValue(myid);
        myref.child(id).child("myrequest").child(myid).setValue(null);
    }

    @Override
    public void onClickreject(String id) {
        SharedPreferences preferences = getSharedPreferences("tarun", MODE_PRIVATE);
        final String myid = preferences.getString("key", "");
        DatabaseReference myref=FirebaseDatabase.getInstance().getReference("DMSAT");
        myref.child(myid).child("request").child(id).setValue(null);
        myref.child(id).child("myrequest").child(myid).setValue(null);
    }
}
