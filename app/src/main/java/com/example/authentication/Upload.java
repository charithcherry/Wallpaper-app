package com.example.authentication;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Upload{

    private String mName;
    private String mImageUrl;


    public Upload(String l, Task<Uri> downloadUrl){
        //empty contructor needed
    }

    public Upload(String name,String imageurl){

       if(name.trim().equals("")) {

           name="No Name";

       }
           mName=name;
        mImageUrl=imageurl;
    }

    public String getmName(){
        return mName;
    }
    public void setmName(String name){
        mName=name;
    }


    public String getmImageUrl(){
        return mImageUrl;
    }
    public void setmImageUrl(String imageUrl){
        mImageUrl=imageUrl;
    }




}
