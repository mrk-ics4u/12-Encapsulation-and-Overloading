/*
 * Name:        Lesson 12: Encapsulation and Overloading (BankAccount.java)
 * Description: A bank account that protects its balance: validation in the
 *              constructor and mutators, a private helper method,
 *              overloaded constructors and methods, toString, and a static
 *              counter. Used by EncapsulationDemo.java.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-06
 */
public class BankAccount {

    // instance variables: every account has its own
    private String owner;
    private double balance;
    

    // class variable: ONE copy, shared by every account
    private static int accountsOpened = 0;

    // constructor 1: new account with no money in it
    public BankAccount(String owner) {
        this.owner = owner;
        this.balance = 0;
        accountsOpened++;
    }

    // constructor 2 (overloaded): new account with a starting balance
    public BankAccount(String owner, double balance) {
        this.owner = owner;
        if (balance >= 0) {
            this.balance = balance;
        } else {
            this.balance = 0;     // a negative starting balance isn't allowed
        }
        accountsOpened++;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    // no setBalance: the balance only changes through deposit and withdraw

    // adds money if the amount is positive, and reports whether it worked
    public boolean deposit(double amount) {
        if (isPositive(amount)) {
            balance = balance + amount;
            return true;
        }
        return false;
    }

    // overloaded: same name, different parameters. Reuses the other deposit.
    public boolean deposit(int dollars, int cents) {
        return deposit(dollars + cents / 100.0);
    }

    // removes money if the amount is positive and there's enough of it
    public boolean withdraw(double amount) {
        if (isPositive(amount) && amount <= balance) {
            balance = balance - amount;
            return true;
        }
        return false;
    }

    // what println and + use when they need this account as text
    public String toString() {
        return String.format("%s: $%.2f", owner, balance);
    }

    // static method: belongs to the class, so it can only use static variables
    public static int getAccountsOpened() {
        return accountsOpened;
    }

    // private helper: only this class needs it, so outside code can't call it
    private boolean isPositive(double amount) {
        return amount > 0;
    }
}
