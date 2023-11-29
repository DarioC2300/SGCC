package com.app.springfx.application;

import com.app.springfx.domain.Technician;
import com.app.springfx.infraestructure.database.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TechnicianImpl implements TechnicianService{
    private final TechnicianRepository technicianRepository;
    @Override
    public List<Technician> getAll() {
        return technicianRepository.findAll();
    }

    @Override
    public Technician getById(Long id) {
        return technicianRepository.findById(id).orElse(null);
    }

    @Override
    public Technician save(Technician technician) {
        return technicianRepository.save(technician);
    }

    @Override
    public Technician update(Technician technician) {
        return technicianRepository.save(technician);
    }

    @Override
    public void delete(Long id) {
        technicianRepository.deleteById(id);
    }

    @Override
    public Technician getByUsernameAndPassword(String username, String password) {
        return technicianRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return technicianRepository.findByUsername(username).isPresent();
    }
}
