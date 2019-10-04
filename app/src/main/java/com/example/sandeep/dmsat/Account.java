package com.example.sandeep.dmsat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Account extends AppCompatActivity implements FriendsAdaptar.Info {
    String myid;
    DrawerLayout drawerLayout;
    View headerview;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setup();
        navigationView=findViewById(R.id.nav);
        headerview=navigationView.getHeaderView(0);
        SharedPreferences preferences = getSharedPreferences("tarun", MODE_PRIVATE);
        myid = preferences.getString("key", "");
        RecyclerView recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> record = new ArrayList<>();
        final FriendsAdaptar adapter = new FriendsAdaptar(this, record);
        TextView showid=headerview.findViewById(R.id.showid);
        showid.setText("ID:"+myid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DMSAT");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.about:
                        Intent intent = new Intent(Account.this, About.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START,false);
                        break;
                    case R.id.menuaddfriends:
                        Intent yintent = new Intent(Account.this, Friends.class);
                        startActivity(yintent);
                        drawerLayout.closeDrawer(GravityCompat.START,false);
                        break;
                    case R.id.menulogout:
                        SharedPreferences preferences=getSharedPreferences("tarun",MODE_PRIVATE);
                        preferences.edit().putString("key","").apply();
                        Intent xintent = new Intent(Account.this, Login.class);
                        drawerLayout.closeDrawer(GravityCompat.START,false);
                        startActivity(xintent);
                        break;
                    case R.id.cpassword:
                        Intent cintent = new Intent(Account.this, ChangePassword.class);
                        startActivity(cintent);
                        drawerLayout.closeDrawer(GravityCompat.START,false);
                        break;
                        case R.id.share:
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "Downlode DMSAT app from :    https://drive.google.com/file/d/1nn9ZrjB3CVoHPuMsEPrHTqWfVvcH7LJ5/view?usp=sharing");
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                            drawerLayout.closeDrawer(GravityCompat.START,false);
                }
                return false;
            }
        });
        reference.child(myid).child("friends").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                      record.add(dataSnapshot.getValue().toString());
                      adapter.notifyDataSetChanged();
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
    }
    @Override
    public void onClickInfo(String id) {
        Intent intent = new Intent(Account.this, Chat.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setup() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

}
