package com.example.sandeep.dmsat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Calendar;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference;
    String friendid;
    String myid;
    Toolbar toolbar;
    ChatAdaptar adapter;
    ArrayList<SavedClass> record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        friendid=intent.getStringExtra("id");
        final EditText message=findViewById(R.id.Text);
        Button send=findViewById(R.id.send);
        final RecyclerView recyclerView = findViewById(R.id.mview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences preferences = getSharedPreferences("tarun", MODE_PRIVATE);
        record = new ArrayList<>();
        adapter = new ChatAdaptar( record);
        myid = preferences.getString("key", "");
        databaseReference=FirebaseDatabase.getInstance().getReference("DMSAT");
        databaseReference.child(myid).child("chat").child(friendid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                record.add(dataSnapshot.getValue(SavedClass.class));
                adapter.notifyItemInserted(record.size()-1);
                recyclerView.smoothScrollToPosition(record.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=message.getText().toString();
                message.setText(null);
                if(text.isEmpty()){}
                else
                {
                    Long time =Calendar.getInstance().getTimeInMillis();
                    String stime=time.toString();
                    SavedClass flag1=new SavedClass(text,0,time);
                   databaseReference.child(myid).child("chat").child(friendid).child(stime).setValue(flag1);
                    SavedClass flag2=new SavedClass(text,1,time);
                   databaseReference.child(friendid).child("chat").child(myid).child(stime).setValue(flag2);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuchat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        record.clear();
        adapter.notifyDataSetChanged();
        databaseReference.child(myid).child("chat").child(friendid).setValue(null);
        return super.onOptionsItemSelected(item);
    }
}
