/*
 * Name:        Lesson 12: Encapsulation and Overloading (EncapsulationDemo.java)
 * Description: Uses the BankAccount class in BankAccount.java: validation,
 *              overloaded constructors and methods, toString, and a static
 *              counter.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-06
 */

// The BankAccount class is in a separate file, BankAccount.java, in the same folder.
// Open both files side by side.

public class EncapsulationDemo {

    public static void main(String[] args) {

        // overloaded constructors: Java picks the one whose parameters match
        BankAccount ada = new BankAccount("Ada");
        BankAccount grace = new BankAccount("Grace", 250.0);
        BankAccount alan = new BankAccount("Alan", -100.0);    // rejected, starts at 0

        // println calls toString() for us
        System.out.println(ada);
        System.out.println(grace);
        System.out.println(alan);

        // validation: bad amounts are refused, and the method says so
        System.out.println("deposit 100:   " + ada.deposit(100.0) + "  -> " + ada);
        System.out.println("deposit -50:   " + ada.deposit(-50.0) + " -> " + ada);
        System.out.println("withdraw 500:  " + ada.withdraw(500.0) + " -> " + ada);
        System.out.println("withdraw 40:   " + ada.withdraw(40.0) + "  -> " + ada);

        // the return value lets the caller react
        if (!grace.withdraw(1000.0)) {
            System.out.println("Grace doesn't have $1000");
        }

        // overloaded method: deposit(int, int) instead of deposit(double)
        grace.deposit(12, 50);
        System.out.println("after deposit(12, 50): " + grace);

        // this doesn't compile: isPositive has private access in BankAccount
        // ada.isPositive(5.0);

        // static method: called on the class, not on one account
        System.out.println("accounts opened: " + BankAccount.getAccountsOpened());
    }

}
