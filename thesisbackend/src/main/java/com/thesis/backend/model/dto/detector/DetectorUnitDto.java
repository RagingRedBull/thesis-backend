package com.thesis.backend.model.dto.detector;

import com.thesis.backend.model.entity.DetectorUnit;

public class DetectorUnitDto {
    private String macAddress;
    private String ipV4;
    private String name;
    private int xpos;
    private int ypos;

    public DetectorUnitDto(){
        //Default Empty
    }

    public DetectorUnitDto(DetectorUnit entity){
        this.macAddress = entity.getMacAddress();
        this.name = entity.getName();
        this.xpos = entity.getXpos();
    }

    public DetectorUnitDto(String macAddress, String ipV4) {
        this.macAddress = macAddress;
        this.ipV4 = ipV4;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public DetectorUnitDto setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public String getIpV4() {
        return ipV4;
    }

    public DetectorUnitDto setIpV4(String ipV4) {
        this.ipV4 = ipV4;
        return this;
    }

    public String getName() {
        return name;
    }

    public DetectorUnitDto setName(String name) {
        this.name = name;
        return this;
    }

    public int getXpos() {
        return xpos;
    }

    public DetectorUnitDto setXpos(int xpos) {
        this.xpos = xpos;
        return this;
    }

    public int getYpos() {
        return ypos;
    }

    public DetectorUnitDto setYpos(int ypos) {
        this.ypos = ypos;
        return this;
    }
}
