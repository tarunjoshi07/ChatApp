package com.example.sandeep.dmsat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    Button save;
    EditText currentPassword,newPassword,check;
    String myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final SharedPreferences preferences=getSharedPreferences("tarun",MODE_PRIVATE);
        myid=preferences.getString("key","");
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("DMSAT");
        currentPassword=findViewById(R.id.currentpassword);
        newPassword=findViewById(R.id.newpassword);
        check=findViewById(R.id.check);
        save=findViewById(R.id.savenewpassword);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String xcurrentpassword=currentPassword.getText().toString();
                final String xnewpassword=newPassword.getText().toString();
                final String xcheck=check.getText().toString();
                if(xcheck.isEmpty()&& xnewpassword.isEmpty()&& xcurrentpassword.isEmpty())
                {
                    Toast.makeText(ChangePassword.this, "Incomplete Form", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Member flag=dataSnapshot.child(myid).getValue(Member.class);
                            if(flag.getPassword().equals(xcurrentpassword))
                            {
                                if(xnewpassword.equals(xcheck))
                                {
                                    reference.child(myid).child("password").setValue(xnewpassword);
                                    Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                    preferences.edit().putString("key","").apply();
                                    Intent intent=new Intent(ChangePassword.this,Login.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(ChangePassword.this, "new password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(ChangePassword.this, "Wrong Old Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                
            }
        });
    }
}
