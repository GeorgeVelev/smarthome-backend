package com.smarthome.backend.command;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.smarthome.backend.annotation.Command;
import com.smarthome.backend.enums.CommandType;
import com.smarthome.backend.enums.DeviceStateType;
import com.smarthome.backend.model.CommandHistory;
import org.springframework.beans.factory.annotation.Value;

@Command
public class HeaterOnCommand extends AbstractCommand<Void> {

    private final CommandType commandType = CommandType.HEAT_ON;

    @Value("${smartHome.mqtt.devices.heaterActuator.topic}")
    private String topic;

    @Override
    protected Void doExecute() {
        mqttClient.publishWith()
                .topic(MqttTopic.of(topic))
                .qos(MqttQos.EXACTLY_ONCE)
                .payload(commandType.getType().getBytes())
                .send();

        deviceState.setHeater(DeviceStateType.ON);
        commandHistoryRepository.save(new CommandHistory(commandType));
        return null;
    }
}
