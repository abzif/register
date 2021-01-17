package com.example.register.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * registry database entity mapping
 */
@Entity
@Table(name = "REGISTER")
public class Register implements Serializable {

    @Id
    @Column(name = "REG_NAME")
    private String name;
    @Column(name = "BALANCE")
    private int balance;

    protected Register() {
        // necessary for JPA
    }

    public Register(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }
}
