package Sudoku.src;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Sudoku{

    private ArrayList<ArrayList<Area>> sudoku;
    private String filename;
    private HashSet<Integer> square;
    private HashSet<Integer> row;
    private HashSet<Integer> column;


    public void solve_sudoku() {
        square = new HashSet<Integer>();
        row = new HashSet<Integer>();
        column = new HashSet<Integer>();
        int solved = 0;
        while(solved < 81){
            solved = 0;
            for(int y = 0;y<9;y++){
                for(int x = 0; x<9;x++){
                    Area tempArea = sudoku.get(y).get(x);
                    if(tempArea.value == 0){
                        blankArea(tempArea,x,y,solved);
                    }
                    else solved++;
                }
            }
//            check_is_there_only_one_potencial();
            for(int y = 0; y < 9;y++){
                checkRow(y);
            }
//            System.out.println(solved);
        }

    }

    private void check_is_there_only_one_potencial(){
        int couter = 0;
        for(int y = 0;y<9;y++){
            for(int x = 0; x<9;x++) {
                Area tempArea = sudoku.get(y).get(x);
                if(tempArea.value == 0 && tempArea.potencialValue.size() == 1 )
                {
                    for(int element: tempArea.potencialValue)tempArea.value = element;
                    removeFromPotencialInColumn(x, tempArea.value);
                    removeFromPotencialInRow(y, tempArea.value);
                    removeFromPotencialInsquare(x,y, tempArea.value);
                    couter++;
                }

            }
        }
        System.out.println("Dodano: "+couter);
    }

    private void blankArea(Area tempArea, int x, int y, int solved){
//        System.out.println("znaleziono puste pole" );
        generateSquare(x,y);
        generateColumn(x);
        generateRow(y);
        tempArea.potencialValue.removeAll(square);
        tempArea.potencialValue.removeAll(column);
        tempArea.potencialValue.removeAll(row);
        if(tempArea.potencialValue.size() == 1){
            for(int element: tempArea.potencialValue)tempArea.value = element;
//            System.out.println("Wstawiono wartosc");
            solved++;
            System.out.println(solved);
            removeFromPotencialInColumn(x, tempArea.value);
            removeFromPotencialInRow(y, tempArea.value);
            removeFromPotencialInsquare(x,y, tempArea.value);
        }
    }

    private void checkRow(int y){
        for(int x = 0; x<9;x++)
        {
            HashSet<Integer> collector = new HashSet<Integer>();
            for(int z = 0; z<9;z++)
            {
                if(z != x && sudoku.get(y).get(z).value == 0 && sudoku.get(y).get(x).value == 0){
                    if((sudoku.get(y).get(x).potencialValue.size() - sudoku.get(y).get(z).potencialValue.size()) == 1){
                        HashSet<Integer> temp = new HashSet<>();
                        for(int element: sudoku.get(y).get(x).potencialValue){
                            if(!(sudoku.get(y).get(z).potencialValue.contains(element))){
                                temp.add(element);
                            }
                        }
                        System.out.println(temp);
                        if(temp.size() == 1) {
                            collector.addAll(temp);
                        }
                    }

                }
            }
            if(collector.size() == 1){
                for (int element : collector) sudoku.get(y).get(x).value = element;
            }
        }

    }

    private void removeFromPotencialInRow(int y, int value){
        for(Area object: sudoku.get(y)){
            if(object.value == 0)object.potencialValue.remove(value);

        }
    }
    private void removeFromPotencialInColumn(int x, int value){
        for (ArrayList<Area> areas : sudoku) {
            if (areas.get(x).value == 0) areas.get(x).potencialValue.remove(value);
        }
    }
    private void removeFromPotencialInsquare(int x,int y,int value){
        int xmid = x/3;
        int ymid = y/3;
        for(int z = ymid*3; z<(ymid+1)*3;z++){
            for(int c = xmid*3; c < (xmid+1)*3; c++){
                if(sudoku.get(y).get(x).value == 0)sudoku.get(y).get(x).potencialValue.remove(value);
            }
        }
    }

    private void generateSquare(int x, int y){
        square.clear();
        int xmid = x/3;
        int ymid = y/3;
        for(int z = ymid*3; z<(ymid+1)*3;z++){
            for(int c = xmid*3; c < (xmid+1)*3; c++){
                square.add(sudoku.get(z).get(c).value);
            }
        }
        square.remove(0);
    }

    private void generateColumn(int x){
        column.clear();
        for(int y = 0; y< sudoku.size(); y++){
            column.add(sudoku.get(y).get(x).value);
        }
        column.remove(0);
    }

    private void generateRow(int y){
        row.clear();
        for(int x = 0; x< sudoku.get(y).size(); x++){
            row.add(sudoku.get(y).get(x).value);
        }
        row.remove(0);
    }

    private List<Integer> availabe_numbers(HashSet object){
        List<Integer> output = new ArrayList<>();
        for(int x = 1; x<= 9 ; x++){
            if(!object.contains(x))output.add(x);
        }
        return output;
    }

    public void results()  {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename.replace(".txt", "_out.txt")));
            for (ArrayList<Area> column : sudoku) {
                for (Area row : column) {
                    writer.write(row.value);
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

    public void printSudoku(){
        for(ArrayList<Area> column: sudoku){
            for (Area row: column){
                System.out.print(row.value);
            }
            System.out.println();
        }
    }


    public void read_data(){
        sudoku = new ArrayList<ArrayList<Area>>();
        Scanner userInterface = new Scanner(System.in);
        System.out.println("NameFile: ");
        filename = userInterface.next() + ".txt";
        try{
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                ArrayList<Area> inner = new ArrayList<Area>();
                String str = myReader.nextLine();
                String[] strsplitted = str.split("");
                for(String s: strsplitted) inner.add(new Area(Integer.parseInt(s)));
                sudoku.add(inner);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
