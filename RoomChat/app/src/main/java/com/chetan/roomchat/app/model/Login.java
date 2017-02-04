package com.chetan.roomchat.app.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.chetan.roomchat.app.BR;
import com.chetan.roomchat.app.activity.ChatActivity;


public class Login extends BaseObservable{

    public String name="";
    private Context context;
    public Login(Context context) {
        this.context = context;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void onClick(View view){
        if(validateName(name)){
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("name",name);
            context.startActivity(intent);
        }else{
            Toast.makeText(view.getContext(), "Please provide your name", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateName(String name){
        return !name.equalsIgnoreCase("");
    }
}
