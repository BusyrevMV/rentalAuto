package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.model.entity.ProgUser;
import com.ertelecom.carrental.repository.ProgUserRepository;
import com.ertelecom.carrental.service.IProgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service("IProgUserService")
public class ProgUserServiceImpl implements IProgUserService {
    @Autowired
    private ProgUserRepository progUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<ProgUser> load(){
        List<ProgUser> list = progUserRepository.findAll();
        return list;
    }

    @Override
    public Optional<ProgUser> loadById(Long id) {
        return progUserRepository.findById(id);
    }

    @Override
    public ProgUser save(ProgUser progUser) throws SQLException {
        if (progUser.getId() == null){
            if (progUser.getPassword() == null || progUser.getPassword().isEmpty()){
                throw new SQLException("Пароль не может быть пустым");
            }
            progUser.setPassword(passwordEncoder.encode(progUser.getPassword()));
        } else {
            progUser.setPassword(loadById(progUser.getId()).get().getPassword());
        }

        progUserRepository.save(progUser);
        return progUser;
    }

    @Override
    public boolean deleteById(long id){
        if (progUserRepository.findById(id).isPresent()) {
            progUserRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public Optional<ProgUser> findByLoginAndPassword(String login, String password) {
        Optional<ProgUser> progUser = progUserRepository.findByUsername(login);
        if (progUser.isPresent()) {
            if (passwordEncoder.matches(password, progUser.get().getPassword())) {
                return progUser;
            }
        }

        return Optional.ofNullable(null);
    }

    @Override
    public ProgUser loadUserByUsername(String login) throws UsernameNotFoundException {
        return progUserRepository.findByUsername(login).orElse(null);
    }
}
