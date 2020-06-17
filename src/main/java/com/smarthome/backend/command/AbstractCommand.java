package com.smarthome.backend.command;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.smarthome.backend.exception.CommandExecutionException;
import com.smarthome.backend.repository.CommandHistoryRepository;
import com.smarthome.backend.service.DeviceState;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommand<T> {

    @Autowired
    protected Mqtt3AsyncClient mqttClient;

    @Autowired
    protected CommandHistoryRepository commandHistoryRepository;

    @Autowired
    protected DeviceState deviceState;

    protected boolean canExecute() {
        return true;
    }

    protected abstract T doExecute();

    public T execute() {
        if (canExecute()) {
            return doExecute();
        } else {
            throw new CommandExecutionException("Command cannot be executed!");
        }
    }

}
