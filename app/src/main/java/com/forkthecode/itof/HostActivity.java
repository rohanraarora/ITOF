package com.forkthecode.itof;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.io.IOException;

public class HostActivity extends AppCompatActivity implements SalutDataCallback{

    Salut salut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        salut = SalutManager.getSalut(this, this, new SalutCallback() {
            @Override
            public void call() {
                Toast.makeText(HostActivity.this,"Device Not Supported",Toast.LENGTH_LONG).show();
            }
        });

        salut.startNetworkService(new SalutDeviceCallback() {
            @Override
            public void call(SalutDevice device) {
                Toast.makeText(HostActivity.this, "Device Connected: " + device.readableName, Toast.LENGTH_SHORT).show();
            }
        }, new SalutCallback() {
            @Override
            public void call() {
                Toast.makeText(HostActivity.this, "Service started", Toast.LENGTH_SHORT).show();
            }
        }, new SalutCallback() {
            @Override
            public void call() {
                Toast.makeText(HostActivity.this, "Unable to start service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendCameraRequest(View view){

        CameraRequestMessage requestMessage = new CameraRequestMessage();
        salut.sendToAllDevices(requestMessage, new SalutCallback() {
            @Override
            public void call() {
                Toast.makeText(HostActivity.this, "Unable to send request", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        salut.stopNetworkService(false);
    }

    @Override
    public void onDataReceived(Object o) {
        try
        {
            CameraResultMessage resultMessage = LoganSquare.parse(o.toString(),CameraResultMessage.class);
            Toast.makeText(HostActivity.this,"Camera result received",Toast.LENGTH_LONG).show();

            //See you on the other side!
            //Do other stuff with data.
        }
        catch (IOException ex)
        {
            Toast.makeText(HostActivity.this,"Fail to parse result",Toast.LENGTH_LONG).show();

        }
    }
}
