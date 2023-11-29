package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.domain.ComputerDevice;
import com.app.springfx.infraestructure.database.ComputerCenterRepository;
import com.app.springfx.infraestructure.database.ComputerDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerDeviceServiceImpl implements ComputerDeviceService{
    private final ComputerDeviceRepository computerDeviceRepository;

    @Override
    public List<ComputerDevice> getAll() {
        return computerDeviceRepository.findAll();
    }

    @Override
    public ComputerDevice getById(Long id) {
        return computerDeviceRepository.findById(id).orElse(null);
    }

    @Override
    public ComputerDevice save(ComputerDevice computerDevice) {
        return computerDeviceRepository.save(computerDevice);
    }

    @Override
    public ComputerDevice update(ComputerDevice computerDevice) {
        return computerDeviceRepository.save(computerDevice);
    }

    @Override
    public void delete(Long id) {
        computerDeviceRepository.deleteById(id);
    }
}
