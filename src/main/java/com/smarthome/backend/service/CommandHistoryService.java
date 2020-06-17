package com.smarthome.backend.service;

import com.smarthome.backend.dto.CommandHistoryDTO;
import com.smarthome.backend.repository.CommandHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandHistoryService {

    @Autowired
    private CommandHistoryRepository commandHistoryRepository;

    public List<CommandHistoryDTO> getCommandExecutionHistory() {
        return commandHistoryRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "timestamp"))).stream()
                .map(commandHistory -> new CommandHistoryDTO(commandHistory.getTimestamp(), commandHistory.getCommandType()))
                .collect(Collectors.toList());
    }

}
