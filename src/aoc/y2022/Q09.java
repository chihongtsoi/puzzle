package aoc.y2022;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q09 extends BaseSolution {


    @Run({"input-aoc/Q20221209.txt"})
    public int part1(String s) {
        Scanner scanner = new Scanner(s);
        Position ropeHead = new Position();
        Position ropeTail = new Position();
        Set<Position> path = new HashSet<>();
        path.add(ropeTail.clone());
        while (scanner.hasNextLine()) {
            Direction direction = Direction.valueOf(scanner.next());
            int step = scanner.nextInt();
            for (int i = 0; i < step; i++) {
                ropeHead.move(direction);
                if (!ropeHead.touching(ropeTail)) ropeTail.pullFrom(ropeHead);
                path.add(ropeTail);
            }
        }
        return path.size();
    }


    @Run({"input-aoc/Q20221209.txt"})
    public int part2(String s) {
        Scanner scanner = new Scanner(s);
        Deque<Position> ropes = new LinkedList<>();
        for (int i = 0; i < 10; i++) ropes.add(new Position());
        Set<Position> path = new HashSet<>();
        path.add(ropes.getLast().clone());
        while (scanner.hasNextLine()) {
            Direction direction = Direction.valueOf(scanner.next());
            int step = scanner.nextInt();
            for (int i = 0; i < step; i++) {
                Iterator<Position> it = ropes.iterator();
                Position prev = it.next();
                prev.move(direction);
                while (it.hasNext()) {
                    Position cur = it.next();
                    if (!prev.touching(cur)) cur.pullFrom(prev);
                    else break;
                    prev = cur;
                }
                path.add(ropes.getLast().clone());
            }
        }
        return path.size();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    static class Position implements Cloneable {
        private int x, y;

        @Override
        protected Position clone() {
            return new Position(x, y);
        }

        public boolean touching(Position rope) {
            return !((Math.abs(this.x - rope.x) > 1) || (Math.abs(this.y - rope.y) > 1));
        }

        public void move(Direction d) {
            x += d.x;
            y += d.y;
        }

        public void pullFrom(Position rope) {
            Direction d = getPullDirection(x - rope.x, y - rope.y);
            x = rope.x + d.x;
            y = rope.y + d.y;
        }

        private static Direction getPullDirection(int x, int y) {
            if (Math.abs(x) > Math.abs(y)) {
                if (x > 0) return Direction.U;
                return Direction.D;
            } else if (Math.abs(x) < Math.abs(y)) {
                if (y > 0) return Direction.R;
                return Direction.L;
            }
            if (x == 2 && y == 2) return Direction.UR;
            if (x == -2 && y == -2) return Direction.DL;
            if (x == 2 && y == -2) return Direction.UL;
            if (x == -2 && y == 2) return Direction.DR;
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return x * 1000000 + y;
        }
    }


    @AllArgsConstructor
    enum Direction {
        U(1, 0), UR(1, 1), R(0, 1), DR(-1, 1),
        D(-1, 0), DL(-1, -1), L(0, -1), UL(1, -1);

        int x, y;

    }
}
