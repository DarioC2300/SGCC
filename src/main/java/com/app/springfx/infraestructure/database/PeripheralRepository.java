package com.app.springfx.infraestructure.database;

import com.app.springfx.domain.Peripheral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheralRepository extends JpaRepository<Peripheral, Long> {
}
