## ThreeInARow
This is a basic Java implementation of the Three in a Row game.

### How to build and test (from Terminal):

1. Make sure that you have Apache Ant installed. Run each ant command in the threeinarow folder, which contains the `build.xml` build file.

2. Run `ant document` to generate the javadoc (a hypertext description) for all the java classes. Generated hypertext description will be in the `jdoc` folder. Open the `index.html` file. 

3. Run `ant compile` to compile all the java classes. Compiled classes will be in the `bin` folder.

4. Run `ant test` to run all unit tests.

### How to run (from Terminal):

1. There are two choices of games which you can play. One is Tic-Tac-Toe, other is Three-In-A-Row. 
2. After building the project (i.e., running `ant`), run the following command in the threeinarow folder:
   `java -cp bin RowGameApp <Game>`. 
3. You need to pass `TicTacToe` or `ThreeInARow` in place of `<Game>` in the command line argument to choose the game you want to run.
4. By default, code runs with `ThreeInARow`.

### How to clean up (from Terminal):

1. Run `ant clean` to clean the project (i.e., delete all generated files).

### Commits:
Even though I have tried to have a set of coherent commits for the three different portions of project, some parts are inter-mixed. Note that the commit corresponding to the code given is 'HW2'.  
