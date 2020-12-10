package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
public class Setwallpaper extends AppCompatActivity {

    private GridView gridView;
    private ImageView currentWallpaper;
    private WallpaperManager wallpaperManager;
    private Drawable drawable;
    private final int REQUEST_READ_EXTERNAL_STORAGE = 1;


    Integer[] myImageArray = {
            R.drawable.b1, R.drawable.b2,
            R.drawable.b4, R.drawable.b3,
            R.drawable.b5,R.drawable.b6,
            R.drawable.b7,R.drawable.b8,
            R.drawable.b9


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper);

        gridView = findViewById(R.id.myGridView);
        currentWallpaper = findViewById(R.id.myImageView);
        gridView.setAdapter(new ImageAdapter(getApplicationContext()));
        UpdateMyWallpaper();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void UpdateMyWallpaper() {

        int permissionCheck = ContextCompat.checkSelfPermission(Setwallpaper.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
            ActivityCompat.requestPermissions(Setwallpaper.this, new String[]{Manifest.permission.SET_WALLPAPER}, REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            drawable = wallpaperManager.getDrawable();
            currentWallpaper.setImageDrawable(drawable);

        }


    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("permission needed")
                    .setMessage("permission needed to change wallpaper")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(Setwallpaper.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);


                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        }
    }


    public class ImageAdapter extends BaseAdapter{

        Context myContext;
        public ImageAdapter(Context applicationContext) {
            myContext=applicationContext;
        }

        @Override
        public int getCount() {
            return myImageArray.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ImageView GridImageView;
            if(view==null)
            {
                GridImageView=new ImageView(myContext);
                GridImageView.setLayoutParams(new GridView.LayoutParams(512,512));
                GridImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else {
                GridImageView=(ImageView)view;

            }
            GridImageView.setImageResource(myImageArray [i]);



            GridImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        wallpaperManager.setResource(myImageArray[i]);



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UpdateMyWallpaper();


                }
            });


            return GridImageView;
        }
    }





}
