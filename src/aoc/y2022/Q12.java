package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Arrays;

public class Q12 extends BaseSolution {

    private static final int[][] DIRECTION = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    @Run({"input-aoc/Q20221212.txt"})
    public int part1(String s) {
        int sx = 0, sy = 0, ex = 0, ey = 0;

        char[][] matrix = toMatrix(s);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 'S') {
                    sx = i;
                    sy = j;
                } else if (matrix[i][j] == 'E') {
                    ex = i;
                    ey = j;
                }
            }
        }
        return findShortestPath(matrix,  sx, sy, ex, ey);
    }


    @Run({"input-aoc/Q20221212.txt"})
    public int part2(String s) {
        int min = Integer.MAX_VALUE;
        char[][] matrix = toMatrix(s);
        int ex= 0, ey = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j]=='S') matrix[i][j] = 'a';
                if(matrix[i][j]=='E'){
                    ex = i;
                    ey = j;
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j]=='a') min= Math.min(min, findShortestPath(matrix,i,j,ex,ey));
            }
        }
        return min;
    }

    private char[][] toMatrix(String s) {
        String[] lines = s.split("\n");
        char[][] matrix = new char[lines.length][lines[0].length()];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = lines[i].charAt(j);
            }
        }
        return matrix;
    }

    private int findShortestPath(char[][] matrix, int sx, int sy, int ex, int ey) {

        matrix[sx][sy] = 'a';
        matrix[ex][ey] = 'z';
        int[][] min = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) Arrays.fill(min[i], Integer.MAX_VALUE);
        dfs(matrix, sx, sy, 0, min);
        return min[ex][ey];
    }

    private void dfs(char[][] matrix, int x, int y, int step, int[][] min) {
        if (step >= min[x][y]) return;
        min[x][y] = step;
        step++;
        for (int[] d : DIRECTION) {
            int _x = x + d[0];
            int _y = y + d[1];
            if (_x >= 0 && _y >= 0 && _x < matrix.length && _y < matrix[_x].length && matrix[_x][_y] - matrix[x][y] <= 1) {
                dfs(matrix, _x, _y, step, min);
            }
        }
    }

}
