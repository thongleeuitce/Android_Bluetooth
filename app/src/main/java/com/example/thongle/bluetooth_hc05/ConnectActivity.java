package com.example.thongle.bluetooth_hc05;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
    private AdapterEffect adapter_effects;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        recyclerView_effect = (RecyclerView) findViewById(R.id.recv_effect);
        floatingActionButton_add = (FloatingActionButton) findViewById(R.id.floatbtn_add);

        effects = new ArrayList<>();

        recyclerView_effect.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        effects.add(new Effect("effect 1", "0", "com", R.drawable.logo));
        effects.add(new Effect("effect 2", "1", "hu tieu", R.drawable.logo));
        effects.add(new Effect("effect 3", "2", "bo kho", R.drawable.logo));
        effects.add(new Effect("effect 4", "3", "sinh to", R.drawable.logo));
        effects.add(new Effect("effect 5", "4", "tra sua", R.drawable.logo));
        effects.add(new Effect("effect 6", "5", "Banh xeo", R.drawable.logo));
        effects.add(new Effect("effect 7", "6", "Diem Diem", R.drawable.logo));
        effects.add(new Effect("effect 8", "7", "Tieu chen", R.drawable.logo));
        effects.add(new Effect("effect 9", "8", "Tieu to", R.drawable.logo));
        effects.add(new Effect("effect 10", "9", "mi cay", R.drawable.logo));

        adapter_effects = new AdapterEffect(effects, ConnectActivity.this);
        recyclerView_effect.setAdapter(adapter_effects);
    }

}
