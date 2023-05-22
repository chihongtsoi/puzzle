package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Arrays;

public class Q25 extends BaseSolution {


    @Run({"input-aoc/Q20221224.txt"})
    public int part1(String s) {
        Basin basin = new Basin(s);
        while (!basin.canGo(basin.m - 1, basin.n - 2)) basin = basin.nextMinute();
        return basin.minute;
    }

    @Run({"input-aoc/Q20221224.txt"})
    public int part2(String s) {
        Basin basin = new Basin(s);
        while (!basin.canGo(basin.m - 1, basin.n - 2)) basin = basin.nextMinute();
        basin.clearAvailablePositionExcept(basin.m - 1, basin.n - 2);
        while (!basin.canGo(0, 1)) basin = basin.nextMinute();
        basin.clearAvailablePositionExcept(0, 1);
        while (!basin.canGo(basin.m - 1, basin.n - 2)) basin = basin.nextMinute();
        return basin.minute;
    }


    @AllArgsConstructor
    static class Basin {
        private final int m;
        private final int n;
        private final int minute;
        private final int[][] blizzards;
        private final boolean[][] availablePosition;
        private final boolean[][] walls;

        public Basin(String s) {
            String[] lines = s.split("\n");
            m = lines.length;
            n = lines[0].length();
            minute = 0;
            blizzards = new int[m][n];
            availablePosition = new boolean[m][n];
            walls = new boolean[m][n];
            availablePosition[0][1] = true;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char c = lines[i].charAt(j);
                    if (c == '#') walls[i][j] = true;
                    Direction direction = Direction.getDirection(c);
                    if (direction != null) blizzards[i][j] = direction.mask;
                }
            }
            for (int[] arr : blizzards) {
                System.out.println(Arrays.toString(arr));
            }
        }

        public boolean canGo(int x, int y) {
            return availablePosition[x][y];
        }

        public Basin nextMinute() {
            boolean[][] newAvailablePosition = new boolean[m][n];
            int[][] newBlizzards = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (blizzards[i][j] == 0) continue;
                    for (Direction direction : Direction.values()) {
                        if ((blizzards[i][j] & direction.mask) > 0) {
                            int x = i + direction.x;
                            int y = j + direction.y;
                            if (walls[x][y]) {
                                int[] reform = direction.reform(m, n, x, y);
                                x = reform[0];
                                y = reform[1];
                            }
                            newBlizzards[x][y] |= direction.mask;
                        }
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (newBlizzards[i][j] > 0 || walls[i][j]) continue;
                    if (availablePosition[i][j]) {
                        newAvailablePosition[i][j] = true;
                        continue;
                    }
                    for (Direction direction : Direction.values()) {
                        int x = i + direction.x;
                        int y = j + direction.y;
                        if (x < 0 || y < 0 || x >= m || y >= n) continue;
                        if (availablePosition[x][y]) {
                            newAvailablePosition[i][j] = true;
                            break;
                        }
                    }
                }
            }
            return new Basin(m, n, minute + 1, newBlizzards, newAvailablePosition, walls);
        }

        public void clearAvailablePositionExcept(int x, int y) {
            for (int i = 0; i < m; i++) Arrays.fill(availablePosition[i], false);
            availablePosition[x][y] = true;
        }
    }


    @AllArgsConstructor
    enum Direction {
        NORTH(-1, 0, 1, '^'),
        SOUTH(1, 0, 2, 'v'),
        WEST(0, -1, 4, '<'),
        EAST(0, 1, 8, '>');

        int x;
        int y;
        int mask;
        char symbol;

        public static Direction getDirection(char c) {
            for (Direction d : Direction.values()) if (c == d.symbol) return d;
            return null;
        }

        public int[] reform(int m, int n, int x, int y) {
            return switch (this) {
                case NORTH -> new int[]{m - 2, y};
                case SOUTH -> new int[]{1, y};
                case WEST -> new int[]{x, n - 2};
                case EAST -> new int[]{x, 1};
            };
        }

    }
}
