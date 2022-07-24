package com.suttori.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Component
public class User {

    private int id;
    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 100, message = "First name should be between 2 and 100 characters")
    private String firstName;
    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 100, message = "First name should be between 2 and 100 characters")
    private String lastName;
    @Min(value = 1, message = "Amount of money should be greater than 0")
    private int amountOfMoney;

    public User() {
    }

    public User(int id, String firstName, String lastName, int amountOfMoney) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountOfMoney = amountOfMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
}