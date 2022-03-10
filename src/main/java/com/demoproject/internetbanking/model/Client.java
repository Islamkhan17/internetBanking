package com.demoproject.internetbanking.model;

import com.demoproject.internetbanking.util.Token;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "clients", uniqueConstraints = {@UniqueConstraint(columnNames = {"token"}, name = "unique_client_token_idx")})
public class Client extends BaseEntity{

    public Client() {
    }

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String patronymic;
    @NotNull
    private Long iin;
    @NotNull
    private Long phone;
    @NotNull
    private String password;
    @NotNull
    private String address;
    @NotNull
    private String token;

    public Client(String name, String lastName, String patronymic, Long iin, Long phone, String password, String address, String token) {
        super(null);
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.iin = iin;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getIin() {
        return iin;
    }

    public void setIin(Long iin) {
        this.iin = iin;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
