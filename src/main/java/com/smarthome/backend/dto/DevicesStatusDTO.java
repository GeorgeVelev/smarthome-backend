package com.smarthome.backend.dto;

import com.smarthome.backend.enums.DeviceStateType;

public class DevicesStatusDTO {

    private DeviceStateType light;
    private DeviceStateType heater;
    private DeviceStateType cooler;

    public DevicesStatusDTO(DeviceStateType light, DeviceStateType heater, DeviceStateType cooler) {
        this.light = light;
        this.heater = heater;
        this.cooler = cooler;
    }

    public DevicesStatusDTO() {
    }

    public DeviceStateType getLight() {
        return light;
    }

    public void setLight(DeviceStateType light) {
        this.light = light;
    }

    public DeviceStateType getHeater() {
        return heater;
    }

    public void setHeater(DeviceStateType heater) {
        this.heater = heater;
    }

    public DeviceStateType getCooler() {
        return cooler;
    }

    public void setCooler(DeviceStateType cooler) {
        this.cooler = cooler;
    }
}
