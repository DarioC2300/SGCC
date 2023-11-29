package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.infraestructure.database.ComputerCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerCenterServiceImpl implements ComputerCenterService{
    private final ComputerCenterRepository computerCenterRepository;

    @Override
    public List<ComputerCenter> getAll() {
        return computerCenterRepository.findAll();
    }

    @Override
    public ComputerCenter getById(Long id) {
        return computerCenterRepository.findById(id).orElse(null);
    }

    @Override
    public ComputerCenter save(ComputerCenter computerCenter) {
        return computerCenterRepository.save(computerCenter);
    }

    @Override
    public ComputerCenter update(ComputerCenter computerCenter) {
        return computerCenterRepository.save(computerCenter);
    }

    @Override
    public void delete(Long id) {
        computerCenterRepository.deleteById(id);
    }
}
