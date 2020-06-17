package com.smarthome.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceStateType {

    ON, OFF, UNKNOWN;

    @JsonCreator
    public static DeviceStateType fromString(String type) {
        for (DeviceStateType state : DeviceStateType.values()) {
            if (state.name().equalsIgnoreCase(type)) {
                return state;
            }
        }
        return null;
    }

    @JsonValue
    public String getTzype() {
        return name();
    }

}
