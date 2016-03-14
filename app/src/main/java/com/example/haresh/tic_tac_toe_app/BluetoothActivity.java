package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.UUID;

public class BluetoothActivity extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private ToggleButton toggleButton;
    private ListView listview;
    private ArrayAdapter adapter;
    private static final int ENABLE_BT_REQUEST_CODE = 1;
    private static final int DISCOVERABLE_BT_REQUEST_CODE = 2;
    private static final int Finished_Activity = 3;
    private static final int DISCOVERABLE_DURATION = 300;
    public static BluetoothDevice mBluetoothDevice = null;
    public static BluetoothSocket mBluetoothSocket = null;
    ListeningThread t = null;
    ConnectingThread ct = null;


    private final static UUID uuid = UUID.fromString("fc5ffc49-00e3-4c8b-9cf1-6b72aad1001a");
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                adapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        listview = (ListView) findViewById(R.id.listView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listview.getItemAtPosition(position);
                String MAC = itemValue.substring(itemValue.length() - 17);
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(MAC);

                ct = new ConnectingThread(bluetoothDevice);
                ct.start();
            }
        });

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listview.setAdapter(adapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            toggleButton.setChecked(false);
            adapter.clear();
        }
    }

    public void onToggleClicked(View view) {
        adapter.clear();
        ToggleButton toggleButton = (ToggleButton) view;

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Oops! Your device does not support Bluetooth",
                    Toast.LENGTH_SHORT).show();
            toggleButton.setChecked(false);
        } else {
            toggleButton.setChecked(!bluetoothAdapter.isEnabled());
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
                Toast.makeText(BluetoothActivity.this, "Bluetooth disabled", Toast.LENGTH_SHORT).show();
            } else {
                ///bluetoothAdapter.enable(); //This does not ask for user to permit connection
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetoothIntent, ENABLE_BT_REQUEST_CODE);
                Toast.makeText(BluetoothActivity.this, "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BT_REQUEST_CODE) {
            // Bluetooth successfully enabled!
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth enabled." + "\n" + "Scanning for peers", Toast.LENGTH_SHORT).show();

                makeDiscoverable();
                discoverDevices();

                t = new ListeningThread();
                t.start();

            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled.", Toast.LENGTH_SHORT).show();
                toggleButton.setChecked(false);
            }
        } else if (requestCode == DISCOVERABLE_BT_REQUEST_CODE) {
            if (resultCode == DISCOVERABLE_DURATION) {
                Toast.makeText(getApplicationContext(), "Your device is now discoverable for " + DISCOVERABLE_DURATION + " seconds", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to enable discoverable mode.", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Finished_Activity) {
            bluetoothAdapter.disable();
            adapter.clear();
            toggleButton.setChecked(false);
        }
    }

    protected void discoverDevices() {
        if (bluetoothAdapter.startDiscovery()) {
            Toast.makeText(getApplicationContext(), "Discovering peers", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Discovery failed to start.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void makeDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
        startActivityForResult(discoverableIntent, DISCOVERABLE_BT_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(broadcastReceiver);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {

        mBluetoothDevice = device;
        mBluetoothSocket = socket;
        Intent intent = new Intent(this, TwoDevice2P.class);
        startActivityForResult(intent, Finished_Activity);

    }

    private class ListeningThread extends Thread {
        private final BluetoothServerSocket bluetoothServerSocket;

        public ListeningThread() {
            BluetoothServerSocket temp = null;
            try {
                temp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(getString(R.string.app_name), uuid);

            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothServerSocket = temp;
        }

        public void run() {
            BluetoothSocket bluetoothSocket;
            while (true) {
                try {
                    bluetoothSocket = bluetoothServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                if (bluetoothSocket != null) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "A connection has been accepted.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    connected(bluetoothSocket, bluetoothSocket.getRemoteDevice());

                    try {
                        bluetoothServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public void cancel() {
            try {
                bluetoothServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectingThread extends Thread {
        private final BluetoothDevice bluetoothDevice;
        private final BluetoothSocket bluetoothSocket;

        public ConnectingThread(BluetoothDevice device) {

            BluetoothSocket temp = null;
            bluetoothDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                temp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothSocket = temp;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();

            try {
                bluetoothSocket.connect();
            } catch (IOException connectException) {
                connectException.printStackTrace();
                try {
                    bluetoothSocket.close();
                } catch (IOException closeException) {
                    closeException.printStackTrace();
                }
            }

            if (bluetoothSocket != null && bluetoothDevice != null) {
                connected(bluetoothSocket, bluetoothDevice);
            }

        }

        // Cancel an open connection and terminate the thread
        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
