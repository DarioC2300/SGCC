package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;

import java.util.List;

public interface ComputerCenterService {
    List<ComputerCenter> getAll();
    ComputerCenter getById(Long id);
    ComputerCenter save(ComputerCenter computerCenter);
    ComputerCenter update(ComputerCenter computerCenter);
    void delete(Long id);
}
