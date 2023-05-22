package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;


public class Q17 extends BaseSolution {


    @Run({"input-aoc/Q20221217.txt"})
    public int part1(String s) {
        Tunnel tunnel = new Tunnel(7, 10000, s);
        tunnel.fall(2022);
        tunnel.print(20);
        return tunnel.height();
    }

    @Run({"input-aoc/Q20221217.txt"})
    public int part2(String s) throws InterruptedException {
        return 0;
    }

    static class Tunnel {

        private final boolean[][] tunnel;
        private final String s;
        private int top;
        private final int width;
        private final int height;
        private final int mid;
        private int cursor;

        public Tunnel(int width, int height, String s) {
            tunnel = new boolean[height][width];
            this.s = s;
            top = height;
            this.width = width;
            this.height = height;
            mid = width/2;
            cursor = 0;
        }

        public void fall(int n) {
            for (int i = 0; i < n; i++) {
                RockShape shape = RockShape.values()[i % RockShape.values().length];
                Rock rock = new Rock(top - 4, 2, shape);
                while (true) {
                    Direction direction = nextDirection();
                    System.out.println(direction);
                    rock.move(direction);
                    if (rock.touch()) rock.move(direction.opposite());
                    rock.move(Direction.DOWN);
                    if (rock.touch()) {
                        rock.move(Direction.UP);
                        break;
                    }
                }
                rock.freeze();
                top = Math.min(top, rock.top());
            }
        }
        public int height(){
            return height-top;
        }

        private Direction nextDirection() {
            Direction d =  switch (s.charAt(cursor++)) {
                case '<' -> Direction.LEFT;
                case '>' -> Direction.RIGHT;
                default -> null;
            };
            if(cursor>=s.length()) cursor = 0;
            return d;
        }

        public void print(int n){
            for (int i = height-n; i < height ; i++) {
                for (int j = 0; j < width; j++) {
                    if(tunnel[i][j]) System.out.print("#");
                    else System.out.print(".");
                }
                System.out.println();
            }
        }

        @AllArgsConstructor
        class Rock {
            private int x;
            private int y;
            private final RockShape shape;

            public void move(Direction direction) {
                this.x += direction.x;
                this.y += direction.y;
            }

            public int top() {
                int min = 0;
                for (int[] rocks : shape.rocks) min = Math.min(min, rocks[0]);
                return x + min;
            }

            public boolean touch() {
                for (int[] rocks : shape.rocks) {
                    int _x = x + rocks[0];
                    int _y = y + rocks[1];
                    if (_x < 0 || _y < 0 || _x >= height || _y >= width) return true;
                    if (tunnel[_x][_y]) return true;
                }
                return false;
            }

            public void freeze() {
                for (int[] rocks : shape.rocks) {
                    int _x = x + rocks[0];
                    int _y = y + rocks[1];
                    tunnel[_x][_y] = true;
                }
            }
        }
    }


    enum RockShape {
        /*
        @###

        .#.
        ###
        @#.

        ..#
        ..#
        @##

        #
        #
        #
        @

        ##
        @#
         */
        HORIZONTAL(new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 0}}),
        CROSS(new int[][]{{0, 1}, {-1, 0}, {-1, 1}, {-2, 1}, {-1,2}}),
        L(new int[][]{{-1, 2}, {-2, 2}, {0, 1}, {0, 2}, {0, 0}}),
        VERTICAL(new int[][]{{-1, 0}, {-2, 0}, {-3, 0}, {0, 0}}),
        SQUARE(new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {0, 0}});

        RockShape(int[][] rocks) {
            this.rocks = rocks;
        }

        int[][] rocks;
    }

    @AllArgsConstructor
    enum Direction {
        UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);
        int x;
        int y;

        public Direction opposite() {
            return switch (this) {
                case UP -> DOWN;
                case RIGHT -> LEFT;
                case LEFT -> RIGHT;
                case DOWN -> UP;
            };
        }
    }
}
