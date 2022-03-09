package com.thesis.backend.model.response;

public class CompartmentLogResponse {
    private float dht11Temp;
    private float dht22Temp;

    public float getDht11Temp() {
        return dht11Temp;
    }

    public void setDht11Temp(float dht11Temp) {
        this.dht11Temp = dht11Temp;
    }

    public float getDht22Temp() {
        return dht22Temp;
    }

    public void setDht22Temp(float dht22Temp) {
        this.dht22Temp = dht22Temp;
    }
}
