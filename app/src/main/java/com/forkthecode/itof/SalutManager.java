package com.forkthecode.itof;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutServiceData;

/**
 * Created by rohanarora on 16/11/16.
 */

public class SalutManager{


    public static Salut getSalut(Activity activity, SalutDataCallback dataCallback, SalutCallback deviceNotSupported){

        SalutDataReceiver dataReceiver = new SalutDataReceiver(activity,dataCallback);
        SalutServiceData serviceData = new SalutServiceData("ITOF",50489, Build.MODEL + " " + Build.DEVICE);
        Salut salut = new Salut(dataReceiver,serviceData, deviceNotSupported);

        return salut;
    }

}
