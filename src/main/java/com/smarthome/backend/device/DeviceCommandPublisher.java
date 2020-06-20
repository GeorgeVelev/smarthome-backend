package com.smarthome.backend.device;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.smarthome.backend.command.*;
import com.smarthome.backend.dto.CommandDTO;
import com.smarthome.backend.service.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceCommandPublisher {

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private Mqtt3AsyncClient mqttClient;

    public void publishCommand(CommandDTO commandDTO) {
        switch (commandDTO.getCommand()) {
            case HEAT_ON:
                commandExecutor.executeCommand(HeaterOnCommand.class);
                break;
            case HEAT_OFF:
                commandExecutor.executeCommand(HeaterOffCommand.class);
                break;
            case LIGHTS_ON:
                commandExecutor.executeCommand(LightsOnCommand.class);
                break;
            case LIGHTS_OFF:
                commandExecutor.executeCommand(LightsOffCommand.class);
                break;
            case COOL_ON:
                commandExecutor.executeCommand(CoolerOnCommand.class);
                break;
            case COOL_OFF:
                commandExecutor.executeCommand(CoolerOffCommand.class);
                break;
            default:
                throw new IllegalArgumentException("Command type not supported!");
        }
    }

}
