package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.domain.Software;
import com.app.springfx.infraestructure.database.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftwareServiceImpl implements SoftwareService{
    private final SoftwareRepository softwareRepository;

    @Override
    public List<Software> getAll() {
        return softwareRepository.findAll();
    }

    @Override
    public Software getById(Long id) {
        return softwareRepository.findById(id).orElse(null);
    }

    @Override
    public Software save(Software software) {
        return softwareRepository.save(software);
    }

    @Override
    public Software update(Software software) {
        return softwareRepository.save(software);
    }

    @Override
    public void delete(Long id) {
        softwareRepository.deleteById(id);
    }
}
