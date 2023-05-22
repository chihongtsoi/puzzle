package aoc.y2022;

import lombok.AllArgsConstructor;
import lombok.Data;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q23 extends BaseSolution {


    @Run({"input-aoc/Q20221223.txt"})
    public int part1(String s) {
        Ground ground = new Ground(s);
        for (int i = 0; i < 10; i++) ground.round();
        return ground.emptyGround();
    }

    @Run({"input-aoc/Q20221223.txt"})
    public int part2(String s) {
        Ground ground = new Ground(s);
        for (int i = 1; ; i++) if (!ground.round()) return i;
    }

    static class Ground {
        private final Set<Position> elves;
        private final Position reuse = new Position(0, 0);
        private int preferOffset;

        public Ground(String s) {
            elves = new HashSet<>();
            String[] lines = s.split("\n");
            for (int i = 0; i < lines.length; i++) {
                for (int j = 0; j < lines[i].length(); j++) {
                    if (lines[i].charAt(j) == '#') elves.add(new Position(i, j));
                }
            }
        }

        public boolean round() {
            Map<Position, List<Position>> propose = new HashMap<>();
            for (Position elf : elves) {
                if (noElfAround(elf)) continue;
                for (int i = 0; i < 4; i++) {
                    Direction direction = Direction.values()[(preferOffset + i) % Direction.values().length];
                    if (checkDirection(elf, direction)) {
                        propose.compute(elf.move(direction.direction), (k, v) -> {
                            if (v == null) v = new ArrayList<>();
                            v.add(elf);
                            return v;
                        });
                        break;
                    }
                }
            }
            int move = 0;
            for (Map.Entry<Position, List<Position>> entry : propose.entrySet()) {
                if (entry.getValue().size() > 1) continue;
                Position elf = entry.getKey();
                elves.add(elf);
                elves.remove(entry.getValue().get(0));
                move++;
            }
            preferOffset++;
            return move > 0;
        }

        public int emptyGround() {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = 0;
            int maxY = 0;
            for (Position elf : elves) {
                minX = Math.min(minX, elf.x);
                minY = Math.min(minY, elf.y);
                maxX = Math.max(maxX, elf.x);
                maxY = Math.max(maxY, elf.y);
            }
            int width = maxY - minY + 1;
            int height = maxX - minX + 1;
            return width * height - elves.size();
        }


        private boolean noElfAround(Position elf) {
            int x = elf.x;
            int y = elf.y;
            for (EightDirection dir : EightDirection.values()) {
                int _x = x + dir.x;
                int _y = y + dir.y;
                if (elves.contains(reuse.set(_x, _y))) return false;
            }
            return true;
        }

        private boolean checkDirection(Position elf, Direction direction) {
            int x = elf.x;
            int y = elf.y;
            for (EightDirection dir : direction.directions) {
                int _x = x + dir.x;
                int _y = y + dir.y;
                if (elves.contains(reuse.set(_x, _y))) return false;
            }
            return true;
        }

    }

    @Data
    @AllArgsConstructor
    static class Position {
        private int x;
        private int y;

        public Position move(EightDirection direction) {
            return new Position(this.x + direction.x, this.y + direction.y);
        }

        public Position set(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return x * 10000 + y;
        }
    }


    @AllArgsConstructor
    enum EightDirection {
        E(0, 1), SE(1, 1), S(1, 0), SW(1, -1),
        W(0, -1), NW(-1, -1), N(-1, 0), NE(-1, 1);
        int x, y;
    }


    enum Direction {
        NORTH(EightDirection.N, EightDirection.NE, EightDirection.NW),
        SOUTH(EightDirection.S, EightDirection.SE, EightDirection.SW),
        WEST(EightDirection.W, EightDirection.NW, EightDirection.SW),
        EAST(EightDirection.E, EightDirection.NE, EightDirection.SE);

        Direction(EightDirection... directions) {
            this.directions = directions;
            this.direction = directions[0];
        }

        EightDirection[] directions;
        EightDirection direction;
    }
}
