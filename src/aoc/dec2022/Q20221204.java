package aoc.dec2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Scanner;

public class Q20221204 extends BaseSolution {

    @Run("input-aoc/Q20221204.txt")
    public int part1(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("[-,\n]");
        int count = 0;
        while (scanner.hasNextLine()) {
            if (fullyContain(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt())) count++;
        }
        return count;
    }

    @Run("input-aoc/Q20221204.txt")
    public int part2(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("[-,\n]");
        int count = 0;
        while (scanner.hasNextLine()) {
            if (overlap(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt())) count++;
        }
        return count;
    }

    private static boolean fullyContain(int x, int y, int i, int j) {
        return (x <= i && y >= j) || (i <= x && j >= y);
    }

    private static boolean overlap(int x, int y, int i, int j) {
        return fullyContain(x, y, i, j) ||
                (x <= i && y >= i) ||
                (i <= x && j >= x);
    }

}