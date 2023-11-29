package com.app.springfx.application;

import com.app.springfx.domain.Peripheral;

import java.util.List;

public interface PeripheralService {
    List<Peripheral> getAll();
    Peripheral getById(Long id);
    Peripheral save(Peripheral peripheral);
    Peripheral update(Peripheral peripheral);
    void delete(Long id);
}
