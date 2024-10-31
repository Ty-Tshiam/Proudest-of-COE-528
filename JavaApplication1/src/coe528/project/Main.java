package coe528.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Manager manager = Manager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(5);
        grid.setHgap(5);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        GridPane.setConstraints(passwordField, 1, 1);

        Label roleLabel = new Label("Role:");
        GridPane.setConstraints(roleLabel, 0, 2);
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Manager", "Customer");
        roleComboBox.setValue("Customer");
        GridPane.setConstraints(roleComboBox, 1, 2);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();
            boolean logM = false, logC = false;
            Customer consumer = new Customer();
            if (username.equals("admin")) {
                logM = manager.login(username, password, role);
            } else {
                for (Customer customer : manager.customers) {
                    logC = customer.login(username, password, role);
                    consumer = customer;
                    if (logC) {
                        break;
                    }
                }
            }

            if (logM) {
                openManagerPage(primaryStage);
            } else if (logC) {
                openCustomerPage(primaryStage, consumer);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username, password, or role. Please try again.");
                alert.showAndWait();
            }

        });

        grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, roleLabel, roleComboBox, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openManagerPage(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        Label welcomeLabel = new Label("Welcome, Manager!");
        Button addAccountButton = new Button("Add New Account");
        addAccountButton.setOnAction(e -> openAddAccountPage(primaryStage));
        Button deleteAccountButton = new Button("Delete Old Account");
        deleteAccountButton.setOnAction(e -> openDeleteAccountPage(primaryStage));
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        layout.getChildren().addAll(welcomeLabel, addAccountButton, deleteAccountButton, logoutButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    private void openCustomerPage(Stage primaryStage, Customer loggedInCustomer) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + loggedInCustomer.getUsername() + "!");
        Label balanceLabel = new Label();

        Button checkBalanceButton = new Button("Check Balance");
        checkBalanceButton.setOnAction(e -> {
            balanceLabel.setText("Your balance is: $" + loggedInCustomer.getBalanceString());
        });

        Button depositButton = new Button("Deposit Money");
        depositButton.setOnAction(e -> openDepositPage(primaryStage, loggedInCustomer));

        Button withdrawButton = new Button("Withdraw Money");
        withdrawButton.setOnAction(e -> openWithdrawPage(primaryStage, loggedInCustomer));

        Button purchaseButton = new Button("Make a Purchase");
        purchaseButton.setOnAction(e -> openPurchasePage(primaryStage, loggedInCustomer));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));

        layout.getChildren().addAll(welcomeLabel, checkBalanceButton, balanceLabel, depositButton, withdrawButton, purchaseButton, logoutButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
    }

    private void openPurchasePage(Stage primaryStage, Customer loggedInCustomer) {
        VBox purchaseLayout = new VBox(10);
        purchaseLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Make a Purchase");
        purchaseLayout.getChildren().add(titleLabel);

        Label amountLabel = new Label("Enter Purchase Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        purchaseLayout.getChildren().addAll(amountLabel, amountField);

        Button purchaseButton = new Button("Purchase");
        purchaseButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            loggedInCustomer.purchase(amount);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Make a Purchase");
            alert.setHeaderText(null);
            if (amount >= 50) {
                alert.setContentText("Purchase successful!");
            } else {
                alert.setContentText("Purchase needs to be at least $50");
            }
            alert.showAndWait();

        });
        purchaseLayout.getChildren().add(purchaseButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openCustomerPage(primaryStage, loggedInCustomer));
        purchaseLayout.getChildren().add(backButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        purchaseLayout.getChildren().add(logoutButton);

        Scene purchaseScene = new Scene(purchaseLayout, 300, 300);
        primaryStage.setScene(purchaseScene);
    }

    private void openWithdrawPage(Stage primaryStage, Customer loggedInCustomer) {
        VBox withdrawLayout = new VBox(10);
        withdrawLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Withdraw Money");
        withdrawLayout.getChildren().add(titleLabel);

        Label amountLabel = new Label("Enter Amount to Withdraw:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        withdrawLayout.getChildren().addAll(amountLabel, amountField);

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            loggedInCustomer.withdraw(amount);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Withdraw Money");
            alert.setHeaderText(null);
            if (amount <= loggedInCustomer.getBalance() && amount >= 0) {
                alert.setContentText("Withdrawal successful!");
            } else {
                alert.setContentText("Invalid withdrawal");
            }
            alert.showAndWait();

        });
        withdrawLayout.getChildren().add(withdrawButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openCustomerPage(primaryStage, loggedInCustomer));
        withdrawLayout.getChildren().add(backButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        withdrawLayout.getChildren().add(logoutButton);

        Scene withdrawScene = new Scene(withdrawLayout, 300, 300);
        primaryStage.setScene(withdrawScene);
    }

    private void openDepositPage(Stage primaryStage, Customer loggedInCustomer) {
        VBox depositLayout = new VBox(10);
        depositLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Deposit Money");
        depositLayout.getChildren().add(titleLabel);

        Label amountLabel = new Label("Enter Amount to Deposit:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        depositLayout.getChildren().addAll(amountLabel, amountField);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                loggedInCustomer.deposit(amount);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Deposit Money");
                alert.setHeaderText(null);
                if (amount >= 0) {
                    alert.setContentText("Deposit Successful!");
                } else {
                    alert.setContentText("Deposit Unsuccessful");
                }
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Deposit Money");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid amount!");
                alert.showAndWait();
            }
        });
        depositLayout.getChildren().add(depositButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openCustomerPage(primaryStage, loggedInCustomer));
        depositLayout.getChildren().add(backButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        depositLayout.getChildren().add(logoutButton);

        Scene depositScene = new Scene(depositLayout, 300, 300);
        primaryStage.setScene(depositScene);
    }

    private void openDeleteAccountPage(Stage primaryStage) {
        VBox deleteAccountLayout = new VBox(10);
        deleteAccountLayout.setPadding(new Insets(20));

        Label deleteLabel = new Label("Select an account to delete:");
        deleteAccountLayout.getChildren().add(deleteLabel);

        ListView<String> accountListView = new ListView<>();
        for (Customer customer : manager.customers) {
            accountListView.getItems().add(customer.getUsername());
        }
        deleteAccountLayout.getChildren().add(accountListView);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String selectedUsername = accountListView.getSelectionModel().getSelectedItem();
            if (selectedUsername != null) {
                Customer selectedCustomer = null;
                for (Customer customer : manager.customers) {
                    if (customer.getUsername().equals(selectedUsername)) {
                        selectedCustomer = customer;
                        break;
                    }
                }
                if (selectedCustomer != null) {
                    manager.deleteCustomer(selectedCustomer);
                    accountListView.getItems().remove(selectedUsername);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Account");
                    alert.setHeaderText(null);
                    alert.setContentText("Account deleted successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Account");
                    alert.setHeaderText(null);
                    alert.setContentText("Selected account not found!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Account");
                alert.setHeaderText(null);
                alert.setContentText("Please select an account to delete!");
                alert.showAndWait();
            }
        });
        deleteAccountLayout.getChildren().add(deleteButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openManagerPage(primaryStage));
        deleteAccountLayout.getChildren().add(backButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> start(primaryStage));
        deleteAccountLayout.getChildren().add(logoutButton);

        Scene deleteAccountScene = new Scene(deleteAccountLayout, 300, 300);
        primaryStage.setScene(deleteAccountScene);
    }

    private void openAddAccountPage(Stage primaryStage) {
        VBox addAccountLayout = new VBox(10);
        addAccountLayout.setPadding(new Insets(20));

        Label successLabel = new Label("");
        successLabel.setVisible(false);

        GridPane addAccountGrid = new GridPane();
        addAccountGrid.setPadding(new Insets(10, 10, 10, 10));
        addAccountGrid.setVgap(5);
        addAccountGrid.setHgap(5);

        Label newUsernameLabel = new Label("New Username:");
        GridPane.setConstraints(newUsernameLabel, 0, 0);
        TextField newUsernameField = new TextField();
        newUsernameField.setPromptText("Enter new username");
        GridPane.setConstraints(newUsernameField, 1, 0);

        Label newPasswordLabel = new Label("New Password:");
        GridPane.setConstraints(newPasswordLabel, 0, 1);
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new password");
        GridPane.setConstraints(newPasswordField, 1, 1);

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 1, 2);
        addButton.setOnAction(e -> {
            String newUsername = newUsernameField.getText();
            String newPassword = newPasswordField.getText();
            boolean sameName = false;
            for (Customer consumer : manager.customers) {
                if (newUsername.equals(consumer.getUsername())) {
                    sameName = true;
                }
            }
            if (sameName) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists. Please choose a different username.");
                alert.showAndWait();
            } else {
                Customer newCustomer = new Customer(newUsername, newPassword);
                manager.addCustomer(newCustomer);
                successLabel.setText("Customer added successfully!");
                successLabel.setVisible(true);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> openManagerPage(primaryStage));
        GridPane.setConstraints(backButton, 0, 2);

        addAccountGrid.getChildren().addAll(newUsernameLabel, newUsernameField, newPasswordLabel, newPasswordField, addButton, backButton);

        addAccountLayout.getChildren().addAll(addAccountGrid, successLabel);

        Scene addAccountScene = new Scene(addAccountLayout, 300, 200);
        primaryStage.setScene(addAccountScene);
    }

}
