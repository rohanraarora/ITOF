package com.forkthecode.itof;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by rohanarora on 17/11/16.
 */

@JsonObject
public class CameraRequestMessage extends Message {

    public CameraRequestMessage(){
        type = Message.TYPE_CAMERA_REQUEST;
    }


}
