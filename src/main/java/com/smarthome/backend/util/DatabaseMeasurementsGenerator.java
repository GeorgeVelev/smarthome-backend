package com.smarthome.backend.util;

import com.smarthome.backend.dto.MeasurementDTO;
import com.smarthome.backend.enums.MeasurementType;
import com.smarthome.backend.enums.MeasurementUnit;
import com.smarthome.backend.service.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(value = "smartHome.database.enableMeasurementsGenerator", havingValue = "true")
public class DatabaseMeasurementsGenerator implements CommandLineRunner {

    @Autowired
    private MeasurementsService measurementsService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 20; i++) {
            measurementsService.saveMultiple(Arrays.asList(
                    new MeasurementDTO(MeasurementType.LIGHT, MeasurementUnit.VOLTAGE, Double.toString(ThreadLocalRandom.current().nextDouble(0.0, 3.3))),
                    new MeasurementDTO(MeasurementType.TEMPERATURE, MeasurementUnit.CELSIUS, Double.toString(ThreadLocalRandom.current().nextDouble(10.0, 33.0)))
            ));
        }
    }

}
