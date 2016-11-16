package com.forkthecode.itof;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.peak.salut.Salut;

/**
 * Created by rohanarora on 17/11/16.
 */
@JsonObject
public class CameraResultMessage extends Message{

    @JsonField
    String result;

    public CameraResultMessage(){
        type = Message.TYPE_CAMERA_RESULT;
    }


}
