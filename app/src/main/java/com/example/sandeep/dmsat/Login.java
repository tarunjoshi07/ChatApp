package com.example.sandeep.dmsat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login,signin;
        final EditText id,password;
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signin=findViewById(R.id.signin);
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("DMSAT");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().isEmpty()||password.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "incomplete!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String xid=id.getText().toString();
                    final String xpassword=password.getText().toString();
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(xid).exists()){
                                Member flag=dataSnapshot.child(xid).getValue(Member.class);
                                if(flag.getPassword().equals(xpassword))
                                {
                                 Intent intent=new Intent(Login.this,Account.class);
                                 startActivity(intent);
                                    SharedPreferences preferences=getSharedPreferences("tarun",MODE_PRIVATE);
                                    preferences.edit().putString("key",xid).apply();
                                }
                                else {
                                    Toast.makeText(Login.this, "wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Login.this, "wrong id!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Signin.class);
                startActivity(intent);
            }
        });
    }
}
