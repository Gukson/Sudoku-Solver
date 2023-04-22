package Sudoku.src;

import java.util.Arrays;
import java.util.HashSet;

public class Area {
    public int value = 0;
    public HashSet<Integer> potencialValue ;

    Area(int amout){
        if(amout == 0){
            potencialValue = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        }
        else value = amout;
    }


}
