# smarthome-backend
Server application for a university IoT project "Smart Home"

## Used technologies
1. Mosquitto MQTT broker
2. MySQL RDBMS
3. Spring Framework & Spring Boot
4. Docker

## Short description
This is the back-end server application for the "Smart Home" university project for "Distributed Embedded Systems" discipline.

### Functionalities:
- Room lights and temperature monitoring
- Temperature control via commands based on the temperature sensor readings
    - commands issued to relays controlling a fan and a heater
- Lights control via commands based on the lights sensor readings
    - commands issued to relays the room lights
    
### Hardware:
- 2x NodeMCU (ESP8266) modules
- 4x AC relays (10A/250V)
- 1x DHT11 temperature and humidity sensor
- 1x photoresistor as a light sensor

## How-to
1. Download and install Docker -> [link](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
2. Execute ``docker-compose up`` in the root directory of the project
3. Run the Spring Boot appliaction - ``./mvnw spring-boot:run``

## Sample HTTP requests

##### Fetch latest 5 light measurements:
```GET http://localhost:8090/smarthome/measurements/light/latest?num=5```

##### Fetch latest 5 temperature measurements:
```GET http://localhost:8090/smarthome/measurements/temperature/latest?num=5```

##### Fetch all light measurements:
```GET http://localhost:8090/smarthome/measurements/light```

##### Fetch all temperature measurements:
```GET http://localhost:8090/smarthome/measurements/temperature```

##### Issue a lights-on command to a device:
```POST http://localhost:8090/smarthome/device/command``` with the following JSON request body: 
```json
  { 
    "command": "LIGHTS_ON" 
  }
```
Supported commands: ```LIGHTS_ON, LIGHTS_OFF, HEAT_ON, HEAT_OFF, VENT_ON, VENT_OFF```
