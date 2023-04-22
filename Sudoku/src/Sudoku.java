package Sudoku.src;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Sudoku{

    private ArrayList<ArrayList<Integer>> sudoku;
    private String filename;
    private HashSet<Integer> square;
    private HashSet<Integer> row;
    private HashSet<Integer> column;


    public void solve_sudoku(){
        square = new HashSet<Integer>();
        row = new HashSet<Integer>();
        column = new HashSet<Integer>();
        int solved = 0;
        while (solved < 81){
            solved = 0;
            for(int y = 0; y < sudoku.size(); y++){
                for(int x = 0; x<sudoku.get(y).size(); x++){
                    if(sudoku.get(y).get(x) == 0){
                        generateSquare(x,y);
                        generateRow(y);
                        generateColumn(x);

                    }else solved++;
                }
            }
            System.out.println(solved);
            System.out.println("lecimy od nowa");
        }
    }

    private void generateSquare(int x, int y){
        square.clear();
        int xmid = x/3;
        int ymid = y/3;
        for(int z = ymid*3; z<(ymid+1)*3;z++){
            for(int c = xmid*3; c < (xmid+1)*3; c++){
                square.add(sudoku.get(z).get(c));
            }
        }
        square.remove(0);
    }

    private void generateColumn(int x){
        column.clear();
        for(int y = 0; y< sudoku.size(); y++){
            column.add(sudoku.get(y).get(x));
        }
        column.remove(0);
    }

    private void generateRow(int y){
        row.clear();
        for(int x = 0; x< sudoku.get(y).size(); x++){
            row.add(sudoku.get(y).get(x));
        }
        row.remove(0);
    }

    private List availabe_numbers(HashSet object){
        List<Integer> output = new ArrayList<>();
        for(int x = 1; x<= 9 ; x++){
            if(!object.contains(x))output.add(x);
        }
        return output;
    }

    public void results()  {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename.replace(".txt", "_out.txt")));
            for (ArrayList<Integer> integers : sudoku) {
                for (Integer integer : integers) {
                    writer.write(integer);
                }
                writer.newLine();
            }
            writer.close();
        }
        catch ( IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        };
    }


    public void read_data(){
        sudoku = new ArrayList<ArrayList<Integer>>();
        Scanner userInterface = new Scanner(System.in);
        System.out.println("NameFile: ");
        filename = userInterface.next() + ".txt";
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                ArrayList<Integer> inner = new ArrayList<Integer>();
                String str = myReader.nextLine();
                String[] strsplitted = str.split("");
                for(String s: strsplitted) inner.add(Integer.parseInt(s));
                sudoku.add(inner);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
