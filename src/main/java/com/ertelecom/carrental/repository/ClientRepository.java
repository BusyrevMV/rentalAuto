package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDriverNumber(String driverNumber);
    List<Client> findAll();
}
