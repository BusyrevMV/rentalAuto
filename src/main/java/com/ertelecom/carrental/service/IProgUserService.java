package com.ertelecom.carrental.service;

import com.ertelecom.carrental.model.entity.ProgUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IProgUserService extends UserDetailsService {
    List<ProgUser> load();

    Optional<ProgUser> loadById(Long id);

    ProgUser save(ProgUser progUser) throws SQLException;

    boolean deleteById(long id);

    Optional<ProgUser> findByLoginAndPassword(String login, String password);
}
