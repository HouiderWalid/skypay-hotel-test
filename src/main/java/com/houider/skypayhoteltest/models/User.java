package com.houider.skypayhoteltest.models;

import com.houider.skypayhoteltest.exceptions.InsufficientBalanceException;

public class User {

    private int id;
    private int balance = 0;

    public User(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void printAllDetails() {
        System.out.println("User ID: " + this.getId());
        System.out.println("User Balance: " + this.getBalance());
    }

    public void deductBalance(int amount) {

        if (amount > this.balance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        this.balance -= amount;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }
}
