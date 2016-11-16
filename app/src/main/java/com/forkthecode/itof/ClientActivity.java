package com.forkthecode.itof;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDevice;

import java.io.IOException;

public class ClientActivity extends AppCompatActivity implements SalutDataCallback{

    Salut salut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        salut = SalutManager.getSalut(this, this, new SalutCallback() {
            @Override
            public void call() {
                Toast.makeText(ClientActivity.this,"Device Not Supported",Toast.LENGTH_LONG).show();
            }
        });

        salut.discoverNetworkServices(new SalutDeviceCallback() {
            @Override
            public void call(SalutDevice device) {
                Toast.makeText(ClientActivity.this,"Host Found: " + device.readableName,Toast.LENGTH_LONG).show();
                salut.registerWithHost(device, new SalutCallback() {
                    @Override
                    public void call() {
                        Toast.makeText(ClientActivity.this,"Connected with host",Toast.LENGTH_LONG).show();
                    }
                }, new SalutCallback() {
                    @Override
                    public void call() {
                        Toast.makeText(ClientActivity.this,"Unable to connect with host",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        salut.unregisterClient(false);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onDataReceived(Object o) {
        try
        {
            CameraRequestMessage requestMessage = LoganSquare.parse(o.toString(),CameraRequestMessage.class);
            Toast.makeText(ClientActivity.this,"Camera request received",Toast.LENGTH_LONG).show();
            dispatchTakePictureIntent();

            //See you on the other side!
            //Do other stuff with data.
        }
        catch (IOException ex)
        {
            Toast.makeText(ClientActivity.this,"Fail to parse request",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String bitmap = Util.getStringFromBitmap(imageBitmap);
            CameraResultMessage resultMessage = new CameraResultMessage();
            resultMessage.result = bitmap;
            salut.sendToHost(resultMessage, new SalutCallback() {
                @Override
                public void call() {
                    Toast.makeText(ClientActivity.this,"Unable to send result back to host",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
