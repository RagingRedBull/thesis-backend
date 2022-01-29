package com.thesis.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class DetectorUnitDto {
    @JsonProperty(required = true)
    private String macAddress;
    @JsonProperty(required = true)
    private String ipV4;
    private String name;
    private int xpos;
    private int ypos;

    public DetectorUnitDto(){
        //Default Empty
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

    @Override
    public String toString() {
        return "DetectorUnitDto{\n" +
                "macAddress='" + macAddress + '\'' +
                "\n, ipV4='" + ipV4 + '\'' +
                "\n, name='" + name + '\'' +
                "\n, xpos=" + xpos +
                "\n, ypos=" + ypos +
                "n" + '}';
    }
}
