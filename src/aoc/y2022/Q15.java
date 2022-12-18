package aoc.y2022;

import lombok.Data;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;
import java.util.stream.LongStream;

public class Q15 extends BaseSolution {


    @Run({"input-aoc/Q20221215.txt"})
    public int part1(String s) {
        int ROW = 2000000;
        int COL_OFFSET = 10000000;
        List<Sensor> sensors = parseSensors(s);
        return calcCoverage(sensors, ROW, COL_OFFSET, Integer.MIN_VALUE, Integer.MAX_VALUE).cardinality();
    }


    @Run({"input-aoc/Q20221215.txt"})
    public Long part2(String s) throws InterruptedException {
        int MIN_BOUND = 0;
        int MAX_BOUND = 4000000;
        List<Sensor> sensors = parseSensors(s);
        return LongStream.range(0, MAX_BOUND + 1).parallel().mapToObj(
                (row) -> {
                    BitSet bitSet = calcCoverage(sensors, (int) row, 0, MIN_BOUND, MAX_BOUND);
                    if (bitSet.cardinality() != 4000000) {
                        long x = bitSet.nextClearBit(0);
                        return x * 4000000 + row;
                    }
                    return null;
                }
        ).filter(Objects::nonNull).findFirst().orElse(null);
    }

    private BitSet calcCoverage(List<Sensor> sensors, int row, int offset, int min, int max) {
        BitSet bitSet = new BitSet();
        for (Sensor sensor : sensors) {
            int rowDistance = Math.abs(row - sensor.getY());
            int distance = sensor.getDistance();
            int d = distance - rowDistance;
            if (d < 0) continue;
            if (d == 0) {
                bitSet.set(sensor.getX() + offset, true);
            } else {
                int from = Math.max(min, sensor.getX() - d + offset);
                int to = Math.min(max, sensor.getX() + d + offset + 1);
                bitSet.set(from, to, true);
            }

        }
        return bitSet;
    }

    private List<Sensor> parseSensors(String s) {
        List<Sensor> sensors = new ArrayList<>();
        Scanner scanner = new Scanner(s);
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(": ");
            Point sp = parsePoint(tokens[0].substring(10));
            Point bp = parsePoint(tokens[1].substring(21));
            sensors.add(new Sensor(sp, bp));
        }
        return sensors;
    }

    private Point parsePoint(String s) {
        int index = s.indexOf(", ");
        int x = Integer.parseInt(s.substring(2, index));
        int y = Integer.parseInt(s.substring(index + 4));
        return new Point(x, y);
    }


    @Data
    static class Point {
        private final int x;
        private final int y;
    }

    static class Sensor extends Point {
        private final int distance;

        public Sensor(Point self, Point beacons) {
            super(self.getX(), self.getY());
            this.distance = distance(beacons);
        }

        private int distance(Point beacons) {
            return Math.abs(getX() - beacons.getX()) + Math.abs(getY() - beacons.getY());
        }

        private int getDistance() {
            return distance;
        }
    }
}
