# Project README

## Overview
This JavaFX application provides a banking interface with separate functionalities for **Managers** and **Customers**. The Manager can add and delete customer accounts, while customers can log in to check their balance, deposit and withdraw funds, and make purchases. The project is structured to provide a simple login screen with different views based on the user's role.

## Features

1. **Login Interface**:
   - Users can log in as either a **Manager** or a **Customer**.
   - Username and password input fields are validated based on role-specific requirements.

2. **Manager Functions**:
   - **Add New Account**: Adds new customer accounts with unique usernames.
   - **Delete Old Account**: Removes an existing customer account from the system.

3. **Customer Functions**:
   - **Check Balance**: Displays the current balance.
   - **Deposit Money**: Allows deposits into the customer's account.
   - **Withdraw Money**: Allows withdrawals from the customer's account.
   - **Make a Purchase**: Enables customers to make a purchase (with a minimum amount required for successful transactions).

## Project Structure
- `Main.java`: This file serves as the entry point and main interface.
  - **Login Screen**: Takes user input and authenticates against a Manager or Customer account.
  - **Manager Page**: Allows Managers to add or delete customer accounts.
  - **Customer Page**: Displays options for Customers to check their balance, deposit, withdraw, and make purchases.

- `Manager.java`: Singleton class that holds and manages customer accounts and handles manager-specific operations.
- `Customer.java`: Represents individual customer accounts, storing username, password, and balance details.

## How to Run
1. Clone or download the repository.
2. Open the project in an IDE that supports JavaFX (e.g., IntelliJ IDEA, Eclipse).
3. Ensure JavaFX is configured.
4. Run the `Main` class to start the application.

## How to Use
1. **Log In**: Enter a username, password, and role (`Manager` or `Customer`).
   - Use `admin` as the username to log in as the Manager.
2. **Manager Options**:
   - **Add Account**: Enter a unique username and password for a new customer account.
   - **Delete Account**: Select a customer username from the list to delete.
3. **Customer Options**:
   - **Check Balance**: View the balance of the account.
   - **Deposit/Withdraw**: Add or withdraw funds from the account.
   - **Make a Purchase**: Enter a purchase amount and confirm if it meets the minimum requirement.

## Future Improvements
- Add password encryption for increased security.
- Implement a more robust error handling mechanism.
- Allow Managers to update customer account details.

## Requirements
- Java 8 or higher
- JavaFX
