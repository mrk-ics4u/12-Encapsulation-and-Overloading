/*
 * ThermostatTest.java -- unit tests for the Lesson 12 exercise
 * ICS 4U0 - Lesson 12 Exercise: Thermostat
 *
 * These tests run ThermostatPanel.main() exactly as it will be graded: they
 * type the six input lines on System.in and check the lines it prints to
 * System.out. ThermostatPanel only works if your Thermostat class does, so
 * every failure here points at Thermostat.java. You don't need to change
 * this file -- just run the tests (see README.md) and fix Thermostat.java
 * until they all pass.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThermostatTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private Locale originalLocale;

    @BeforeEach
    public void redirectIoAndPinLocale() {
        originalIn = System.in;
        originalOut = System.out;
        originalLocale = Locale.getDefault();
        // String.format("%.1f") follows the default locale -- on a machine
        // that uses a comma for decimals, output like "21,0" would fail the
        // exercise for reasons that have nothing to do with your code, so
        // every test pins the locale before calling main().
        Locale.setDefault(Locale.CANADA);
    }

    @AfterEach
    public void restoreIoAndLocale() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Locale.setDefault(originalLocale);
    }

    /**
     * Feeds {@code input} to ThermostatPanel.main() on System.in and returns
     * everything it printed to System.out.
     */
    private String run(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));

        ThermostatPanel.main(new String[0]);

        return captured.toString(StandardCharsets.UTF_8);
    }

    /**
     * Compares output line by line, trimming trailing whitespace on each
     * line and ignoring one blank line at the very end (println's final
     * newline). Everything else -- including every space, period, and letter -- is
     * compared exactly.
     */
    private void assertOutputEquals(String expected, String actual) {
        String[] expectedLines = expected.stripTrailing().split("\n", -1);
        String[] actualLines = actual.stripTrailing().split("\n", -1);

        assertEquals(expectedLines.length, actualLines.length,
                "Expected " + expectedLines.length + " line(s) of output, got " + actualLines.length
                        + ".\n--- expected ---\n" + expected + "--- actual ---\n" + actual);

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].stripTrailing(), actualLines[i].stripTrailing(),
                    "Line " + (i + 1) + " didn't match.\n--- expected ---\n" + expected
                            + "--- actual ---\n" + actual);
        }
    }

    @Test
    public void exampleFromReadme() {
        String input = "Kitchen\nBedroom\n18\n22\n2\n1.5\n";
        String expected = "Kitchen set to 21.0 C\n"
                + "Bedroom set to 18.0 C\n"
                + "Kitchen set to 23.0 C\n"
                + "Bedroom set to 19.5 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void oneArgumentConstructorStartsAt21() {
        // The one-argument constructor must set the target to 21.0. Leaving it
        // out gives the default value, 0.0.
        String input = "Porch\nCellar\n16\n40\n0\n0\n";
        String expected = "Porch set to 21.0 C\n"
                + "Cellar set to 16.0 C\n"
                + "Porch set to 21.0 C\n"
                + "Cellar set to 16.0 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void constructorRejectsTargetOutOfRange() {
        // 35 is out of range, so the two-argument constructor uses 21.0 instead.
        // The constructor has to validate too, not just setTarget.
        String input = "Office\nGarage\n35\n21\n0\n0\n";
        String expected = "Office set to 21.0 C\n"
                + "Garage set to 21.0 C\n"
                + "Office set to 21.0 C\n"
                + "Garage set to 21.0 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void setTargetIgnoresOutOfRange() {
        // setTarget(10) is out of range, so the target stays at 21.0 and the
        // one raise() takes it to 21.5.
        String input = "Den\nAttic\n20\n10\n1\n2\n";
        String expected = "Den set to 21.0 C\n"
                + "Attic set to 20.0 C\n"
                + "Den set to 21.5 C\n"
                + "Attic set to 22.0 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void boundariesAreValid() {
        // 15.0 and 30.0 are both allowed. Using > and < instead of >= and <=
        // rejects them.
        String input = "Sunroom\nBasement\n30\n15\n0\n0\n";
        String expected = "Sunroom set to 21.0 C\n"
                + "Basement set to 30.0 C\n"
                + "Sunroom set to 15.0 C\n"
                + "Basement set to 30.0 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void raiseCannotGoAbove30() {
        // The second raise() would make 30.5, and raise(6) would make 31.0.
        // Both are refused. Have raise use setTarget (or isValid) instead of
        // adding to target directly.
        String input = "Studio\nHall\n25\n29.5\n2\n6\n";
        String expected = "Studio set to 21.0 C\n"
                + "Hall set to 25.0 C\n"
                + "Studio set to 30.0 C\n"
                + "Hall set to 25.0 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void raiseByNegativeAmountLowersTarget() {
        // raise(-2.5) lowers the target. Don't reject negative amounts, only
        // results outside 15.0 to 30.0.
        String input = "Nursery\nLounge\n18\n21\n0\n-2.5\n";
        String expected = "Nursery set to 21.0 C\n"
                + "Lounge set to 18.0 C\n"
                + "Nursery set to 21.0 C\n"
                + "Lounge set to 15.5 C\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void toStringShowsOneDecimal() {
        // 20.25 must print as 20.3. Build the text with String.format and %.1f,
        // not by adding the double to a String.
        String input = "Library\nGym\n20\n22\n1\n0.25\n";
        String expected = "Library set to 21.0 C\n"
                + "Gym set to 20.0 C\n"
                + "Library set to 22.5 C\n"
                + "Gym set to 20.3 C\n";
        assertOutputEquals(expected, run(input));
    }
}
