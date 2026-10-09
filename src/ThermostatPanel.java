/*
 * Name:        Lesson 12: Encapsulation and Overloading (ThermostatPanel.java)
 * Description: The program that uses your Thermostat class. Don't change
 *              this file. It reads the input, creates two Thermostat
 *              objects, changes their targets, and prints them.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-06
 */
import java.util.Scanner;

public class ThermostatPanel {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String firstRoom = input.nextLine().trim();
        String secondRoom = input.nextLine().trim();
        double secondStart = Double.parseDouble(input.nextLine().trim());
        double newTarget = Double.parseDouble(input.nextLine().trim());
        int raises = Integer.parseInt(input.nextLine().trim());
        double amount = Double.parseDouble(input.nextLine().trim());

        // one constructor each
        Thermostat first = new Thermostat(firstRoom);
        Thermostat second = new Thermostat(secondRoom, secondStart);

        System.out.println(first);
        System.out.println(second);

        // first: set a new target, then raise() a number of times
        first.setTarget(newTarget);
        for (int i = 0; i < raises; i++) {
            first.raise();
        }

        // second: raise(amount) once
        second.raise(amount);

        System.out.println(first);
        System.out.println(second);
    }
}
