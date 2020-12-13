package com.example.authentication;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Uploadingimg extends AppCompatActivity {
    Button ch,up;
    ImageView img;
    StorageReference mStorangeRef;
    public Uri imgurl;
    private StorageTask uploadTask;
    ProgressBar mProgressBar;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_img);
        mStorangeRef= FirebaseStorage.getInstance().getReference("Images");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Images");

        img=(ImageView)findViewById(R.id.imageView2);
        ch=(Button)findViewById(R.id.select_btn);
        up=(Button)findViewById(R.id.upload_btn);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar2);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(uploadTask!=null && uploadTask.isInProgress())
                {
                    Toast.makeText(Uploadingimg.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    Fileuploader();
                }}
        });



    }


    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }



    private void Fileuploader(){

       if(imgurl!=null) {

           final StorageReference Ref = mStorangeRef.child(System.currentTimeMillis() + "." + getExtension(imgurl));

           uploadTask = Ref.putFile(imgurl)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // Get a URL to the uploaded content
                           // Uri downloadUrl = taskSnapshot.getDownloadUrl();

                           Handler handler = new Handler();
                           handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   mProgressBar.setProgress(0);
                               }
                           }, 1000);


                           Toast.makeText(Uploadingimg.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                           Upload upload=new Upload(String.valueOf(System.currentTimeMillis()),
                                   Ref.getDownloadUrl().toString());
                           String uploadId=mDatabaseRef.push().getKey();
                           mDatabaseRef.child(uploadId).setValue(upload);

                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception exception) {
                           // Handle unsuccessful uploads
                           // ...
                           Toast.makeText(Uploadingimg.this, "Image uploaded failed", Toast.LENGTH_SHORT).show();

                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                           double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                           mProgressBar.setProgress((int) progress);
                       }
                   });

       }
       else {
           Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
       }

    }

    private void Filechooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imgurl=data.getData();
            img.setImageURI(imgurl);

        }
    }
}
