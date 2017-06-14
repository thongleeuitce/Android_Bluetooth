package com.example.thongle.bluetooth_hc05.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thongle.bluetooth_hc05.R;

/**
 * Created by thongle on 21/05/2017.
 */

public class BluetoothDevicesAdapter extends ArrayAdapter<BluetoothDevice> {
    public BluetoothDevicesAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BluetoothDevice bluetoothDevice = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device, parent, false);
        TextView textView_name = (TextView) convertView.findViewById(R.id.txtv_device_name);
        TextView textView_address = (TextView) convertView.findViewById(R.id.txtxv_device_address);
        textView_name.setText(bluetoothDevice.getName());
        textView_address.setText(bluetoothDevice.getAddress());

        return convertView;
    }
}
