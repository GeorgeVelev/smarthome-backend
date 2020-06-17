package com.smarthome.backend.repository;

import com.smarthome.backend.model.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Integer> {
}
