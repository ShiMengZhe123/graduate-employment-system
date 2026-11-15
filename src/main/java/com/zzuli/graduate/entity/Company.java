package com.zzuli.graduate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    private String creditCode;
    private String name;
    private String contactPhone;
    private String address;
    private String description;
    private boolean verified;

    public Company(String code, String name, String contactPhone, String address) {
        this.creditCode = code;
        this.name = name;
        this.contactPhone = contactPhone;
        this.address = address;
        this.description = "";
        this.verified = true;
    }
}