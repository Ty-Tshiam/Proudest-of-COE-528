package coe528.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Manager {

    private static Manager instance;
    ArrayList<Customer> customers = new ArrayList<>();
    private String username;
    private String password;
    private String role;

    private Manager() {
        username = "admin";
        password = "admin";
        role = "manager";
        try {
            FileWriter w = new FileWriter(username);
            w.write(username + "\n" + password + "\n" + role);
            w.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public boolean login(String name, String motdepasse, String title) {
        title = title.toLowerCase();
        String line;
        boolean check = false;
        String info[] = {name, motdepasse, title}; 
        int i = 0;
        try (BufferedReader r = new BufferedReader(new FileReader(name))) {
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

    public void addCustomer(Customer customer) {
        customer.account.newBalance();
        customers.add(customer);
    }

    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
}
