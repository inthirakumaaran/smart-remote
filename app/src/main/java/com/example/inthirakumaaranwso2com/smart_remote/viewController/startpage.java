package com.example.inthirakumaaranwso2com.smart_remote.viewController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.inthirakumaaranwso2com.smart_remote.R;

public class startpage extends AppCompatActivity {
    @BindView(R.id.start_register)
    Button register;
    @BindView(R.id.start_select)
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        ButterKnife.bind(this);



    }

    public void select(View v) {
        startActivity(new Intent(this,remotelist.class));
    }

    @OnClick(R.id.start_register)
    public void register(View view) {
        startActivity(new Intent(this,register_new.class));
        Log.d("start","reg_cliked");

    }
    public void regg(View view){    //back button
        startActivity(new Intent(this,register_new.class));
    }
    public void quit(View v){
        finish();
        System.exit(0);
    }




}
