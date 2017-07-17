package com.example.inthirakumaaranwso2com.smart_remote.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by arunans23 on 7/7/17.
 */

public class remote {
    private String name;
    private String power;
    String chaup;
    String chadwn;

    public int getReID() {
        return reID;
    }

    public void setReID(int reID) {
        this.reID = reID;
    }

    String voldwn;
    String volUp;
    int reID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getChaup() {
        return chaup;
    }

    public void setChaup(String chaup) {
        this.chaup = chaup;
    }

    public String getChadwn() {
        return chadwn;
    }

    public void setChadwn(String chadwn) {
        this.chadwn = chadwn;
    }

    public String getVoldwn() {
        return voldwn;
    }

    public void setVoldwn(String voldwn) {
        this.voldwn = voldwn;
    }

    public String getVolUp() {
        return volUp;
    }

    public void setVolUp(String volUp) {
        this.volUp = volUp;
    }
}
