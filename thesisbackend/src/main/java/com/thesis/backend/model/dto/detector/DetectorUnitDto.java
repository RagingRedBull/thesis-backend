package com.thesis.backend.model.dto.detector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thesis.backend.model.entity.DetectorUnit;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
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

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpV4() {
        return ipV4;
    }

    public void setIpV4(String ipV4) {
        this.ipV4 = ipV4;
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
