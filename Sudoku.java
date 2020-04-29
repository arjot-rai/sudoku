package sudoku;

import java.util.*;
import java.util.stream.IntStream;
import javax.swing.*;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class Sudoku{
    static Random r = new Random();
    static int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static int SIZE = 9;

    public static int[] RandomizeArray(int[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }
    private static boolean checkCol(int[][] board, int col){
        Set<Integer> s = new HashSet<>();

        for(int i = 0; i < SIZE; ++i) {
            if (board[i][col] != 0) {
                if (s.contains(board[i][col])) {
                    return false;
                }

                s.add(board[i][col]);
            }
        }
        return true;
    }

    private static boolean checkRow(int[][] board, int row){
        Set<Integer> s = new HashSet<>();

        for(int i = 0; i < SIZE; ++i) {
            if (board[row][i] != 0) {
                if (s.contains(board[row][i])) {
                    return false;
                }
                s.add(board[row][i]);
            }
        }
        return true;
    }

    private static boolean checkGrid(int[][] board, int row, int col){
        int row_start = ((int) row / 3)*3;
        int col_start = ((int) col / 3)*3;
        Set<Integer> s = new HashSet<>();

        for(int i = row_start; i < row_start + 3; ++i) {
            for (int j = col_start; j < col_start + 3; ++j) {
                if (board[i][j] != 0) {
                    if (s.contains(board[i][j])) {
                        return false;
                    }
                    s.add(board[i][j]);
                }
            }
        }
        return true;
    }

    private static boolean isSolved(int[][] board, int row, int col){
        return checkRow(board, row) && checkCol(board, col) && checkGrid(board, row, col);
    }


    public static boolean solve(int[][] board){
        for(int i = 0; i < SIZE; ++i){
            for(int j = 0; j < SIZE; ++j) {
                if(board[i][j] == 0){
                    numbers = RandomizeArray(numbers);
                    for(int k = 0; k < SIZE; ++k) {
                        board[i][j] = numbers[k];
                        if (isSolved(board, i, j) && solve(board)) {
                            return true;
                        }
                        board[i][j] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args){

        int[][] board = {
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

//        System.out.println(checkGrid(board, 3, 3));

        boolean isSolved = solve(board);
        if(isSolved) {
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.print("\n");
            }
        }
        else{
            System.out.println("Its not possible to solve this problem!!");
        }
    }
}
