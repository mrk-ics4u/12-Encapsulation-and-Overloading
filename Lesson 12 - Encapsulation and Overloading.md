# Lesson 12 - Encapsulation and Overloading

Making attributes `private` stops other code from reaching in directly. But a setter that copies whatever it's given is no safer: `setBalance(-500)` does the same damage as `balance = -500`. **Encapsulation** means the class protects its own data, so every way in checks the value first.

This lesson builds a `BankAccount` that can't be put into a bad state, and adds a few tools that make classes easier to use: overloading, `toString`, and `static` attributes.

---

## 1. Validating in Mutators

A setter or other mutator should check a new value before using it. If the value is invalid, **leave the attribute unchanged**.

```java
public void setTarget(double target) {
    if (target >= 15.0 && target <= 30.0) {
        this.target = target;
    }
}
```

When the caller needs to know whether it worked, return a `boolean` instead of `void`:

```java
public boolean withdraw(double amount) {
    if (amount > 0 && amount <= balance) {
        balance = balance - amount;
        return true;
    }
    return false;
}
```

```java
if (!account.withdraw(1000.0)) {
    System.out.println("Not enough money");
}
```

Sometimes the right choice is no setter at all. `BankAccount` has no `setBalance`, so the only ways to change the balance are `deposit` and `withdraw`, and both check the amount.

---

## 2. Validating in the Constructor

The constructor is another way in. If it copies its parameters without checking, `new BankAccount("Alan", -100.0)` creates an account that's already broken.

```java
public BankAccount(String owner, double balance) {
    this.owner = owner;
    if (balance >= 0) {
        this.balance = balance;
    } else {
        this.balance = 0;
    }
}
```

An invalid starting value needs a sensible default, since the attribute has to be set to something.

---

## 3. Private Helper Methods

`deposit` and `withdraw` both check that the amount is positive. Instead of writing the check twice, put it in its own method:

```java
private boolean isPositive(double amount) {
    return amount > 0;
}
```

```java
public boolean deposit(double amount) {
    if (isPositive(amount)) {
        balance = balance + amount;
        return true;
    }
    return false;
}
```

It's `private` because no other class has a reason to call it. It's a piece of how `BankAccount` works, not something it offers. In UML a private method starts with `-`, the same as a private attribute.

Methods inside a class call each other directly, with no dot: `isPositive(amount)`, not `this.isPositive(amount)`.

The rule from Lesson 11 still holds: attributes private, methods public **unless only the class itself needs them**.

---

## 4. Overloading

Two methods (or constructors) can have the **same name** if their **parameter lists are different**. This is called **overloading**. Java picks the one whose parameters match the arguments in the call.

```java
public BankAccount(String owner) { ... }                    // starts at $0
public BankAccount(String owner, double balance) { ... }    // starts with a balance
```

```java
BankAccount ada = new BankAccount("Ada");             // first constructor
BankAccount grace = new BankAccount("Grace", 250.0);  // second constructor
```

Methods work the same way:

```java
public boolean deposit(double amount) { ... }

public boolean deposit(int dollars, int cents) {
    return deposit(dollars + cents / 100.0);    // reuses the other deposit
}
```

A method's **signature** is its name plus the types of its parameters, in order. Overloads must have different signatures. These two **don't compile** together, because only the return type is different:

```java
public double getBalance() { ... }
public int getBalance() { ... }      // error: already defined
```

Parameter names don't count either. `deposit(double amount)` and `deposit(double money)` have the same signature.

Once you write any constructor, Java no longer gives you the empty default one. `new BankAccount()` doesn't compile unless you write a constructor with no parameters.

---

## 5. toString

`System.out.println(ada)` has to turn the object into text. Without help, Java prints the class name and a code, like `BankAccount@1b6d3586`. Writing a `toString` method replaces that:

```java
public String toString() {
    return String.format("%s: $%.2f", owner, balance);
}
```

```java
System.out.println(ada);               // Ada: $60.00
String line = "Account " + ada;       // Account Ada: $60.00
```

