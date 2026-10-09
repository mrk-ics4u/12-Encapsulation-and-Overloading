/*
 * Name:
 * Description:
 * Created by:
 * Last edited:
 */
public class Thermostat {

    // private attributes (see the UML diagram in README.md)


    // constructor 1: target starts at 21.0
    public Thermostat(String room) {

    }

    // constructor 2: uses the given target if it's valid, otherwise 21.0
    public Thermostat(String room, double target) {

    }

    // getters
    public String getRoom() {
        return "";    // replace this line
    }

    public double getTarget() {
        return 0;     // replace this line
    }

    // setter: only changes the target if the new one is valid
    public void setTarget(double target) {

    }

    // raises the target by 0.5, only if the result is valid
    public void raise() {

    }

    // raises the target by amount (which can be negative), only if the result is valid
    public void raise(double amount) {

    }

    // returns the thermostat as text, like "Kitchen set to 21.0 C"
    public String toString() {
        return "";    // replace this line
    }

    // private helper: true if temp is from 15.0 to 30.0, inclusive
    private boolean isValid(double temp) {
        return false; // replace this line
    }
}
