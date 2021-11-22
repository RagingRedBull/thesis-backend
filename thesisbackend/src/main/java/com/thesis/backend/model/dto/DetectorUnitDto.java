package com.thesis.backend.model.dto;

import com.thesis.backend.model.entity.DetectorUnit;

public class DetectorUnitDto {
    private String macAddress;
    private String name;
    private int xpos;
    private int ypos;

    public DetectorUnitDto(DetectorUnit entity){
        this.macAddress = entity.getMacAddress();
        this.name = entity.getName();
        this.xpos = entity.getXpos();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

}
