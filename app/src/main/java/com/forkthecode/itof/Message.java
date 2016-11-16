package com.forkthecode.itof;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by rohanarora on 17/11/16.
 */
@JsonObject
public class Message {

    @JsonField
    String type;


    public static final String TYPE_CAMERA_REQUEST = "type_camera_request";
    public static final String TYPE_CAMERA_RESULT = "type_camera_result";
}
