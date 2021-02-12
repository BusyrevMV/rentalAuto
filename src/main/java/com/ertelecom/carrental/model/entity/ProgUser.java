package com.ertelecom.carrental.model.entity;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Table(name = "proguser")
public class ProgUser implements UserDetails {

    @Id
    @SequenceGenerator(name = "proguserIdSeq", sequenceName = "proguser_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proguserIdSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    @Length(max = 100, message = "Поле \"Имя\" не может быть больше 100 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Поле \"Фамилия\" не может быть пустым")
    @Length(max = 100, message = "Поле \"Фамилия\" не может быть больше 100 символов")
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic")
    @Length(max = 100, message = "Поле \"Отчество\" не может быть больше 100 символов")
    private String patronymic;

    @NotBlank(message = "Поле \"Логин\" не может быть пустым")
    @Length(max = 30, message = "Поле \"Логин\" не может быть больше 30 символов")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgUser progUser = (ProgUser) o;
        return Objects.equals(id, progUser.id);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFullName(){
        return surname + " " + name + " " + patronymic != null ? patronymic : "";
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuths;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
