import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Transaction {
    private double amount;
    private String description;

    public Transaction(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction: " + description + ", Amount: " + amount;
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount, String description) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction(amount, description));
        }
    }

    public boolean withdraw(double amount, String description) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction(-amount, description));
            return true;
        }
        return false; // Withdrawal failed
    }
}

class Bank {
    private List<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(String accountNumber, String accountHolder) {
        BankAccount account = new BankAccount(accountNumber, accountHolder);
        accounts.add(account);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null; // Account not found
    }
}

public class BankManagementSystem {
    public static void main(String[] args) throws IOException {
        Bank bank = new Bank();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Menu
        while (true) {
            System.out.println("\n===== Bank of NYC =====");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Display Account Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.print("\nEnter Account Number: ");
                    String accountNumber = reader.readLine();
                    System.out.print("Enter Account Holder Name: ");
                    String accountHolder = reader.readLine();
                    bank.createAccount(accountNumber, accountHolder);
                    System.out.println("\nAccount created successfully.");
                    break;
                case 2:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = reader.readLine();
                    BankAccount depositAccount = bank.findAccount(accountNumber);
                    if (depositAccount != null) {
                        System.out.print("Enter Deposit Amount: $");
                        double amount = Double.parseDouble(reader.readLine());
                        System.out.print("Enter Description: ");
                        String description = reader.readLine();
                        depositAccount.deposit(amount, description);
                        System.out.println("\nDeposit successful.");
                    } else {
                        System.out.println("\nAccount not found.");
                    }
                    break;
                case 3:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = reader.readLine();
                    BankAccount withdrawAccount = bank.findAccount(accountNumber);
                    if (withdrawAccount != null) {
                        System.out.print("Enter Withdrawal Amount: $");
                        double amount = Double.parseDouble(reader.readLine());
                        System.out.print("Enter Description: ");
                        String description = reader.readLine();
                        if (withdrawAccount.withdraw(amount, description)) {
                            System.out.println("\nWithdrawal successful.");
                        } else {
                            System.out.println("\nWithdrawal failed. Insufficient funds.");
                        }
                    } else {
                        System.out.println("\nAccount not found.");
                    }
                    break;
                case 4:
                    System.out.print("\nEnter Account Number: ");
                    accountNumber = reader.readLine();
                    BankAccount displayAccount = bank.findAccount(accountNumber);
                    if (displayAccount != null) {
                        System.out.println("\n===== Account Details =====");
                        System.out.println("Account Holder: " + displayAccount.getAccountHolder());
                        System.out.println("Account Number: " + displayAccount.getAccountNumber());
                        System.out.println("Account Balance: $" + displayAccount.getBalance());

                        List<Transaction> transactions = displayAccount.getTransactions();
                        int numTransactionsToShow = Math.min(5, transactions.size());
                        System.out.println("\nLast 5 Transactions for " + displayAccount.getAccountNumber() + ":");
                        for (int i = transactions.size() - 1; i >= transactions.size() - numTransactionsToShow; i--) {
                            System.out.println(transactions.get(i));
                        }
                        System.out.println("===========================");
                    } else {
                        System.out.println("\nAccount not found.");
                    }
                    break;
                case 5:
                    System.out.println("\nThank you for banking with us!");
                    reader.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }
}
