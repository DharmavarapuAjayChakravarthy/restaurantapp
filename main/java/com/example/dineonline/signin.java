package com.example.dineonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import Common.Common;
import Model.User;

public class signin extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtPassword=(MaterialEditText)findViewById(R.id.editPassword);
        edtPhone=(MaterialEditText)findViewById(R.id.editPhone);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(signin.this);
                mDialog.setMessage("Please Waiting..");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            //if (edtPassword.getText().toString().equals(user.getPassword()))
                            //assert user != null;
                            if(user.getPassword().equals((edtPassword.getText().toString())))
                            {
                                //Toast.makeText(signin.this, "Sign In Successful !", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(signin.this,home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(signin.this, "Wrong Password !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                                mDialog.dismiss();
                                Toast.makeText(signin.this, "User not Exist !!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
