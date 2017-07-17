package com.example.inthirakumaaranwso2com.smart_remote.viewController;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inthirakumaaranwso2com.smart_remote.R;
import com.example.inthirakumaaranwso2com.smart_remote.controller.BluetoothThread;
import com.example.inthirakumaaranwso2com.smart_remote.controller.DBremote;

public class selectedRemote extends AppCompatActivity implements View.OnClickListener {
    String mBName;    //bundle data intialization
    String mBPower;
    String mBChdwn;
    String mBChup;
    String mBVolup;
    String mBVoldwn;
    Integer mBreID;

    ImageButton mCurrentB;
    DBremote DB;

    private ProgressDialog mProgressDialog;
    Button mCancel;
    Button mSave;
    ImageButton mVolumeDown;
    ImageButton mVolumeUp;
    ImageButton mChannelDown;
    ImageButton mChannelup;
    ImageButton mPower;
    TextView mName;

    // Tag for logging
    private static final String TAG = "RemoteActivity";
    private final int REQUEST_ENABLE_BT = 1;

    //bundle argument name for remote acitivity intent
    private static final String EXTRA_REMOTE_ID = "com.example.arunans23.smartremotecontroller.remoteid";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    private final String address = "98:D3:32:10:D0:1D";

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_remote);
        mCancel=(Button)findViewById(R.id.rem_cancel);
        mVolumeDown=(ImageButton) findViewById(R.id.rem_voldwn);
        mVolumeUp=(ImageButton) findViewById(R.id.rem_volup);
        mChannelDown=(ImageButton) findViewById(R.id.rem_chdwn);
        mChannelup=(ImageButton) findViewById(R.id.rem_chup);
        mPower=(ImageButton) findViewById(R.id.rem_power);
        mName=(TextView)findViewById(R.id.rem_name);
        Bundle data = getIntent().getExtras();       //set inital values
        if (data != null) {
            mBName = data.getString("name");
            mBPower = data.getString("power");
            mBChdwn = data.getString("chdwn");
            mBChup = data.getString("chup");
            mBVolup= data.getString("volup");
            mBVoldwn= data.getString("voldwn");
            mBreID = data.getInt("reID");
            mName.setText(mBName+" REMOTE");
        }
        mPower.setOnClickListener(this);
        mChannelDown.setOnClickListener(this);
        mChannelup.setOnClickListener(this);
        mVolumeDown.setOnClickListener(this);
        mVolumeUp.setOnClickListener(this);
        DB =new DBremote(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Listening for Input");
        connectBluetooth();

    }


    public void cancel(View v){
        finish();
    }
    public void connectBluetooth() {
        Log.v(TAG, "Bluetooth connected...");

        // Only one thread at a time
        if (btt != null) {
            Log.w(TAG, "Already connected!");
            return;
        }

        // Initialize the Bluetooth thread, passing in a MAC address
        // and a Handler that will receive incoming messages
        btt = new BluetoothThread(address, new Handler() {

            @Override
            public void handleMessage(Message message) {

                String s = (String) message.obj;

                // Do something with the message
                if (s.equals("CONNECTED")) {
                    Toast.makeText(selectedRemote.this, "Connection stable", Toast.LENGTH_SHORT).show();

                } else if (s.equals("DISCONNECTED")) {
                    Toast.makeText(selectedRemote.this, "Disconnected", Toast.LENGTH_SHORT).show();

                } else if (s.equals("CONNECTION FAILED")) {
                    Toast.makeText(selectedRemote.this, "Connection failed", Toast.LENGTH_LONG).show();

                } else if (s.equals("ADAPTER 404")){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                } else {

                }
            }
        });

        // Get the handler that is used to send messages
        writeHandler = btt.getWriteHandler();

        // Run the thread
        btt.start();

    }

    /**
     * Kill the Bluetooth thread.
     */
    public void disconnectBluetooth() {
        Log.v(TAG, "Bluetooth Disconnected...");

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    /**
     * Send a message using the Bluetooth thread's write handler.
     */
    public void writeData(String data) {
        Log.v(TAG, "Data passed" + data);

        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }


    /**
     * Kill the thread when we leave the activity.
     */
    protected void onPause() {
        super.onPause();

        disconnectBluetooth();
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectBluetooth();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rem_power){
            writeData(mBPower);
        }
        else if (v.getId() == R.id.rem_chdwn){
            writeData(mBChdwn);
        }
        else if (v.getId() == R.id.rem_chup){
            writeData(mBChup);
        }
        else if (v.getId() == R.id.rem_voldwn){
            writeData(mBVoldwn);
        }
        else if (v.getId() == R.id.rem_voldwn){
            writeData(mBVolup);
        }
    }

}
