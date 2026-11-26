[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=alu0101679525_ThreeInARow)

## ThreeInARow

This is a basic Java implementation of the Three in a Row and Tic-Tac-Toe games.

### Requirements

- Java Development Kit (JDK) 17 or later.
- Apache Maven.

Run all Maven commands from the project root directory, where `pom.xml` is located.

### How to build and test

Compile the project:

```bash
mvn clean compile
```

Run the unit tests:

```bash
mvn test
```

The JaCoCo coverage report is generated at `target/site/jacoco/index.html` when the tests run.

Run the full verification, including tests, Checkstyle, and the JaCoCo coverage check:

```bash
mvn verify
```

The verification requires at least 70% line coverage. Checkstyle uses the rules in `checkstyle.xml`; violations fail the verification build.

### Javadoc

Generate the Javadoc documentation with:

```bash
mvn javadoc:javadoc
```

Open `target/site/apidocs/index.html` to view the generated documentation.

### How to run

Build the executable JAR first:

```bash
mvn package
```

Run the default game (Three in a Row):

```bash
java -jar target/threeinarow-1.0.0-SNAPSHOT.jar
```

`RowGameApp` accepts an optional argument to select the game:

- `ThreeInARow` — Three in a Row (the default).
- `TicTacToe` — Tic-Tac-Toe.

For example, to run Tic-Tac-Toe:

```bash
java -jar target/threeinarow-1.0.0-SNAPSHOT.jar TicTacToe
```

### How to clean up

Remove generated build files with:

```bash
mvn clean
```
