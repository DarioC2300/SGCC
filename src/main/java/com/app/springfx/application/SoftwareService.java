package com.app.springfx.application;

import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.domain.Software;

import java.util.List;

public interface SoftwareService {
    List<Software> getAll();
    Software getById(Long id);
    Software save(Software software);
    Software update(Software software);
    void delete(Long id);
}
