package com.smarthome.backend.controller;

import com.smarthome.backend.device.DeviceCommandPublisher;
import com.smarthome.backend.dto.CommandDTO;
import com.smarthome.backend.dto.CommandHistoryDTO;
import com.smarthome.backend.dto.DevicesStatusDTO;
import com.smarthome.backend.dto.MeasurementDTO;
import com.smarthome.backend.service.CommandHistoryService;
import com.smarthome.backend.service.DeviceState;
import com.smarthome.backend.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: refactor & split into multiple controllers without mixing responsibilities!
@RestController
@RequestMapping("/smarthome")
@CrossOrigin(origins = "http://localhost")
public class SmartHomeController {

    @Autowired
    private MeasurementsService measurementsService;

    @Autowired
    private DeviceCommandPublisher deviceCommandPublisher;

    @Autowired
    private CommandHistoryService commandHistoryService;

    @Autowired
    private DeviceState deviceState;

    @GetMapping("/measurements")
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> allMeasurements = measurementsService.getAllMeasurements();
        return allMeasurements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(allMeasurements);
    }

    @GetMapping("/measurements/latest")
    public ResponseEntity<List<MeasurementDTO>> getLastMeasurements(@RequestParam(defaultValue = "100") Integer num) {
        List<MeasurementDTO> measurements = measurementsService.getLastMeasurements(num);
        return measurements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(measurements);
    }

    @GetMapping("/measurements/{type}")
    public ResponseEntity<List<MeasurementDTO>> getMeasurementsByType(@PathVariable String type) {
        List<MeasurementDTO> allByType = measurementsService.getAllByType(type);
        return allByType.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(allByType);
    }

    @GetMapping("/measurements/{type}/latest")
    public ResponseEntity<List<MeasurementDTO>> getLatestMeasurementsByType(@PathVariable String type, @RequestParam(defaultValue = "100") Integer num) {
        List<MeasurementDTO> latestByType = measurementsService.getLastMeasurementsByType(type, num);
        return latestByType.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(latestByType);
    }

    @PostMapping("/device/command")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void applyDeviceCommand(@RequestBody CommandDTO commandDTO) {
        deviceCommandPublisher.publishCommand(commandDTO);
    }

    @GetMapping("/device/command/history")
    public ResponseEntity<List<CommandHistoryDTO>> retrieveCommandHistory() {
        return ResponseEntity.ok(commandHistoryService.getCommandExecutionHistory());
    }

    @GetMapping("/device/state")
    public ResponseEntity<DevicesStatusDTO> getCurrentDeviceState() {
        return ResponseEntity.ok(deviceState.getDevicesStatus());
    }

}
