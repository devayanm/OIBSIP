import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String userPin) {
        return this.userPin.equals(userPin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            System.out.println("Amount withdrawn: ₹" + amount);
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Invalid withdrawal amount!");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            System.out.println("Amount deposited: ₹" + amount);
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Amount transferred: ₹" + amount + " to " + recipient.getUserId());
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
        } else {
            System.out.println("Invalid transfer amount!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": ₹" + transaction.getAmount());
        }
    }
}

public class ATM {
    private List<Account> accounts;

    public ATM() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(String userId, String userPin, double initialBalance) {
        Account account = new Account(userId, userPin, initialBalance);
        accounts.add(account);
        System.out.println("Account created successfully!");
    }

    public Account login(String userId, String userPin) {
        for (Account account : accounts) {
            if (account.getUserId().equals(userId) && account.validatePin(userPin)) {
                System.out.println("Login successful!");
                return account;
            }
        }
        System.out.println("Invalid user ID or PIN!");
        return null;
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;
        Account loggedInAccount = null;

        while (!quit) {
            System.out.println("\n==== ATM MENU ====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer choice.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter user PIN: ");
                    String userPin = scanner.nextLine();
                    System.out.print("Enter initial balance: ");

                    double initialBalance = 0.0;
                    try {
                        initialBalance = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal balance.");
                        scanner.nextLine(); // Consume the invalid input
                        continue; // Restart the loop
                    }

                    atm.createAccount(userId, userPin, initialBalance);
                    break;
                case 2:
                    System.out.print("Enter user ID: ");
                    String loginId = scanner.nextLine();
                    System.out.print("Enter user PIN: ");
                    String loginPin = scanner.nextLine();
                    loggedInAccount = atm.login(loginId, loginPin);
                    if (loggedInAccount != null) {
                        boolean loggedIn = true;
                        while (loggedIn) {
                            System.out.println("\n==== ATM MENU ====");
                            System.out.println("1. Transactions History");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Deposit");
                            System.out.println("4. Transfer");
                            System.out.println("5. Quit");
                            System.out.print("Enter your choice: ");

                            int userChoice = 0;
                            try {
                                userChoice = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a valid integer choice.");
                                scanner.nextLine(); // Consume the invalid input
                                continue; // Restart the loop
                            }

                            switch (userChoice) {
                                case 1:
                                    loggedInAccount.printTransactionHistory();
                                    break;
                                case 2:
                                    System.out.print("Enter amount to withdraw: ");

                                    double withdrawAmount = 0.0;
                                    try {
                                        withdrawAmount = scanner.nextDouble();
                                        scanner.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input! Please enter a valid decimal amount.");
                                        scanner.nextLine(); // Consume the invalid input
                                        continue; // Restart the loop
                                    }

                                    loggedInAccount.withdraw(withdrawAmount);
                                    break;
                                case 3:
                                    System.out.print("Enter amount to deposit: ");

                                    double depositAmount = 0.0;
                                    try {
                                        depositAmount = scanner.nextDouble();
                                        scanner.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input! Please enter a valid decimal amount.");
                                        scanner.nextLine(); // Consume the invalid input
                                        continue; // Restart the loop
                                    }

                                    loggedInAccount.deposit(depositAmount);
                                    break;
                                case 4:
                                    System.out.print("Enter recipient's user ID: ");
                                    String recipientId = scanner.nextLine();
                                    Account recipientAccount = atm.login(recipientId, "");
                                    if (recipientAccount != null) {
                                        System.out.print("Enter amount to transfer: ");

                                        double transferAmount = 0.0;
                                        try {
                                            transferAmount = scanner.nextDouble();
                                            scanner.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input! Please enter a valid decimal amount.");
                                            scanner.nextLine(); // Consume the invalid input
                                            continue; // Restart the loop
                                        }

                                        loggedInAccount.transfer(recipientAccount, transferAmount);
                                    } else {
                                        System.out.println("Recipient's user ID not found!");
                                    }
                                    break;
                                case 5:
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                        }
                    }
                    break;
                case 3:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        scanner.close();
        System.out.println("Thank you for using the ATM!");
    }
}
