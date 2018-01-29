import java.awt.*;
import java.util.Arrays;

public class Cell {

    public int value;
    public boolean full;
    public int blockNo;
    public boolean[] options;
    public int i;
    public int j;
    public boolean fixed;
    boolean color;

    Cell(int i, int j){

        value = 0;
        full = false;
        options = new boolean[9];
        Arrays.fill(options, Boolean.TRUE);
        this.i = i;
        this.j = j;
        fixed = false;
        color = false;
    }

    //sets the value and displays in gui
    public void setCell (int value){

        this.value = value;
        full = true;
        SudokuMain.gui.guiGrid[i][j].setText(this.value+"");
        SudokuMain.gui.guiGrid[i][j].setEditable(false);
        if(color) SudokuMain.gui.guiGrid[i][j].setBackground(SudokuMain.gui.spink);
        else SudokuMain.gui.guiGrid[i][j].setBackground(SudokuMain.gui.lightpink);

    }


    public void optionSetter(int a){
        options[a-1] = false;

    }

    //returns the only true option if all other options are false. otherwise return zero
    public int falseCounter(){
        int counter = 0;
        int result = 0;
        for(int i = 0; i<9;i++){
            if (options[i]) {
                counter++;
                result = i + 1;
            }
        }

        if (counter == 1) return result;
        else return 0;


    }

    public String OptionsString(){
        String returned="";
        for(int i=0;i<9;i++){
            if (options[i]) returned += (" " + (i+1));
        }

        return returned;

    }

    public boolean doubleTrue(){
        int counter = 0;
        for(int i = 0; i<9;i++){
            if (options[i])counter++;
        }
        if (counter==2)return true;
        else return false;
    }

    public int[] doubleTrueValues(){
        int counter=0;
        int [] returned = new int[2];
        for(int i = 0; i<9;i++){
            if (options[i]){
                returned[counter]=i+1;
                counter++;
            }

        }
        return returned;
    }



}
