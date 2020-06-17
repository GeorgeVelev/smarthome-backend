package com.smarthome.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CommandType {

    LIGHTS_ON,

    LIGHTS_OFF,

    HEAT_ON,

    HEAT_OFF,

    COOL_ON,

    COOL_OFF;

    @JsonCreator
    public static CommandType fromString(String type) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.name().equalsIgnoreCase(type)) {
                return commandType;
            }
        }
        return null;
    }

    @JsonValue
    public String getType() {
        return name();
    }

}
