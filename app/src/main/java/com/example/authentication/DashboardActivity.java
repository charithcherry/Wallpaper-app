package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {


    private Button logout,setwall,upload,imagesview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        logout=findViewById(R.id.logout);
        setwall=findViewById(R.id.SetWall);
        upload=findViewById(R.id.upload);
        imagesview=findViewById(R.id.ImagesView);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,Setwallpaper.class);
                startActivity(intent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,Uploadingimg.class);
                startActivity(intent);
            }
        });
        imagesview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,ImagesActivity.class);
                startActivity(intent);
            }
        });



    }

}
