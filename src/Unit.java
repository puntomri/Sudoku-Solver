import java.util.ArrayList;

public class Unit {

    ArrayList<Cell> unit;

    Unit(){
        unit = new ArrayList<>();
    }


    //finds if there is a single cell in a block with an option, and change the value.
    public Cell trueFinder(boolean certain){

        for(int digit = 0; digit<9;digit++){
            int counter = 0;
            int index = 0;
            for(int i = 0; i<9;i++) {

                if (unit.get(i).options[digit]&&!unit.get(i).full) {
                    counter++;
                    index = i;
                }
            }
            if (counter == 1) {
                Cell chosenCell = unit.get(index);
                chosenCell.setCell(digit+1);
                if (certain) chosenCell.fixed=true;
                return chosenCell;
            }

        }
        return null;

    }


}
