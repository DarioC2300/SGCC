package com.app.springfx.application;

import com.app.springfx.domain.Peripheral;
import com.app.springfx.infraestructure.database.PeripheralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeripheralServiceImpl implements PeripheralService{
    private final PeripheralRepository peripheralRepository;

    @Override
    public List<Peripheral> getAll() {
        return peripheralRepository.findAll();
    }

    @Override
    public Peripheral getById(Long id) {
        return peripheralRepository.findById(id).orElse(null);
    }

    @Override
    public Peripheral save(Peripheral peripheral) {
        return peripheralRepository.save(peripheral);
    }

    @Override
    public Peripheral update(Peripheral peripheral) {
        return peripheralRepository.save(peripheral);
    }

    @Override
    public void delete(Long id) {
        peripheralRepository.deleteById(id);
    }
}
