package com.app.springfx.infraestructure.database;

import com.app.springfx.domain.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    @Query("SELECT u FROM Technician u WHERE u.surname = ?1 AND u.password = ?2")
    Optional<Technician> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM Technician u WHERE u.surname = ?1")
    Optional<Technician> findByUsername(String username);
}
