package coe528.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Customer {

    protected Account account;
    private CustomerLevel state;
    private String username, password, role, level;

    public Customer() {

    }

    public Customer(String name, String motdepasse) {
        account = new Account();
        updateLevel();
        username = name;
        password = motdepasse;
        role = "customer";
        try {
            FileWriter w = new FileWriter(username);
            w.write(username + "\n" + password + "\n" + role + "\n" + this.account.toString() + "\n" + level);
            w.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean login(String name, String motdepasse, String title) {
        title = title.toLowerCase();
        String line;
        boolean check = false;
        String info[] = {name, motdepasse, title};
        int i = 0;
        try (BufferedReader r = new BufferedReader(new FileReader(username))) {
            while ((line = r.readLine()) != null) {
                if (i == 3) {
                    break;
                }
                if (line.equals(info[i])) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
                i++;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            return check;
        }
    }

    public void purchase(double amount) {
        state.purchase(this, amount);
        updateLevel();
    }

    public double getBalance() {
        return account.getAmount();
    }

    public void withdraw(double money) {
        account.withdraw(money);
        updateLevel();
    }

    public void deposit(double money) {
        account.deposit(money);
        updateLevel();
    }

    public void setState(CustomerLevel state) {
        this.state = state;
    }

    public void updateLevel() {
        if (getBalance() < 10000) {
            setState(new Silver());
            level = "silver";
        } else if (getBalance() < 20000) {
            setState(new Gold());
            level = "gold";
        } else {
            setState(new Platinum());
            level = "platinum";
        }
    }

    public String getUsername() {
        return username;
    }

    public String getBalanceString() {
       return account.toString();
    }

}
