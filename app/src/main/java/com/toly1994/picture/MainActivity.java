package com.toly1994.picture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toly1994.picture.world.World;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new World(this));
    }
}
