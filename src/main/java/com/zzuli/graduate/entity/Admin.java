package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@Data
public class Admin {
    @Id
    private String username;
    private String password;
    private String role;

    public Admin() {}
    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}