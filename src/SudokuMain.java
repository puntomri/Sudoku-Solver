import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SudokuMain {

    Cell[][] gameGrid;
    Unit[] gameUnits;
    static Gui gui;


    SudokuMain() {
        gameGrid = new Cell[9][9];
        gameUnits = new Unit[9 * 3];

        for (int i = 0; i < (9 * 3); i++) {
            gameUnits[i] = new Unit();
        }

        gameStarter();
    }

    //creats 81 Cell objects set to 0
    public void gameStarter() {

        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                Cell X = new Cell(i, j);
                gameGrid[i][j] = X;

                if (i < 3 && j < 3) gameGrid[i][j].blockNo = 0;
                else if (i >= 3 && i < 6 && j < 3) gameGrid[i][j].blockNo = 1;
                else if (i >= 6 && j < 3) gameGrid[i][j].blockNo = 2;
                else if (i < 3 && j >= 3 && j < 6) gameGrid[i][j].blockNo = 3;
                else if (i >= 3 && i < 6 && j >= 3 && j < 6) gameGrid[i][j].blockNo = 4;
                else if (i >= 6 && j >= 3 && j < 6) gameGrid[i][j].blockNo = 5;
                else if (i < 3 && j >= 6) gameGrid[i][j].blockNo = 6;
                else if (i >= 3 && i < 6 && j >= 6) gameGrid[i][j].blockNo = 7;
                else if (i >= 6 && j >= 6) gameGrid[i][j].blockNo = 8;

                //creates columns rows and block objects
                gameUnits[gameGrid[i][j].blockNo].unit.add(gameGrid[i][j]); //blocks
                gameUnits[(i + 9)].unit.add(gameGrid[i][j]);//Rows
                gameUnits[(j + (9 * 2))].unit.add(gameGrid[i][j]);//columns

                int tc = X.blockNo;
                if (tc == 0 || tc == 2 || tc == 4 || tc == 6 || tc == 8) {
                    X.color = true;
                }

            }


        }

        //test values...
        gameGrid[0][0].value = 6;
        gameGrid[0][4].value = 2;
        // gameGrid[0][3].value = 1;
        gameGrid[1][0].value = 7;
        gameGrid[1][3].value = 3;
        gameGrid[1][4].value = 4;
        gameGrid[1][7].value = 6;
        gameGrid[1][8].value = 9;
        gameGrid[2][0].value = 1;
        gameGrid[2][7].value = 2;
        gameGrid[2][8].value = 7;

        gameGrid[3][1].value = 1;
        gameGrid[3][8].value = 6;
        // gameGrid[4][0].value = 9;
        gameGrid[4][2].value = 7;
        gameGrid[4][3].value = 8;
        gameGrid[4][5].value = 5;
        gameGrid[4][6].value = 9;
        gameGrid[5][0].value = 9;
        gameGrid[5][7].value = 5;

        gameGrid[6][1].value = 9;
        gameGrid[6][8].value = 3;

        gameGrid[7][1].value = 5;
        gameGrid[7][0].value = 4;
        gameGrid[7][4].value = 1;
        gameGrid[7][5].value = 3;
        gameGrid[8][4].value = 9;
        gameGrid[8][8].value = 4;


    }

    // inserts user's input to the grid values (text to int) + set options
    public void insertData() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                try {

                    int insertedValue = Integer.parseInt(gui.guiGrid[i][j].getText());
                    if (insertedValue > 0 && insertedValue < 10) {
                        gameGrid[i][j].setCell(insertedValue);
                        setOptions(i, j);
                        gameGrid[i][j].fixed = true;
                    } else {
                        gui.inserterror(i, j);
                    }


                } catch (NumberFormatException e) {

                }


            }
        }


    }

    // sets false the options on related cells for any cell that has value
    public void setOptions(int i, int j) {

        //row & column setter
        for (int k = 0; k < 9; k++) {
            if (!gameGrid[i][k].full) {
                gameGrid[i][k].optionSetter(gameGrid[i][j].value);
                //gui.guiGrid[i][k].setToolTipText("changed");
            }
            if (!gameGrid[k][j].full) {
                gameGrid[k][j].optionSetter(gameGrid[i][j].value);
                //gui.guiGrid[k][j].setToolTipText("changed2");
            }
        }
        // block setter
        for (int l = 0; l < 9; l++) {
            for (int m = 0; m < 9; m++) {
                if ((gameGrid[l][m].blockNo == gameGrid[i][j].blockNo) && !gameGrid[l][m].full) {
                    gameGrid[l][m].optionSetter(gameGrid[i][j].value);
                    // gui.guiGrid[l][m].setToolTipText("changed3");
                }

            }
        }

    }


    // sets new values according to options status
    public void run1(boolean certain) {

        // case #1 - falsecount (checks if one cell has only one true option)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = gameGrid[i][j].falseCounter();
                if (!gameGrid[i][j].full && value > 0) {
                    gameGrid[i][j].setCell(value);
                    setOptions(i, j);
                    if (certain) gameGrid[i][j].fixed = true;
                }
            }
        }

        //case #2 - truecount - checks if there is only one cell with a true option in a block, row or column

        for (int g = 0; g < (9 * 3); g++) {

            Cell cell = gameUnits[g].trueFinder(certain);
            try {
                setOptions(cell.i, cell.j);
            } catch (NullPointerException e) {
            }

        }


    }

    //double true finder, guesser, and setter. returns the option that was not chosen
    public int[] guess() {
        int[] returned = new int[3];
        int notchosen;

        boolean aa = true;
        while (aa) {
            int i = (int) (Math.random() * 9);
            int j = (int) (Math.random() * 9);

            if (gameGrid[i][j].doubleTrue() && !gameGrid[i][j].full) {
                int random = (int) (Math.random() * 2);
                int guessValue = gameGrid[i][j].doubleTrueValues()[random];
                notchosen = gameGrid[i][j].doubleTrueValues()[Math.abs(random - 1)];
                gameGrid[i][j].setCell(guessValue);
                setOptions(i, j);
                aa = false;
                returned[0] = i;
                returned[1] = j;
                returned[2] = notchosen;
            }

        }

        return returned;

    }

    public void solve() {

        boolean certain = true;
        boolean problem1 = false;//first fail- guess
        boolean problem2 = false;//fails to solve - reset
        boolean switcher = false;
        int fullcounter = 0;
        int fullcounter2;
        //int[] option2 = new int[3];


        while (fullcounter < 9 * 9) {

            fullcounter = 0;
            fullcounter2 = 0;

            //count full cells
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (gameGrid[i][j].full) fullcounter2++;
                }
            }

            //runs regular checks and changes
            run1(certain);

            //2nd count of full cells after the run1 function
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (gameGrid[i][j].full) fullcounter++;
                }
            }

            // checks if the run1 function did a change, if not, set problem 1 or problem2 true.
            if (fullcounter2 == fullcounter) {

                solve2();
                if (switcher) {


                  // problem2 = true;
                } else {
                  //  problem1 = true;
                }
            }

            //make a guess + saves the 2nd option
            if (problem1) {
                //option2 = guess();
                guess();
                problem1 = false;
                switcher = true;
                certain = false;

            }

            //if guess was wrong, reset
            if (problem2) {

                reset(); //clears all non fixed cells

                //gameGrid[option2[0]][option2[1]].setCell(option2[2]); //sets the 2nd alternative
                //gameGrid[option2[0]][option2[1]].fixed=true; //certain it
                //setOptions(option2[0],option2[1]);
                // certain = true;

                problem1 = false;
                problem2 = false;
                switcher = false;
            }


        }

        if (fullcounter == (9 * 9)) {
            gui.completed();

        }


    }

    ArrayList<Cell> cella = new ArrayList<>();
    int fullcounter = 0;


    public void solve2() {

        //count full cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
             if(gameGrid[i][j].full)  fullcounter++;
             else cella.add(gameGrid[i][j]);

            }
        }

        recurse(0);



    }



    public boolean recurse(int n){



        //creat list with options
        ArrayList<Integer> options = new ArrayList<>();
        for (int i =0; i<9;i++){
            if (cella.get(n).options[i]) options.add(i+1);
        }


        for  (int w = 0;w<options.size();w++){

            //check if possible for a number
            int no = options.get(w);
            boolean pos=true;
            for(int q = 0; q<9; q++){
                if ((gameGrid[cella.get(n).i][q].value==no)||
                    (gameGrid[q][cella.get(n).j].value==no)||
                    (gameUnits[cella.get(n).blockNo].unit.get(q).value==no))pos=false;

            }

            if (pos) {
                cella.get(n).setCell(no);
                fullcounter++;

                if (fullcounter == 81) {

                    gui.completed();

                    return true;
                }
                else if (!recurse(n + 1)) {

                    cella.get(n).full = false;
                    cella.get(n).value = 0;
                    gui.guiGrid[cella.get(n).i][cella.get(n).j].setText("");
                    gui.guiGrid[cella.get(n).i][cella.get(n).j].setEditable(true);
                    if(gameGrid[cella.get(n).i][cella.get(n).j].color) gui.guiGrid[cella.get(n).i][cella.get(n).j].setBackground(Color.gray);
                    else gui.guiGrid[cella.get(n).i][cella.get(n).j].setBackground(Color.lightGray);
                    fullcounter--;


                } else return true;

            }





        }






        return false;



    }


    public void reset(){

        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {

                if (!gameGrid[i][j].fixed) {
                      gameGrid[i][j].full = false;
                      gameGrid[i][j].value = 0;
                    Arrays.fill(gameGrid[i][j].options, Boolean.TRUE);
                      gui.guiGrid[i][j].setText("");
                    gui.guiGrid[i][j].setEditable(true);
                      if(gameGrid[i][j].color) gui.guiGrid[i][j].setBackground(Color.gray);
                      else gui.guiGrid[i][j].setBackground(Color.lightGray);

                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameGrid[i][j].full) setOptions(i, j);

            }
        }

    }


    public static void main(String [] args){

        //SwingUtilities.invokeLater(new Runnable() {
       //     public void run() {
        SudokuMain sudoku = new SudokuMain();
                gui = new Gui(sudoku);
      //      }
       // });


    }

}
