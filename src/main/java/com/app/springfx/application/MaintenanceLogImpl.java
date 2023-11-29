package com.app.springfx.application;

import com.app.springfx.domain.MaintenanceLog;
import com.app.springfx.infraestructure.database.MaintenanceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceLogImpl implements MaintenanceLogService{
    private final MaintenanceLogRepository maintenanceLogRepository;
    @Override
    public List<MaintenanceLog> getAll() {
        return maintenanceLogRepository.findAll();
    }

    @Override
    public MaintenanceLog getById(Long id) {
        return maintenanceLogRepository.findById(id).orElse(null);
    }

    @Override
    public MaintenanceLog save(MaintenanceLog maintenanceLog) {
        return maintenanceLogRepository.save(maintenanceLog);
    }

    @Override
    public MaintenanceLog update(MaintenanceLog maintenanceLog) {
        return maintenanceLogRepository.save(maintenanceLog);
    }

    @Override
    public void delete(Long id) {
        maintenanceLogRepository.deleteById(id);
    }
}
