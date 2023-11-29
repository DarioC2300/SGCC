package com.app.springfx.application;

import com.app.springfx.domain.MaintenanceLog;

import java.util.List;

public interface MaintenanceLogService {
    List<MaintenanceLog> getAll();
    MaintenanceLog getById(Long id);
    MaintenanceLog save(MaintenanceLog maintenanceLog);
    MaintenanceLog update(MaintenanceLog maintenanceLog);
    void delete(Long id);
}
