package com.ertelecom.carrental.repository;

import com.ertelecom.carrental.model.entity.ProgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgUserRepository extends JpaRepository<ProgUser, Long> {
    Optional<ProgUser> findByUsername(String login);
    List<ProgUser> findAll();
}
