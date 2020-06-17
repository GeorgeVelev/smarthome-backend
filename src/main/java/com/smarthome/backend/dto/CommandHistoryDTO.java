package com.smarthome.backend.dto;

import com.smarthome.backend.enums.CommandType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class CommandHistoryDTO {

    private LocalDateTime timestamp;

    private CommandType commandType;

    public CommandHistoryDTO(LocalDateTime timestamp, CommandType commandType) {
        this.timestamp = timestamp;
        this.commandType = commandType;
    }

    public CommandHistoryDTO() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}
