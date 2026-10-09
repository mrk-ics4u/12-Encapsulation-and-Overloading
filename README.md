# ICS 4U0 — Lesson 12: Encapsulation and Overloading

## Exercise

You're writing the `Thermostat` class for a smart home app. Each thermostat controls one room, and it must never be set outside the safe range of **15.0 to 30.0** degrees. The control panel is already written, in `src/ThermostatPanel.java`. It creates two thermostats, changes them, and prints them. **It only works once your `Thermostat` class does.**

Write the class in `src/Thermostat.java` to match this UML diagram:

```
┌────────────────────────────────────────┐
│ Thermostat                             │
├────────────────────────────────────────┤
│ - room : String                        │
│ - target : double                      │
├────────────────────────────────────────┤
│ + Thermostat(room : String)            │
│ + Thermostat(room : String,            │
│              target : double)          │
│ + getRoom() : String                   │
│ + getTarget() : double                 │
│ + setTarget(target : double) : void    │
│ + raise() : void                       │
│ + raise(amount : double) : void        │
│ + toString() : String                  │
│ - isValid(temp : double) : boolean     │
└────────────────────────────────────────┘
```

Everything you need is from Lesson 12:

- **validation** in the constructor and setter,
- a **private helper method** (`isValid`, marked `-`),
- **overloaded** constructors and methods, and
- **`toString`** built with `String.format`.

| Method | What it does |
|---|---|
| `Thermostat(room)` | Sets the room. The target starts at 21.0. |
| `Thermostat(room, target)` | Sets the room and the target. If the target isn't valid, uses 21.0 instead. |
| `getRoom()`, `getTarget()` | Return the matching attribute. |
| `setTarget(target)` | Changes the target, only if the new one is valid. Otherwise nothing changes. |
| `raise()` | Raises the target by 0.5, only if the result is valid. |
| `raise(amount)` | Raises the target by `amount`, only if the result is valid. A negative amount lowers it. |
| `toString()` | Returns the room and target, like `Kitchen set to 21.0 C`, with the target to 1 decimal. |
| `isValid(temp)` | Returns `true` if `temp` is from 15.0 to 30.0, including 15.0 and 30.0. |

- `Thermostat.java` already has every header from the diagram. Add the attributes and fill in each body.
- Write the range check **once**, in `isValid`, and use it everywhere else.
- **Don't change `ThermostatPanel.java`**, and don't rename or remove any method in `Thermostat.java`.

---

## Input

Six lines from the keyboard (`ThermostatPanel` reads them for you):

| Line | Value | Type |
|------|-------|------|
| 1 | First room | text |
| 2 | Second room | text |
| 3 | Starting target for the second room | decimal number |
| 4 | New target for the first room (`setTarget`) | decimal number |
| 5 | Number of times to call `raise()` on the first room | whole number |
| 6 | Amount for `raise(amount)` on the second room | decimal number |

The first room is created with the one-parameter constructor, and the second with the two-parameter constructor.

Example input:

```
Kitchen
Bedroom
18
22
2
1.5
```

---

## Output

Exactly four lines: both thermostats when they're created, then both after the changes.

```
<first room> set to <target to 1 decimal> C
<second room> set to <target to 1 decimal> C
<first room> set to <target to 1 decimal> C
<second room> set to <target to 1 decimal> C
```

For the example input above, the program must print **exactly**:

```
Kitchen set to 21.0 C
Bedroom set to 18.0 C
Kitchen set to 23.0 C
Bedroom set to 19.5 C
```

`ThermostatPanel` prints these lines by calling your `toString`, so every difference comes from your `Thermostat` class. `Kitchen set to 21.0 C` and `Kitchen set to 21 C` are not the same answer.

---

## Testing

- Test your code yourself first.
- Open the **Testing** panel from the sidebar (flask icon) and click ▶ **Run Tests**.

A green check next to a test means it passed; a red X means it failed and will show you the expected vs. actual output.