`println` and `+` both call `toString()` for you. The header has to be exactly `public String toString()`, with no parameters.

`String.format` works like `printf`, but it returns the `String` instead of printing it. Use it whenever `toString` needs to round a `double`.

> **AP students:** writing your own `toString` is excluded from the AP exam (1.15.A.5). You still need to know that `+` with an object and a `String` calls the object's `toString`.

---

## 6. Static Attributes

A normal attribute has one copy **per object**. A `static` attribute has **one copy for the whole class**, shared by every object:

```java
private static int accountsOpened = 0;

public BankAccount(String owner) {
    this.owner = owner;
    this.balance = 0;
    accountsOpened++;           // every new account adds to the same counter
}
```

The code above means that every time a new `BankAccount` is created, the overall class (not the individual object(s)) counts one additional `accountsOpened`.  That's why `static` on `plays` in Lesson 11 made every song share one play count.

A `static` method belongs to the class too, so you call it on the class name, not on an object:

```java
public static int getAccountsOpened() {
    return accountsOpened;
}
```

```java
System.out.println(BankAccount.getAccountsOpened());    // 3
```

A `static` method can't use `owner` or `balance`, because it isn't running for any one account (or individual object). UML shows static members underlined. In a plain-text diagram we write `(static)` after them.

Adding `final` makes a value that can never change, a **constant**. Constants are named in capitals:

```java
private static final double MIN_TEMP = 15.0;
```

---

## 7. Common Encapsulation Bugs

**Validating in the setter but not the constructor.** `new Thermostat("Den", 99)` sets 99 even though `setTarget(99)` would refuse it.

**Changing an attribute directly where a validated method exists.** `target = target + 0.5;` in `raise()` skips the check. Call `setTarget(target + 0.5)` instead.

**`>` instead of `>=`.** A range "from 15 to 30" includes both ends.

**Overloads that differ only by return type.** Compile error: "method is already defined."

**Misspelling `toString`.** `tostring()` or `toString(String s)` is just another method, so `println` still prints `ClassName@1b6d3586`.

**Calling a private method from another class.** Compile error: "has private access."

---

## Try It Yourself

Run the companion file from the `src` folder:

```bash
javac EncapsulationDemo.java
java EncapsulationDemo
```

`EncapsulationDemo` uses the `BankAccount` class in `BankAccount.java`. Open both files side by side.

Then work on the exercise in `Thermostat.java`.

---

## Course Expectations

### ICS 4U

This is the final lesson for the Classes and Objects criteria.  Your teacher will update the Programming Test 1 document with specific *Look Fors*, and you should now be able to add this concept to your projects.

### AP Expectations

The following are expectations of the AP exam and will show up on the final exam:

- Instance variables should be `private` so the class controls access to them (data encapsulation); `private` methods can only be used inside the class (Topic 3.3, 3.3.A.1, 3.3.A.5, 3.3.A.6)
- The constructor sets the initial state and should give every instance variable a value; if no constructor is written, Java provides a default one (3.4.A.2, 3.4.A.4)
- A mutator method changes instance variables and is often `void`; a non-void method returns a value of its return type (3.5.A.2, 3.5.A.6)
- A method signature is the method name and the ordered list of parameter types; methods are overloaded when they have the same name but different signatures (1.9.A.2, 1.9.B.4)
- Constructors are overloaded when there are several with different signatures, and the one called is identified by its signature (1.13.A.1 to 1.13.A.3)
- Concatenating a `String` with an object calls the object's `toString` method; writing (overriding) `toString` is excluded from the exam (1.15.A.5)
- Class (`static`) variables belong to the class and are shared by all objects; public ones are accessed with the class name and the dot operator (3.7.B.1, 3.7.B.2)
- Class (`static`) methods can use class variables and other class methods, but not instance variables or instance methods without an object (3.7.A.1, 3.7.A.2)
- A variable declared `final` can't be changed (3.7.B.3)
