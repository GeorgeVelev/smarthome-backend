package com.smarthome.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.smarthome.backend.dto.MeasurementDTO;
import com.smarthome.backend.dto.SensorMessageDTO;
import com.smarthome.backend.enums.MeasurementType;
import com.smarthome.backend.enums.MeasurementUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MqttMessageGenerator {

    @Autowired
    private Mqtt3AsyncClient mqttClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${smartHome.mqtt.devices.sensor.topic}")
    private String publishTopic;

    @Scheduled(initialDelay = 30_000L, fixedRate = 10_000L)
    public void generateMessages() throws JsonProcessingException {
        SensorMessageDTO messageDTO = new SensorMessageDTO("app-msg-generator",
                Arrays.asList(
                        new MeasurementDTO(MeasurementType.LIGHT, MeasurementUnit.VOLTAGE, Double.toString(ThreadLocalRandom.current().nextDouble(0.0, 1023.0))),
                        new MeasurementDTO(MeasurementType.TEMPERATURE, MeasurementUnit.CELSIUS, Double.toString(ThreadLocalRandom.current().nextDouble(0.0, 1023.0)))
                )
        );

        mqttClient.publishWith()
                .topic(MqttTopic.of(publishTopic))
                .payload(objectMapper.writeValueAsBytes(messageDTO))
                .send();
    }

}
