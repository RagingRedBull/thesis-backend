package com.thesis.backend.util;

public class StringUtil {
    public static String formatMacAddress(String macAddress) {
        if(macAddress.contains("-") || macAddress.contains(".")){
            macAddress = macAddress.replace('-',':');
            macAddress = macAddress.replace('.', ':');
        }
        macAddress = macAddress.toUpperCase();
        return macAddress;
    }
}
