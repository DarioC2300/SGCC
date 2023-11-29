package com.app.springfx.application;

import com.app.springfx.domain.Technician;

import java.util.List;

public interface TechnicianService {
    List<Technician> getAll();
    Technician getById(Long id);
    Technician save(Technician technician);
    Technician update(Technician technician);
    void delete(Long id);
    Technician getByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

}
