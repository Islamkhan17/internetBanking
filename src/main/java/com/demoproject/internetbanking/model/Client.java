package com.demoproject.internetbanking.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "clients")
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
    private Integer iin;
    @NotNull
    private Integer phone;
    @NotNull
    private String password;
    @NotNull
    private String address;



    public Client(String name, String lastName, String patronymic, Integer iin, Integer phone, String password, String address) {
        super(null);
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.iin = iin;
        this.phone = phone;
        this.password = password;
        this.address = address;
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

    public Integer getIin() {
        return iin;
    }

    public void setIin(Integer iin) {
        this.iin = iin;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
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
}
