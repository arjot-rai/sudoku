package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import sudoku.Sudoku;

public class Sudoku_GUI extends JFrame implements Cloneable{
    sudoku.Sudoku_Generator gen =  new sudoku.Sudoku_Generator(1);
    Container container = getContentPane();
    public static final int GRID_SIZE = 9;
    public static final int SUB_GRID_SIZE = 9;

    // GUI
    public static final int CELL_SIZE = 60;
    public static final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;
    public static final Font FONT_NUMBERS = new Font("Comicsans", Font.BOLD, 20);
    private JTextField[][] cells = new JTextField[GRID_SIZE][GRID_SIZE];

    private static final Color FG_CLOSE = Color.white;
    private static final Color BG_CLOSE = Color.black;
    private static final Color BG_WRONG = Color.red;
    private static final Color BG_OPEN = Color.gray;

    private int[][] board = gen.getBoard();
//            {
//            {1, 7, 9, 6, 2, 5, 4, 8, 3},
//            {3, 2, 5, 9, 8, 4, 7, 6, 1},
//            {4, 8, 6, 1, 7, 3, 9, 5, 2},
//            {2, 6, 1, 5, 4, 7, 8, 3, 9},
//            {8, 4, 7, 2, 3, 9, 6, 1, 5},
//            {5, 9, 3, 8, 6, 1, 2, 7, 4},
//            {6, 3, 8, 4, 5, 2, 1, 9, 7},
//            {9, 5, 2, 7, 1, 8, 3, 4, 6},
//            {7, 1, 4, 3, 9, 6, 5, 2, 8}
//
//    };

    private boolean[][] mask = gen.getMask();
//            {
//            { false, false, false, true, false, true, false, true, false },
//            { true, false, false, false, true, false, true, false, false },
//            { false, true, true, false, false, true, true, false, false },
//            { true, false, true, false, false, false, false, false, true },
//            { false, true, false, false, false, false, false, true, false },
//            { true, false, false, false, false, false, true, false, true },
//            { false, false, true, true, false, false, true, true, false },
//            { false, false, true, false, true, false, false, false, true },
//            { false, true, false, true, false, true, false, false, false }
//    };
//    public boolean solved = Sudoku.solve(solvedBoard);


    class JTextFieldLimit extends PlainDocument {
        private int limit;
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    private class InputListener implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e){

        }
        @Override
        public void keyTyped(KeyEvent e){

        }
        @Override
        public void keyReleased(KeyEvent e){
            int selectedRow = -1;
            int selectedCol = -1;
            JTextField actionSource = (JTextField)e.getSource();
            boolean found = false;
            for(int row = 0; row < GRID_SIZE && !found; ++row){
                for(int col = 0; col < GRID_SIZE && !found; ++col){
                    if(cells[row][col] == actionSource){
                        selectedRow = row;
                        selectedCol = col;
                        found = true;

                    }

                }
            }
            int value = 0;

            String input = cells[selectedRow][selectedCol].getText();

            try{
                value = Integer.parseInt(input);
                if(board[selectedRow][selectedCol] == value){
                    cells[selectedRow][selectedCol].setBackground(Color.gray);
                    mask[selectedRow][selectedCol] = true;
                    if(gen.checkMask(mask)){
                        winner();
                    }
                }
                else {
                    cells[selectedRow][selectedCol].setBackground(BG_WRONG);
                    mask[selectedRow][selectedCol] = false;
                }
                cells[selectedRow][selectedCol].setForeground(FG_CLOSE);
            }
            catch (Exception except){
                cells[selectedRow][selectedCol].setText("");

            }
        }
    }

    private void winner(){
        JOptionPane.showMessageDialog(null, "Congratulation!");
    }
    private JMenuBar buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newgame = new JMenuItem("New Game");
        JMenuItem about = new JMenuItem("About");

        fileMenu.add(newgame);
        fileMenu.addSeparator();
        fileMenu.add(about);
        bar.add(fileMenu);


        newgame.addActionListener((ActionEvent e) ->{
            dispose();
            String[] options = {"Easy", "Medium", "Hard"};
            String difficulty = (String) JOptionPane.showInputDialog(null, "Choose difficulty(By default easy):", "Difficulty", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            try{
                if(difficulty.compareTo("Easy") == 0){
                    new Sudoku_GUI(1);
                }
                if(difficulty.compareTo("Medium") == 0){
                    new Sudoku_GUI(2);
                }
                if(difficulty.compareTo("Hard") == 0){
                    new Sudoku_GUI(3);
                }
            }
            catch (NullPointerException except){
                new Sudoku_GUI(1);
            }


        });
        about.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(
                    null,
                    "By Arjot Singh Rai. April 30, 2020",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        return bar;
    }
    private void clearAll() {
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                if (cells[i][j].getBackground() == BG_OPEN || cells[i][j].getBackground() == BG_WRONG) {
                    cells[i][j].setText("");
                    cells[i][j].setBackground(BG_OPEN);
                }
            }
        }
    }
    private void solve(){
        for(int i = 0; i < GRID_SIZE; ++i){
            for(int j = 0; j < GRID_SIZE; ++j) {
                if (!mask[i][j]) {
                    cells[i][j].setBackground(BG_OPEN);
                    cells[i][j].setForeground(FG_CLOSE);
                    cells[i][j].setText("" + board[i][j]);
                }
            }
        }
    }
    public Sudoku_GUI(int difficulty){
        gen = new sudoku.Sudoku_Generator(difficulty);
        container.setLayout(new BorderLayout());
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
//        ChangeListener listener = new ChangeListener();
        InputListener listener = new InputListener();
        for(int row = 0; row < GRID_SIZE; ++row){
            for(int col = 0; col < GRID_SIZE; ++col){
                cells[row][col] = new JTextField();
                cells[row][col].setDocument(new JTextFieldLimit(1));
                grid.add(cells[row][col]);
                if(!mask[row][col]){
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(BG_OPEN);
//                    cells[row][col].addActionListener(listener);
                    cells[row][col].addKeyListener(listener);
                }
                else{
                    cells[row][col].setText(board[row][col] + "");
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(BG_CLOSE);
                    cells[row][col].setForeground(FG_CLOSE);
                }
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(FONT_NUMBERS);
            }
        }

        container.setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));

        JButton clearButton = new JButton("Clear");
        clearButton.setMaximumSize(new Dimension(10, 10));
        JButton newButton = new JButton("New");
        newButton.setMaximumSize(new Dimension(10, 10));
        JButton solveButton = new JButton("Solve");
        solveButton.setMaximumSize(new Dimension(10, 10));

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(clearButton, BorderLayout.WEST);
        buttonPanel.add(solveButton, BorderLayout.EAST);

        JMenuBar menu = buildMenu();
//        this.setLayout(new BorderLayout());
        setJMenuBar(menu);
        container.add(grid,   BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener((ActionEvent e) -> {
            clearAll();
        });

        solveButton.addActionListener((ActionEvent e) -> {
            solve();
        });

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setVisible(true);
    }

    public static void main(String[] args){
        new Sudoku_GUI(1);


    }

}
