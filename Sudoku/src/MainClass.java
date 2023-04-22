package Sudoku.src;

public class MainClass {
    public static void main(String[] args) {
        Sudoku test = new Sudoku();
        test.read_data();
        test.solve_sudoku();
        test.results();
    }


}
