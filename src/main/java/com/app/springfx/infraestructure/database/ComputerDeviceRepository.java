package com.app.springfx.infraestructure.database;

import com.app.springfx.domain.ComputerDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerDeviceRepository extends JpaRepository<ComputerDevice, Long> {
}
