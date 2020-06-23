package com.smarthome.backend.command;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.smarthome.backend.annotation.Command;
import com.smarthome.backend.enums.CommandType;
import com.smarthome.backend.enums.DeviceStateType;
import com.smarthome.backend.model.CommandHistory;
import org.springframework.beans.factory.annotation.Value;

@Command
public class HeaterOffCommand extends AbstractCommand<Void> {

    private final CommandType commandType = CommandType.HEAT_OFF;

    @Value("${smartHome.mqtt.devices.heaterActuator.topic}")
    private String topic;

    @Override
    protected Void doExecute() {
        if (deviceState.getHeater() != DeviceStateType.OFF) {
            mqttClient.publishWith()
                    .topic(MqttTopic.of(topic))
                    .qos(MqttQos.EXACTLY_ONCE)
                    .payload(commandType.getType().getBytes())
                    .send();

            deviceState.setHeater(DeviceStateType.OFF);
            commandHistoryRepository.save(new CommandHistory(commandType));
        }
        return null;
    }
}
