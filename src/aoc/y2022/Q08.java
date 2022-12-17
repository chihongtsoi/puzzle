package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Q08 extends BaseSolution {


    @Run({"input-aoc/Q20221208.txt"})
    public int part1(String s) {
        int[][] matrix = resolveInput(s);
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] counted = new boolean[m][n];
        WatchDirection[] directions = WatchDirection.values();
        int count =0;
        for (WatchDirection d : directions) {
            int[] highest = new int[d.getK(m,n)];
            Arrays.fill(highest, -1);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int _i = d.getI(i, m);
                    int _j = d.getJ(j, n);
                    int _k = d.getK(i,j);
                    int cur = matrix[_i][_j];
                    if(!counted[_i][_j] &&cur>highest[_k]){
                        count++;
                        counted[_i][_j] = true;
                    }
                    highest[_k] = Math.max(highest[_k], cur);
                }
            }
        }
        return count;
    }


    @Run({"input-aoc/Q20221208.txt"})
    public long part2(String s) {
        int[][] matrix = resolveInput(s);
        int m = matrix.length;
        int n = matrix[0].length;
        List<List<Stack<int[]>>> stacks = new ArrayList<>();
        for (int i = 0; i < 4; i++) stacks.add(new Stack<>());
        WatchDirection[] directions = WatchDirection.values();
        int[][] multiply = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(multiply[i], 1);
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (WatchDirection d : directions) {
                    int _i = d.getI(i, m);
                    int _j = d.getJ(j, n);
                    int _k = d.getK(i, j);
                    List<Stack<int[]>> rcStack = stacks.get(d.ordinal());
                    if (_k >= rcStack.size()) rcStack.add(new Stack<>());
                    Stack<int[]> monoStack = rcStack.get(_k);
                    int cur = matrix[_i][_j];
                    if (monoStack.isEmpty()) {
                        monoStack.push(new int[]{cur, 1});
                        continue;
                    }
                    int count = 0;
                    while (!monoStack.isEmpty() && cur > monoStack.peek()[0]) count += monoStack.pop()[1];
                    multiply[_i][_j] *= count + (monoStack.isEmpty() ? 0 : 1);
                    max = Math.max(max, multiply[_i][_j]);
                    if (!monoStack.isEmpty() && monoStack.peek()[0] == cur) count += monoStack.pop()[1];
                    monoStack.push(new int[]{cur, count + 1});
                }
            }
        }
        return max;
    }

    public int[][] resolveInput(String s) {
        String[] lines = s.split("\n");
        int[][] matrix = new int[lines.length][lines[0].length()];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = lines[i].charAt(j) - '0';
            }
        }
        return matrix;
    }


    @AllArgsConstructor
    enum WatchDirection {
        EAST(0, -1),
        SOUTH(-1, 0),
        WEST(0, 1),
        NORTH(1, 0);
        int x, y;

        public int getI(int i, int m) {
            return switch (this) {
                //start with top left corner
                case EAST, SOUTH -> i;
                //start with bottom right corner
                case WEST, NORTH -> m - i - 1;
            };
        }

        public int getJ(int j, int n) {
            return switch (this) {
                case EAST, SOUTH -> j;
                case WEST, NORTH -> n - j -1;
            };
        }

        public int getK(int i, int j) {
            return switch (this) {
                case EAST, WEST -> i;
                case SOUTH, NORTH -> j;
            };
        }
    }
}
