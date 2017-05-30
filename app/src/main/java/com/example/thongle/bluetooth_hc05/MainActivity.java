package com.example.thongle.bluetooth_hc05;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DEVICE = "extra_device";
    public static final String EXTRA_BLUETOOTH = "extra_ble";

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
//                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
//                startActivity(intent);
                if(bluetooth.getBluetoothAdapter().isEnabled())
                    searchDevices();
                else
                    toolbar.setSubtitle(getString(R.string.enabling_blt));
                    bluetooth.enableBluetooth();
            }
        });
        listView_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toolbar.setSubtitle(getString(R.string.asking_to_connect));
                final BluetoothDevice device = bluetoothDevicesAdapter.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(false)
                        .setTitle(getString(R.string.connect))
                        .setMessage("Do you want to connect to: " + device.getName() + " - " + device.getAddress())
                        .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                progressBar_toolbar.setVisibility(View.INVISIBLE);
                                bluetooth.getBluetoothAdapter().cancelDiscovery();
                                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                                intent.putExtra(EXTRA_DEVICE, device);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                toolbar.setSubtitle("Cancelled connection");
                            }
                        }).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiverScan, filter);
    }

    private void searchDevices(){
        if (bluetooth.scanDevices()){
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
