package com.example.thongle.bluetooth_hc05;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by thongle on 21/05/2017.
 */

public class ConnectActivity extends AppCompatActivity {
    private RecyclerView recyclerView_effect;
    private FloatingActionButton floatingActionButton_add;
    private ArrayList<Effect> effects;
    private ArrayAdapter adapter_effects;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        effects = new ArrayList<>();
    }

}
