package coe528.project;
import java.text.DecimalFormat;

/*
Overview: This mutable class is resposible for tracking the 
balance in each customer's bank account. It can be withdrawn 
from and deposited into. It is represented as a double variable.

Abstraction function:
AF(c) = c, where c is a number as a double variable type 
to two decimal places

Representation Invariant:
RI(c) = c >= 0 && c is a double type
 */

public class Account {

    private double amount;

    //EFFECTS: returns the current amount of money in the account
    public double getAmount() {
        return amount;
    }

    //REQUIRES: money >= 0
    //MODIFIES: amount
    //EFFECTS: increases a certain aamount of money into the account
    public void deposit(double money) {
        if (money >= 0) {
            amount += money;
        }
    }

    //REQUIRES: money <= amount and money >= 0
    //MODIFIES: amount
    //EFFECTS: decreases a certain aamount of money from the account
    public void withdraw(double money) {
        if (amount >= money && money >= 0) {
            amount -= money;
        }
    }

    //MODIFIES: amount
    //EFFECTS: sets the amount in the account to 100
    public void newBalance() {
        amount = 100;
    }

    //EFFECTS: returns true if the rep invariant holds
    //holds for this; otherwise returns false
    public boolean repOk(){
        Object x = amount;
        if(!(x instanceof Double)){
            return false;
        }
        return amount >= 0;
    }
    
    //EFFECTS: returns the representation of the account class
    // and implements the abstraction function
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String num = df.format(amount);
        return num;
    }
}
