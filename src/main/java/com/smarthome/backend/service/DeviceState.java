package com.smarthome.backend.service;

import com.smarthome.backend.dto.DevicesStatusDTO;
import com.smarthome.backend.enums.DeviceStateType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeviceState {

    private DeviceStateType light;
    private DeviceStateType heater;
    private DeviceStateType cooler;

    @PostConstruct
    public void init() {
        light = DeviceStateType.UNKNOWN;
        heater = DeviceStateType.UNKNOWN;
        cooler = DeviceStateType.UNKNOWN;
    }

    public DevicesStatusDTO getDevicesStatus() {
        return new DevicesStatusDTO(light, heater, cooler);
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
