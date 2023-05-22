package janestreetpuzzle;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Q202212 {

    public static ForkJoinPool pool = new ForkJoinPool(16);

    public static final int[][] BOARD = {
            {57, 33, 132, 268, 492, 732},
            {81, 123, 240, 443, 353, 508},
            {186, 42, 195, 704, 452, 228},
            {-7, 2, 357, 452, 317, 395},
            {5, 23, -4, 592, 445, 620},
            {0, 77, 32, 403, 337, 452},
    };


    public static void main(String[] args) {
        CurrentStateAction initState = new CurrentStateAction(new Dice(), 5, 0, 0, 0, 0);
        pool.invoke(initState);
        new Scanner(System.in).next();
    }

    static class CurrentStateAction extends RecursiveAction {

        private final Dice dice;

        private final int x;
        private final int y;
        private int score;
        private final int step;
        private long visited;

        public CurrentStateAction(Dice dice, int x, int y, int score, int step, long visited) {
            this.dice = dice;
            this.x = x;
            this.y = y;
            this.score = score;
            this.step = step;
            this.visited = visited;
        }

        @Override
        protected void compute() {
            if (!isStateValid()) return;
            visit();
            if (x == 0 && y == 5) {
                printResult();
                return;
            }
            score = BOARD[x][y];
            for (Direction direction : Direction.values()) {
                int _x = x + direction.x;
                int _y = y + direction.y;
                if (_x < 0 || _x > 5 || _y < 0 || _y > 5) continue;
                new CurrentStateAction(dice.move(direction), _x, _y, score, step + 1, visited).fork();
            }

        }

        private void visit() {
            int bit = x * 6 + y;
            visited |= 1L << bit;
        }

        private boolean isStateValid() {
            if (step == 0) return true;
            int diff = BOARD[x][y] - score;
            if (dice.getValue(Face.UP) == null) {
                if (diff % step != 0) return false;
                dice.setValue(Face.UP, diff / step);
                return true;
            }
            return dice.getValue(Face.UP) * step + score == BOARD[x][y];
        }

        private void printResult() {
            int sum = 0;
            System.out.println(this);
            StringBuilder board = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    int bit = i * 6 + j;
                    long mask = 1L << bit;
                    if ((visited & mask) == 0) {
                        board.append('O');
                        sum += BOARD[i][j];
                    } else {
                        board.append('X');
                    }

                }
                board.append('\n');
            }
            System.out.println(board);
            System.out.println(sum);
        }

        @Override
        public String toString() {
            return "BacktrackingAction{" +
                    "dice=" + dice +
                    ", x=" + x +
                    ", y=" + y +
                    ", score=" + score +
                    ", step=" + step +
                    ", visited=" + visited +
                    '}';
        }
    }


    static class Dice {

        private final Integer[] v;

        public Dice() {
            this.v = new Integer[6];
        }


        private Dice(Integer... v) {
            this.v = v;
        }

        public Dice move(Direction direction) {
            return switch (direction) {
                case NORTH -> new Dice(getValue(Face.SOUTH), getValue(Face.UP), getValue(Face.EAST), getValue(Face.DOWN),
                        getValue(Face.WEST), getValue(Face.NORTH));
                //SOUTH, UP, EAST, DOWN, WEST, NORTH
                case WEST -> new Dice(getValue(Face.EAST), getValue(Face.NORTH), getValue(Face.DOWN), getValue(Face.SOUTH),
                        getValue(Face.UP), getValue(Face.WEST));
                //EAST, NORTH, DOWN, SOUTH, UP, WEST
                case SOUTH -> new Dice(getValue(Face.NORTH), getValue(Face.DOWN), getValue(Face.EAST), getValue(Face.UP),
                        getValue(Face.WEST), getValue(Face.SOUTH));
                //NORTH, DOWN, EAST, UP, WEST, SOUTH
                case EAST -> new Dice(getValue(Face.WEST), getValue(Face.NORTH), getValue(Face.UP), getValue(Face.SOUTH),
                        getValue(Face.DOWN), getValue(Face.EAST));
                //WEST, NORTH, UP, SOUTH, DOWN, EAST
            };
        }

        public Integer getValue(Face face) {
            return v[face.ordinal()];
        }

        public void setValue(Face face, int value) {
            v[face.ordinal()] = value;
        }

        @Override
        public String toString() {
            return "Dice{" +
                    "v=" + Arrays.toString(v) +
                    '}';
        }
    }

    enum Face {
        UP, NORTH, EAST, SOUTH, WEST, DOWN
    }

    enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);
        public int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
