package com.example.thongle.bluetooth_hc05;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar_toolbar;
    private Button button_search;
    private ListView listView_devices;
    private TextView textView_empty_devices;
    private CoordinatorLayout coordinatorLayout;
    private BluetoothDevicesAdapter bluetoothDevicesAdapter;

    private Bluetooth bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar_toolbar = (ProgressBar) findViewById(R.id.prog_toolbar);
        button_search = (Button) findViewById(R.id.btn_search);
        listView_devices = (ListView) findViewById(R.id.liv_devices);
        textView_empty_devices = (TextView) findViewById(R.id.txtv_nothing);

        bluetooth = new Bluetooth(this);
        bluetoothDevicesAdapter = new BluetoothDevicesAdapter(this);
//        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getString(R.string.none));
        listView_devices.setAdapter(bluetoothDevicesAdapter);
        listView_devices.setEmptyView(textView_empty_devices);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetooth.getBluetoothAdapter().isEnabled())
                    searchDevices();
                else
                    toolbar.setSubtitle(getString(R.string.enabling_blt));
                    bluetooth.enableBluetooth();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == bluetooth.REQUEST_ENABLE_BLT) {
            if (resultCode == RESULT_OK) {
                searchDevices();
            } else {
                toolbar.setSubtitle(getString(R.string.error));
                Snackbar.make(coordinatorLayout, getString(R.string.failed_to_enable_bluetooth), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.try_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bluetooth.enableBluetooth();
                            }
                        }).show();
            }
        }
    }

    private void searchDevices(){
        if (bluetooth.scanDevices(mReceiverScan)){
            progressBar_toolbar.setVisibility(View.VISIBLE);
            toolbar.setSubtitle(getString(R.string.searching_devices));
        }
        else {
            toolbar.setSubtitle(getString(R.string.error));
            Snackbar.make(coordinatorLayout, getString(R.string.failed_to_search), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.try_again), new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            searchDevices();
                        }
                    }).show();
        }

    }

    private BroadcastReceiver mReceiverScan = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                    if (state == BluetoothAdapter.STATE_OFF) {
                        if (bluetooth.getDiscoveryCallback() != null)
                            bluetooth.getDiscoveryCallback().onError("Bluetooth turned off");
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    context.unregisterReceiver(mReceiverScan);
                    progressBar_toolbar.setVisibility(View.INVISIBLE);
                    toolbar.setSubtitle(getString(R.string.none));
                    if (bluetooth.getDiscoveryCallback() != null)
                        bluetooth.getDiscoveryCallback().onFinish();
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(bluetoothDevicesAdapter.getPosition(device) == -1) {
                        bluetoothDevicesAdapter.add(device);
                        bluetoothDevicesAdapter.notifyDataSetChanged();
                    }
                    if (bluetooth.getDiscoveryCallback() != null)
                        bluetooth.getDiscoveryCallback().onDevice(device);
                    break;
            }
        }
    };
}
