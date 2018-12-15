package com.growuptang.pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.growuptang.pro.ui.Main2Activity;
import com.growuptang.pro.ui.MainActivity;

import butterknife.ButterKnife;


/**
 * @author Administrator
 * @time 2018年12月15日18:16:10
 */

public class SplashActivity extends AppCompatActivity {

    Button btn_first, btn_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        btn_first = findViewById(R.id.btn_first);

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_second = findViewById(R.id.btn_second);
        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
