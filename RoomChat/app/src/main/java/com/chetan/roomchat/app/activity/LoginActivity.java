package com.chetan.roomchat.app.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chetan.roomchat.app.model.Login;
import com.chetan.roomchat.app.R;
import com.chetan.roomchat.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        Login login = new Login(LoginActivity.this);
        binding.setLogin(login);
    }

}
