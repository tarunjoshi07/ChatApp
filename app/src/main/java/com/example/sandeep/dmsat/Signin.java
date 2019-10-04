package com.example.sandeep.dmsat;

import android.content.Intent;
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

public class Signin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        final EditText fname,lname,login,password;
        fname=findViewById(R.id.firstname);
        lname=findViewById(R.id.lastname);
        login=findViewById(R.id.loginid);
        password=findViewById(R.id.spassword);
        Button save=findViewById(R.id.join);
        final DatabaseReference reference =FirebaseDatabase.getInstance().getReference("DMSAT");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || password.getText().toString().isEmpty() || login.getText().toString().isEmpty()) {
                    Toast.makeText(Signin.this, "incomplete form", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String xfname = fname.getText().toString();
                    final String xlname = lname.getText().toString();
                    final String xid = login.getText().toString();
                    final String xpassword = password.getText().toString();
                    final Member member = new Member(xfname, xlname, xid, xpassword);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(xid).exists()) {
                                Toast.makeText(Signin.this, "Id already taken", Toast.LENGTH_SHORT).show();
                            } else {
                                reference.child(xid).setValue(member);
                                Intent intent = new Intent(Signin.this, Login.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Signin.this, "Error!!!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
