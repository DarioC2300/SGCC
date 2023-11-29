package com.app.springfx.infraestructure.database;

import com.app.springfx.domain.ComputerCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerCenterRepository extends JpaRepository<ComputerCenter, Long> {
}
