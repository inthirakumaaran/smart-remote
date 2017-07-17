package com.example.inthirakumaaranwso2com.smart_remote.model;

/**
 * Created by inthirakumaaranwso2com on 7/15/17.
 */

public class remotekey {
    String keyname;
    String keyvalue;

    public String getKeyname() {
        return keyname;
    }

    public String getKeyvalue() {
        return keyvalue;
    }

    public remotekey(String name, String value){
        keyname=name;
        keyvalue=value;

    }
}
