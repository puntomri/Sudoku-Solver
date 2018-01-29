
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui extends JFrame {


    SudokuMain sudoku;
    JTextField [][] guiGrid;
    JTextField options;
    JLabel optionsLabel;
    JButton startButton;
    JButton startButton2;
    JButton startButton3;
    JButton startButton4;
    JButton startButton5;
    Color lightpink;
    Color spink;



    public Gui(SudokuMain s){

        lightpink = new Color(255, 191, 231);
        spink = new Color(204, 124, 174);

        this.sudoku = s;

        guiGrid = new JTextField[9][9];

       Container sod = getContentPane();
       //setLocationRelativeTo(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2-300, dim.height/2-this.getSize().height/2-300);

       BorderLayout mainLayout = new BorderLayout();
       Panel center = new Panel(new FlowLayout());
       Panel guidGrid = new Panel(new GridLayout(9,9));
       //guidGrid.setSize(500,500);
       guidGrid.setPreferredSize(new Dimension(500,500));


       Panel topButtons = new Panel(new FlowLayout());
        Panel bottom = new Panel(new FlowLayout());
       setLayout(mainLayout);
      add(center, BorderLayout.CENTER);

      center.add(guidGrid);
      add(topButtons,BorderLayout.NORTH);
      add(bottom,BorderLayout.SOUTH);

        options = new JTextField("",18);
        optionsLabel = new JLabel("Options:");
        bottom.add(optionsLabel);
        bottom.add(options);

        startButton = new JButton("insert sample");
        //startButton.setToolTipText("bla");
        topButtons.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku.insertData();
              //  startButton2.setEnabled(true);
             //   startButton3.setEnabled(true);
                startButton4.setEnabled(true);
             //   startButton5.setEnabled(true);

            }
        });

        startButton3 = new JButton("Solve step by step");
        startButton3.setEnabled(false);
       topButtons.add(startButton3);
        startButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent x) {
                sudoku.run1(true);

            }
        });

        startButton5 = new JButton("guess");
        startButton5.setEnabled(false);
        topButtons.add(startButton5);
        startButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent x) {
                sudoku.guess();
            }
        });
        startButton4 = new JButton("solve");
        startButton4.setEnabled(false);
        topButtons.add(startButton4);
        startButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent x) {
                sudoku.solve();
            }
        });

        startButton2 = new JButton("reset");
        startButton2.setEnabled(false);
        topButtons.add(startButton2);
        startButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent x) {
                sudoku.reset();
            }
        });

        for (int i = 0;i<9; i++){
            for (int j = 0; j<9; j++){

                String display;
                if (sudoku.gameGrid[i][j].value>0){
                    display =sudoku.gameGrid[i][j].value + "";
                } else{
                    display = "";
                }



                JTextField tempfield = new JTextField( display,1);
                tempfield.setName(""+i+j);
                tempfield.setFont(new Font("Courier",Font.BOLD,30));
                tempfield.setHorizontalAlignment(JTextField.CENTER);
                tempfield.setToolTipText("test2");


                tempfield.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                         JTextField x = (JTextField) tempfield.getComponentAt(e.getPoint());
                         String s = x.getName();
                         char a = s.charAt(0);
                         char b = s.charAt(1);
                         int a1 = (int) a -48;
                         int b1 = (int) b - 48;

                       // System.out.println(z);
                        if(!sudoku.gameGrid[a1][b1].full) options.setText(sudoku.gameGrid[a1][b1].OptionsString());
                        else options.setText("full");
                       //


                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        options.setText("out");
                    }
                });

                //add textfield to both panel and array
                guidGrid.add(tempfield);
                guiGrid[i][j] = tempfield;


                //sets the blocks colors

               if (sudoku.gameGrid[i][j].color){
                    guiGrid[i][j].setBackground(Color.gray);
                } else {
                    guiGrid[i][j].setBackground(Color.lightGray);
               }


            }
        }



       setTitle("Sudoku Solver");

       setSize(600,600);
       setResizable(false);

       setVisible(true);



        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);


    }

        public void completed(){
        JOptionPane.showMessageDialog(null,"Completed!","Completed!",1);

        }

    public void inserterror(int i, int j){
        JOptionPane.showMessageDialog(null,"you have entered an invalid number on cell "+ i+ "/"+j+", Please try again","Error!",1);

    }



}
