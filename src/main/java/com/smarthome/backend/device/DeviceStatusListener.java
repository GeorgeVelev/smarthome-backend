package com.smarthome.backend.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.smarthome.backend.command.HeaterOffCommand;
import com.smarthome.backend.command.HeaterOnCommand;
import com.smarthome.backend.command.LightsOffCommand;
import com.smarthome.backend.command.LightsOnCommand;
import com.smarthome.backend.dto.SensorMessageDTO;
import com.smarthome.backend.service.CommandExecutor;
import com.smarthome.backend.service.MeasurementsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class DeviceStatusListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStatusListener.class);

    // Initial lower and upper threshold values for temperature:
    private Double heaterSwitchOnTresholdValue = 18.0;
    private Double heaterSwitchOffTresholdValue = 26.0;

    // Initial lower and upper threshold values for light:
    private Double lightSwitchOnTresholdValue = 0.222581;
    private Double lightSitchOffTresholdValue = 1.0;

    // TODO: Use deltas to avoid frequent ON/OFF commands when actual value is fluctuating around the threshold value
    private static final Double temperatureThresholdDelta = 2.0;
    private static final Double lightThresholdDelta = 200.0;

    @Value("${smartHome.mqtt.devices.sensor.topic}")
    private String mqttTopic;

    @Autowired
    private Mqtt3AsyncClient mqttClient;

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private MeasurementsService measurementsService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        mqttClient.subscribeWith()
                .topicFilter(MqttTopicFilter.of(mqttTopic))
                .callback(this::onMessageReceived)
                .send();
    }

    private void onMessageReceived(Mqtt3Publish mqtt3Publish) {
        LOGGER.info("Sensor readings received: topic='{}' payload='{}'", mqtt3Publish.getTopic(), new String(mqtt3Publish.getPayloadAsBytes()));
        SensorMessageDTO messageDTO = null;

        if (mqtt3Publish.getPayload().isPresent()) {
            try {
                messageDTO = objectMapper.readValue(mqtt3Publish.getPayloadAsBytes(), SensorMessageDTO.class);
            } catch (IOException e) {
                LOGGER.error("Error parsing sensor message", e);
                return;
            }
        }

        measurementsService.saveMultiple(messageDTO.getMeasurements());
        produceCommands(messageDTO);
    }

    /**
     * Produce commands based on the given input {@link SensorMessageDTO}
     *
     * @param messageDTO data received from sensor device
     */
    private void produceCommands(SensorMessageDTO messageDTO) {
        if (messageDTO != null && !CollectionUtils.isEmpty(messageDTO.getMeasurements())) {
            messageDTO.getMeasurements().forEach(measurementDTO -> {
                switch (measurementDTO.getType()) {
                    case LIGHT:
                        double photoresistorVoltage = Double.parseDouble(measurementDTO.getValue());
                        if (photoresistorVoltage < lightSwitchOnTresholdValue) {
                            commandExecutor.executeCommand(LightsOnCommand.class);
                        } else if (photoresistorVoltage > lightSitchOffTresholdValue) {
                            commandExecutor.executeCommand(LightsOffCommand.class);
                        }
                        break;
                    case TEMPERATURE:
                        double temperature = Double.parseDouble(measurementDTO.getValue());
                        if (temperature < heaterSwitchOnTresholdValue) {
                            commandExecutor.executeCommand(HeaterOnCommand.class);
                        } else if (temperature > heaterSwitchOffTresholdValue) {
                            commandExecutor.executeCommand(HeaterOffCommand.class);
                        }
                        break;
                }
            });
        }
    }

}
