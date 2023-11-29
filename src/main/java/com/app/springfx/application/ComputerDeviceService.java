package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.domain.ComputerDevice;

import java.util.List;

public interface ComputerDeviceService {
    List<ComputerDevice> getAll();
    ComputerDevice getById(Long id);
    ComputerDevice save(ComputerDevice computerDevice);
    ComputerDevice update(ComputerDevice computerDevice);
    void delete(Long id);
}
