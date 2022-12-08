package aoc.dec2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q20221208 extends BaseSolution {


    @Run({"input-aoc/Q20221208.txt"})
    public int part1(String s) {
        int[][] matrix = resolveInput(s);
        int m = matrix.length;
        int n = matrix[0].length;
        int[][][] dp = new int[4][m + 2][n + 2];
        WatchDirection[] directions = WatchDirection.values();
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (WatchDirection d : directions) {
                    int _i = d.getI(i, m);
                    int _j = d.getJ(j, n);
                    dp[d.ordinal()][_i][_j] = Math.max(
                            matrix[_i - 1][_j - 1],
                            dp[d.ordinal()][_i + d.x][_j + d.y]
                    );
                }
            }
        }
        int count = (m + n - 2) * 2;
        for (int i = 2; i < m; i++) {
            for (int j = 2; j < n; j++) {
                for (WatchDirection d : WatchDirection.values()) {
                    if (matrix[i - 1][j - 1] > dp[d.ordinal()][i + d.x][j + d.y]) {
                        count++;
                        break;
                    }
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
        long max = 0;
        List<List<Stack<int[]>>> stacks = new ArrayList<>();
        for (int i = 0; i < 4; i++) stacks.add(new Stack<>());
        WatchDirection[] directions = WatchDirection.values();
        int[][][] dp = new int[4][m + 2][n + 2];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (WatchDirection d : directions) {
                    int _i = d.getI(i, m);
                    int _j = d.getJ(j, n);
                    int _k = d.getK(i, j) - 1;
                    List<Stack<int[]>> rcStack = stacks.get(d.ordinal());
                    if (_k >= rcStack.size()) rcStack.add(new Stack<>());
                    Stack<int[]> monoStack = rcStack.get(_k);
                    int cur = matrix[_i - 1][_j - 1];
                    if (monoStack.isEmpty()) {
                        monoStack.push(new int[]{cur, 1});
                        continue;
                    }
                    int count = 0;
                    while (!monoStack.isEmpty() && cur > monoStack.peek()[0]) count += monoStack.pop()[1];
                    dp[d.ordinal()][_i][_j] = count + (monoStack.isEmpty() ? 0 : 1);
                    if (!monoStack.isEmpty() && monoStack.peek()[0] == cur) count += monoStack.pop()[1];
                    monoStack.push(new int[]{cur, count + 1});
                }
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int score = 1;
                for (WatchDirection d : directions) score *= dp[d.ordinal()][i][j];
                max = Math.max(max, score);
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
                case WEST, NORTH -> m - i + 1;
            };
        }

        public int getJ(int j, int n) {
            return switch (this) {
                case EAST, SOUTH -> j;
                case WEST, NORTH -> n - j + 1;
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
