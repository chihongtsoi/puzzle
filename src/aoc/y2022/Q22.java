package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Arrays;

public class Q22 extends BaseSolution {


    @Run({"input-aoc/Q20221222.txt"})
    public int part1(String s) {
        Map map = new Map(s);
        map.doAction();
        return map.getPassword();
    }

    @Run({"input-aoc/Q20221222.txt"})
    public int part2(String s) {
        Map2 map = new Map2(s);
        map.doAction();
        return map.getPassword();
    }

    static class Map {
        protected final int[] horiz_start;
        protected final int[] horiz_end;
        protected final int[] vert_start;
        protected final int[] vert_end;
        protected final char[][] map;
        protected int x;
        protected int y;
        protected int dir;
        protected final String action;
        protected final char[][] path;

        public Map(String s) {
            String[] lines = s.split("\n");
            int line = lines.length;

            int max = 0;
            for (int i = 0; i < line - 2; i++) max = Math.max(max, lines[i].length());
            max += 3;
            horiz_start = new int[line];
            horiz_end = new int[line];
            vert_start = new int[max];
            vert_end = new int[max];
            map = new char[line][max];

            Arrays.fill(horiz_start, Integer.MAX_VALUE);
            Arrays.fill(vert_start, Integer.MAX_VALUE);
            Arrays.fill(map[0], ' ');
            Arrays.fill(map[line - 1], ' ');
            for (int i = 1; i < line - 1; i++) {
                Arrays.fill(map[i], ' ');
                for (int j = 1; j <= lines[i - 1].length(); j++) {
                    char c = lines[i - 1].charAt(j - 1);
                    map[i][j] = c;
                    if (c != ' ') {
                        horiz_start[i] = Math.min(horiz_start[i], j);
                        horiz_end[i] = Math.max(horiz_end[i], j);
                        vert_start[j] = Math.min(vert_start[j], i);
                        vert_end[j] = Math.max(vert_end[j], i);
                    }

                }
            }
            x = 1;
            y = horiz_start[1];
            dir = 0;
            action = lines[lines.length - 1];
            path = new char[map.length][];
            for (int i = 0; i < map.length; i++) {
                path[i] = map[i].clone();
            }
        }

        public void doAction() {
            char[] arr = action.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                if (Character.isDigit(arr[i])) {
                    int step = 0;
                    while (i < arr.length && Character.isDigit(arr[i])) {
                        step *= 10;
                        step += arr[i++] - '0';
                    }
                    i--;
                    move(step);
                } else if (arr[i] == 'R') {
                    dir = (dir + 1) % Direction.values().length;
                } else if (arr[i] == 'L') {
                    dir = dir == 0 ? Direction.values().length - 1 : dir - 1;
                }
            }
        }

        private void move(int step) {
            for (int i = 0; i < step; i++) {
                int[] nextPos = nextPosition();
                if (nextPos == null) break;
                this.x = nextPos[0];
                this.y = nextPos[1];
            }
        }

        protected int[] nextPosition() {
            Direction direction = Direction.values()[dir];
            path[x][y] = direction.marker;
            int x = this.x + direction.x;
            int y = this.y + direction.y;
            if (map[x][y] == ' ') {
                switch (dir) {
                    case 0 -> y = horiz_start[x];
                    case 1 -> x = vert_start[y];
                    case 2 -> y = horiz_end[x];
                    case 3 -> x = vert_end[y];
                }
            }
            if (map[x][y] == '#') return null;
            return new int[]{x, y};
        }

        public int getPassword() {
            return x * 1000 + y * 4 + dir;
        }
    }

    static class Map2 extends Map {
        private static final int LENGTH = 50;
        private static final int TO_ZONE = 0;
        private static final int REVERSE_XY = 1;
        private static final int REVERSE_INDEX = 2;
        private static final int START_END_X = 3;
        private static final int START_END_Y = 4;
        private static final int DIRECTION = 5;
        private final int[] startX;
        private final int[] startY;
        // from_zone -> direction -> to_zone, reverser_xy(0/1), reverse_index(0/1),start_end_x(0/1),start_end_y(0/1)
        private final int[][][] mapping = {
                {{}, {}, {3, 0, 1, 0, 0, 0}, {5, 1, 0, 0, 0, 0}},
                {{4, 0, 1, 0, 1, 2}, {2, 1, 0, 0, 1, 2}, {}, {5, 0, 0, 1, 0, 3}},
                {{1, 1, 0, 1, 0, 3}, {}, {3, 1, 0, 0, 0, 1}, {}},
                {{}, {}, {0, 0, 1, 0, 0, 0}, {2, 1, 0, 0, 0, 0}},
                {{1, 0, 1, 0, 1, 2}, {5, 1, 0, 0, 1, 2}, {}, {}},
                {{4, 1, 0, 1, 0, 3}, {1, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 1}, {}},
        };
        private final int[][] zone;

        public Map2(String s) {
            super(s);
            zone = new int[map.length][map[0].length];

            startX = new int[6];
            startY = new int[6];
            int z = 0;
            for (int i = 1; i < map.length; i += LENGTH) {
                for (int j = 1; j < map[i].length; j += LENGTH) {
                    if (map[i][j] != ' ') {
                        startX[z] = i;
                        startY[z] = j;
                        for (int k = 0; k < LENGTH; k++) for (int l = 0; l < LENGTH; l++) zone[i + k][j + l] = z + 1;
                        z++;
                    }
                }
            }
        }

        @Override
        protected int[] nextPosition() {
            int dir = this.dir;
            Direction direction = Direction.values()[dir];
            path[x][y] = direction.marker;
            int cz = zone[x][y] - 1;
            int x = this.x + direction.x;
            int y = this.y + direction.y;
            if (map[x][y] == ' ') {
                int[] mapping = this.mapping[cz][dir];
                int nz = mapping[TO_ZONE];
                boolean reverser_xy = mapping[REVERSE_XY] == 1;
                boolean reverse_index = mapping[REVERSE_INDEX] == 1;
                boolean start_x = mapping[START_END_X] == 0;
                boolean start_y = mapping[START_END_Y] == 0;
                boolean takeX = (dir & 1) == 0;
                dir = mapping[DIRECTION];
                int index = takeX ? this.x - startX[cz] : this.y - startY[cz];
                x = start_x ? startX[nz] : startX[nz] + LENGTH - 1;
                y = start_y ? startY[nz] : startY[nz] + LENGTH - 1;
                if (reverser_xy) takeX = !takeX;
                if (reverse_index) index = LENGTH - index - 1;
                if (takeX) x += index;
                else y += index;
            }
            if (map[x][y] == '#') return null;
            this.dir = dir;
            return new int[]{x, y};
        }
    }

    @AllArgsConstructor
    enum Direction {
        EAST(0, 1, '>'), SOUTH(1, 0, 'V'),
        WEST(0, -1, '<'), NORTH(-1, 0, '^');
        int x, y;
        char marker;
    }

}
