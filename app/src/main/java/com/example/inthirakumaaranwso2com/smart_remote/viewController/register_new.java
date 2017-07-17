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
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.example.inthirakumaaranwso2com.smart_remote.R;
import com.example.inthirakumaaranwso2com.smart_remote.controller.BluetoothThread;
import com.example.inthirakumaaranwso2com.smart_remote.controller.DBremote;
import com.example.inthirakumaaranwso2com.smart_remote.model.remotekey;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class register_new extends AppCompatActivity implements View.OnClickListener {
    // Tag for logging
    private static final String TAG = "RemoteConfigureActivity";

    //Argument for startActivityForResult() in bluetoothadapter connection
    private final int REQUEST_ENABLE_BT = 1;

    //this character has to be sent first to start receiving a character
    private final String READ_ENABLE_CHAR  = "*";

    //this character has to be sent at the end to stop receiving characters
    private final String READ_DISABLE_CHAR = "/";

    // MAC address of remote Bluetooth device
    private final String address = "98:D3:32:10:D0:1D";

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

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
    EditText mName;


//    @BindView(R.id.reg_cancel)
//    Button mCancel;
//    @BindView(R.id.reg_save)
//    Button mSave;
//    @BindView(R.id.regi_name)
//    EditText mName;
//    @BindView(R.id.regi_power)
//    ImageButton mPower;
//    @BindView(R.id.regi_chUp)
//    ImageButton mChannelup;
//    @BindView(R.id.regi_chDwn)
//    ImageButton mChannelDown;
//    @BindView(R.id.regi_volDwn)
//    ImageButton mVolumeDown;
//    @BindView(R.id.regi_volUp)
//    ImageButton mVolumeUp;

    ArrayList<remotekey> mRemoteKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        ButterKnife.bind(this);
        mCancel=(Button)findViewById(R.id.reg_cancel);
        mSave=(Button)findViewById(R.id.reg_save);
        mVolumeDown=(ImageButton) findViewById(R.id.regi_volDwn);
        mVolumeUp=(ImageButton) findViewById(R.id.regi_volUp);
        mChannelDown=(ImageButton) findViewById(R.id.regi_chDwn);
        mChannelup=(ImageButton) findViewById(R.id.regi_chUp);
        mPower=(ImageButton) findViewById(R.id.regi_power);
        mName=(EditText)findViewById(R.id.regi_name);
        mCancel.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mPower.setOnClickListener(this);
        mChannelDown.setOnClickListener(this);
        mChannelup.setOnClickListener(this);
        mVolumeDown.setOnClickListener(this);
        mVolumeUp.setOnClickListener(this);
        DB =new DBremote(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Listening for Input");
        mRemoteKeys = new ArrayList<>();
        connectBluetooth();
        }




    @Override
    public void onClick(View v) {
        Log.d("summa","click");
        if (v.getId() == R.id.reg_cancel){
            startActivity(new Intent(register_new.this,startpage.class));
        }
        else if (v.getId() == R.id.reg_save){
            String pPower="",pChUp="",pChDwn="",pVolUp="",pVolDown="";
            if ((mRemoteKeys.size() == 5) && !mName.getText().toString().equals("") &&
                    !DB.checkremotename(mName.getText().toString())){
                for (remotekey k :mRemoteKeys){
                    if (k.getKeyname().equals("power")){
                        pPower =k.getKeyvalue();
                    }
                    else if(k.getKeyname().equals("chDown")){
                        pChDwn=k.getKeyvalue();
                    }
                    else if(k.getKeyname().equals("chUp")){
                        pChUp=k.getKeyvalue();
                    }
                    else if(k.getKeyname().equals("volUp")){
                        pVolUp=k.getKeyvalue();
                    }
                    else if(k.getKeyname().equals("volDown")){
                        pVolDown=k.getKeyvalue();
                    }
                }
                DB.addremote(mName.getText().toString(),pPower,pChDwn,pChUp,pVolUp,pVolDown);
                finish();

            } else if (mName.getText().toString().equals("")){
                Snackbar.make(v, "Fill in the remote name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else if (DB.checkremotename(mName.getText().toString())){
                Snackbar.make(v, "choose different remote name", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(v, "Configure all the remote buttons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }}
        else if (v.getId() == R.id.regi_chDwn ||v.getId() == R.id.regi_chUp
                ||v.getId() == R.id.regi_volDwn ||v.getId() == R.id.regi_volUp
                ||v.getId() == R.id.regi_power ){
                    showProgressDialog((ImageButton)findViewById(v.getId()));
                    writeData(READ_ENABLE_CHAR);
        }

    }

    public void connectBluetooth() {
        Log.v(TAG, "Bluetooth Connected...");

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
                    Toast.makeText(register_new.this, "Connection stable", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                } else if (s.equals("DISCONNECTED")) {
                    Toast.makeText(register_new.this, "Disconnected", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                } else if (s.equals("CONNECTION FAILED")) {
                    Toast.makeText(register_new.this, "Connection failed", Toast.LENGTH_LONG).show();
                    hideProgressDialog();


                } else if (s.equals("ADAPTER 404")){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    hideProgressDialog();

                } else {
                    receiveConfigureDetails(s);

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
        Log.v(TAG, "Bluetooth Disconnected");

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


    //show a progress dialog box whenever waiting for data
    private void showProgressDialog(ImageButton b){
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
        this.mCurrentB = b;
    }

    //dismiss progress dialog after listening to data
    private void hideProgressDialog(){
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        this.mCurrentB = null;
        writeData(this.READ_DISABLE_CHAR);
    }

    //method to invoke after receiving configuration data
    private void receiveConfigureDetails(String data){
        if (this.mCurrentB != null){
            this.mCurrentB.setEnabled(false);
        }

        if (this.mCurrentB != null){
            if(mCurrentB==mPower){
                this.mRemoteKeys.add(new remotekey("power", data));
//            Log.i(TAG, mCurrentB.getText().toString() + " " + data);
//            Log.i(TAG, mCurrentButton.getText().toString());
//            Log.i(TAG, mRemoteKeys.get(0).getRemoteKeyValues());
                hideProgressDialog();
            }
            else if(mCurrentB==mChannelDown){
                this.mRemoteKeys.add(new remotekey("chDown", data));
                hideProgressDialog();
            }
            else if(mCurrentB==mChannelup){
                this.mRemoteKeys.add(new remotekey("chUp", data));
                hideProgressDialog();
            }
            else if(mCurrentB==mVolumeDown){
                this.mRemoteKeys.add(new remotekey("volDown", data));
                hideProgressDialog();
            }
            else if(mCurrentB==mVolumeUp){
                this.mRemoteKeys.add(new remotekey("volUp", data));
                hideProgressDialog();
            }

        }

    }


}
