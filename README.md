
# FP-Core-Utilities



A lightweight Java library providing fundamental functional programming constructs like `Result` for explicit error handling, `Failure` for structured exception representation, and `Pair` for simple tuple-like data. Designed to promote more robust and readable code by reducing reliance on traditional exception throwing for control flow.

## Table of Contents

* [Features](https://www.google.com/search?q=%23features)
* [Installation](https://www.google.com/search?q=%23installation)
* [Core Concepts](https://www.google.com/search?q=%23core-concepts)
    * [Result\<T\>](https://www.google.com/search?q=%23resultt)
    * [Failure](https://www.google.com/search?q=%23failure)
    * [Pair\<L, R\>](https://www.google.com/search?q=%23pairl-r)
* [Usage](https://www.google.com/search?q=%23usage)
    * [Using `Result` for Operations](https://www.google.com/search?q=%23using-result-for-operations)
    * [Handling `Result` Outcomes](https://www.google.com/search?q=%23handling-result-outcomes)
    * [Transforming `Result`s with `map` and `flatMap`](https://www.google.com/search?q=%23transforming-results-with-map-and-flatmap)
    * [Working with `Failure` Subtypes](https://www.google.com/search?q=%23working-with-failure-subtypes)
    * [Using `Pair`](https://www.google.com/search?q=%23using-pair)
* [Project Structure](https://www.google.com/search?q=%23project-structure)
* [Contributing](https://www.google.com/search?q=%23contributing)
* [License](https://www.google.com/search?q=%23license)

## Features

* **Explicit Error Handling:** `Result` forces you to consider success and failure paths, leading to more robust code.
* **Structured Failures:** The `Failure` class provides a consistent way to wrap `Throwable`s and custom error messages.
* **Type-Safe Tuples:** `Pair` offers a simple, immutable way to return two related values from a method.
* **Fluent API:** `Result`'s `map`, `flatMap`, `ifOk`, `ifFailure` methods enable elegant functional programming style.
* **Lightweight:** Minimal dependencies, easy to integrate.
* **Immutable Design:** All core classes (`Result`, `Failure`, `Pair`) are immutable, promoting thread-safety and predictability.

## Installation

This library is available on [Maven Central](https://www.google.com/search?q=https://search.maven.org/search%3Fq%3Dg:io.github.veerakumarak.fp).

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.veerakumarak</groupId>
    <artifactId>fp</artifactId>
    <version>5.1.2</version>
</dependency>
```

### Gradle

Add the following dependency to your `build.gradle`:

```gradle
implementation 'io.github.veerakumarak:fp:4.0.0'
```

## Core Concepts

### Result\<T\>

`Result<T>` is a sealed type (conceptually, implemented as a class with private constructors) that represents either a successful outcome containing a value of type `T` (an "Ok" state) or a failure containing a `Failure` object (a "Failure" state). It forces you to handle both possibilities.

**Key methods:**

* `Result.ok(T value)`: Creates a successful `Result`.
* `Result.failure(Failure failure)` / `Result.failure(String message)`: Creates a failed `Result`.
* `Result.of(Supplier<T> supplier)`: Executes a `Supplier` and wraps its return value in an `Ok` `Result` or any thrown `Throwable` in a `Failure` `Result`.
* `isOk()`, `isFailure()`: Check the state.
* `get()`, `expect(String message)`: Retrieve the value, throwing an exception if it's a failure.
* `orElse(T defaultValue)`, `orElseGet(Supplier<T> supplier)`: Provide a default value on failure.
* `orElseThrow()`, `orElseThrow(Supplier<? extends X> exceptionSupplier)`: Throw the contained `Failure` or a custom exception on failure.
* `map(Function<T, U> mapper)`: Transform the value if successful, propagating failure.
* `flatMap(Function<T, Result<U>> mapper)`: Chain operations that also return `Result`s.
* `ifOk(Consumer<T> action)`, `ifFailure(Consumer<Failure> action)`: Perform side effects based on the state.

### Failure

`Failure` is a `RuntimeException` subclass designed to be the consistent container for all failure information within the `Result` type. It wraps an optional underlying `Throwable` cause and provides structured messages.

**Key methods:**

* `Failure.empty()`: A singleton instance representing no failure (used internally by `Result` for its `Ok` state).
* `Failure.with(String message)`: Creates a `Failure` with a simple message.
* `Failure.wrap(String message, Throwable cause)`: Wraps an existing `Throwable` with an optional custom message.
* `Failure.wrap(String message, Failure failure)`: Wraps another `Failure` instance.
* `getMessage()`, `getCause()`: Access failure details.
* `isEq(Class<? extends Failure> failureClass)` / `isFailureOfType()`: Check if a `Failure` is of a specific subclass (e.g., `InvalidRequest`).

### Pair\<A, B\>

`Pair<A, B>` is a simple, immutable record (or class) that holds two elements of potentially different types, `A` (first) and `B` (second). It's a fundamental tuple for returning multiple values from a method without creating custom data classes for every scenario.

**Key methods:**

* `Pair.of(A first, B second)`: Static factory method to create a `Pair`.
* `getFirst()`, `getSecond()`: Accessors for the elements.

## Usage

### Using `Result` for Operations

Wrap any operation that might fail into a `Result`.

```java
import io.github.veerakumarak.fp.Result;
import io.github.veerakumarak.fp.Failure;
import io.github.veerakumarak.fp.failures.NetworkFailure; // Assuming a custom failure type

public class ResultOperationExample {

  public static Result<String> fetchUserData(String userId) {
    if (userId == null || userId.isBlank()) {
      return Result.failure(Failure.with("User ID cannot be empty."));
    }
    if ("network_down".equals(userId)) {
      return Result.failure(new NetworkFailure("Failed to connect to user service.", 500));
    }
    if ("user_not_found".equals(userId)) {
      return Result.failure(Failure.with("User with ID '" + userId + "' not found."));
    }
    return Result.ok("User Data for " + userId);
  }

  public static void main(String[] args) {
    Result<String> data1 = fetchUserData("alice");
    System.out.println("Result for 'alice' is OK: " + data1.isOk()); // true

    Result<String> data2 = fetchUserData("network_down");
    System.out.println("Result for 'network_down' is OK: " + data2.isOk()); // false
    data2.ifFailure(failure -> System.out.println("Failure: " + failure.getMessage() + " (Cause: " + failure.getCause() + ")"));
  }
}
```

### Handling `Result` Outcomes

```java
import io.github.veerakumarak.fp.Result;
import io.github.veerakumarak.fp.Failure;
import io.github.veerakumarak.fp.failures.NetworkFailure;

import java.io.IOException;

public class ResultHandlingExample {

  public static Result<Integer> parseAndDivide(String numStr, int divisor) {
    return Result.of(() -> Integer.parseInt(numStr)) // Catches NumberFormatException
            .flatMap(num -> {
              if (divisor == 0) {
                return Result.failure(Failure.with("Cannot divide by zero."));
              }
              return Result.ok(num / divisor);
            });
  }

  public static void main(String[] args) {
    // Using ifOk and ifFailure for side effects
    parseAndDivide("10", 2)
            .ifOk(val -> System.out.println("Success: " + val))
            .ifFailure(failure -> System.err.println("Failed: " + failure.getMessage()));

    parseAndDivide("abc", 5)
            .ifOk(val -> System.out.println("Success: " + val))
            .ifFailure(failure -> System.err.println("Failed (parse failure): " + failure.getMessage()));

    parseAndDivide("10", 0)
            .ifOk(val -> System.out.println("Success: " + val))
            .ifFailure(failure -> System.err.println("Failed (division by zero): " + failure.getMessage()));

    // Using orElse for default values
    Integer resultOrDefault = parseAndDivide("invalid", 1).orElse(-1);
    System.out.println("Result or default: " + resultOrDefault); // -1

    // Using orElseThrow to re-enter exception flow
    try {
      Integer value = parseAndDivide("20", 4).orElseThrow(); // Throws the contained Failure
      System.out.println("Value from orElseThrow: " + value);
    } catch (Failure e) {
      System.err.println("Caught a Failure: " + e.getMessage());
    }

    // Using orElseThrow with a custom exception supplier
    try {
      Integer value = parseAndDivide("oops", 1).orElseThrow(() -> new IOException("Input format failure!"));
      System.out.println("Value from orElseThrow with custom exception: " + value);
    } catch (IOException e) {
      System.err.println("Caught custom IOException: " + e.getMessage());
    }
  }
}
```

### Transforming `Result`s with `map` and `flatMap`

```java
import io.github.veerakumarak.fp.Result;
import io.github.veerakumarak.fp.Failure;
import io.github.veerakumarak.fp.failures.NetworkFailure;

public class ResultTransformationExample {

  public static Result<String> getUserEmail(int userId) {
    if (userId == 123) return Result.ok("user123@example.com");
    if (userId == 404) return Result.failure(Failure.with("User not found"));
    return Result.failure(new NetworkFailure("DB connection failed", 500));
  }

  public static Result<Integer> getEmailLength(String email) {
    if (email == null) return Result.failure(Failure.with("Email is null"));
    return Result.ok(email.length());
  }

  public static void main(String[] args) {
    // Using map: transforms the value if successful
    Result<Integer> userId123EmailLength = getUserEmail(123)
            .map(email -> email.length()); // Directly maps String to Integer
    userId123EmailLength.ifOk(len -> System.out.println("Email length for user 123: " + len));

    // Using map on a failed Result: failure propagates
    Result<Integer> userId404EmailLength = getUserEmail(404)
            .map(email -> email.length());
    userId404EmailLength.ifFailure(err -> System.err.println("Map failed for user 404: " + err.getMessage()));

    // Using flatMap: for chaining operations that also return Results
    // Get user email, then get its length. If either step fails, the whole chain fails.
    Result<Integer> chainedLength = getUserEmail(123)
            .flatMap(email -> getEmailLength(email));
    chainedLength.ifOk(len -> System.out.println("Chained email length for user 123: " + len));

    Result<Integer> chainedLengthFailed = getUserEmail(404) // This fails first
            .flatMap(email -> getEmailLength(email));
    chainedLengthFailed.ifFailure(err -> System.err.println("Chained failed for user 404: " + err.getMessage()));
  }
}
```

### Working with `Failure` Subtypes

```java
import io.github.veerakumarak.fp.Failure;
import io.github.veerakumarak.fp.failures.IllegalArgument; // Assuming this is a Failure subclass
import io.github.veerakumarak.fp.failures.NetworkFailure;

public class FailureSubtypeExample {

  public static Failure getSpecificFailure(int code) {
    if (code == 1) return new IllegalArgument("Invalid input received.");
    if (code == 2) return new NetworkFailure("Connection timeout.", 504);
    return Failure.with("Unknown failure.");
  }

  public static void main(String[] args) {
    Failure failure1 = getSpecificFailure(1);
    System.out.println("Failure 1 is IllegalArgument: " + failure1.isEq(IllegalArgument.class)); // true

    Failure failure2 = getSpecificFailure(2);
    System.out.println("Failure 2 is NetworkFailure: " + failure2.isEq(NetworkFailure.class)); // true
    if (failure2.isEq(NetworkFailure.class)) {
      NetworkFailure ne = (NetworkFailure) failure2;
      System.out.println("Network Failure Status: " + ne.getStatusCode());
    }
  }
}
```

### Using `Pair`

```java
import io.github.veerakumarak.fp.Pair;

public class PairExample {

    public static Pair<String, Integer> getUserInfo(String userId) {
        // In a real app, this would fetch from a database or service
        if ("alice".equals(userId)) {
            return Pair.of("Alice Wonderland", 30);
        }
        return Pair.of("Unknown", -1);
    }

    public static void main(String[] args) {
        Pair<String, Integer> info = getUserInfo("alice");
        System.out.println("User Name: " + info.getFirst() + ", Age: " + info.getSecond());

        Pair<Double, String> coordinates = Pair.of(123.45, "North");
        System.out.println("Lat: " + coordinates.getFirst() + ", Direction: " + coordinates.getSecond());

        // Pairs are immutable
        // coordinates.setLeft(567.89); // Compile-time error or not allowed if Pair is record/immutable class
    }
}
```

## Project Structure

* `io.github.veerakumarak.fp`: Contains the core `Result`, `Failure`, and `Pair` classes.
* `io.github.veerakumarak.fp.failures`: Contains specific `Failure` subclasses (e.g., `IllegalArgument`, `InternalFailure`, etc.).

## Contributing

Contributions are welcome\! If you have suggestions for improvements, new features, or bug fixes, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](https://www.google.com/search?q=LICENSE) file for details.