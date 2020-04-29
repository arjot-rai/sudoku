package sudoku;
import java.util.Random;
public class Sudoku_Generator {
    private int[][] board;
    private boolean[][] mask = {
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true }
    };
    private sudoku.Sudoku solver = new sudoku.Sudoku();
    private int[][] start = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    Sudoku_Generator(int difficulty){
        generateBoard();
        Random r = new Random();
        if(difficulty == 1){
            generateMask(r.nextInt(17) + 17);
        }else if(difficulty == 2){
            generateMask(r.nextInt(17) + 28);
        }else{
            generateMask(r.nextInt(17) + 43);
        }
    }

    public int[][] getBoard(){
        return board;
    }

    public boolean[][] getMask(){
        return mask;
    }

    public void generateBoard(){
        sudoku.Sudoku.solve(start);
        board = start;
    }
    public void generateMask(int maskNumber){
        Random r = new Random();
        for(int i = 0; i < maskNumber; ++i){
            int j = r.nextInt(9);
            int k = r.nextInt(9);
            if(!mask[j][k]){
                i -= 1;
            }
            else{
                mask[j][k] = false;

            }
        }
    }
    boolean checkMask(boolean[][] mask){
        for(int i = 0; i < mask.length; ++i){
            for(int j = 0; j < mask.length; ++j){
                if(!mask[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}
