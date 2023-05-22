package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Scanner;

public class Q03 extends BaseSolution {

    @Run("input-aoc/Q20221203.txt")
    public int solve(String s) {
        Scanner scanner = new Scanner(s);
        int total = 0;
        while (scanner.hasNextLine()) {
            byte[] contain = new byte[52];
            for (int i = 0; i < 3; i++) {
                String line = scanner.nextLine();
                int mask = 1 << i;
                for (char c : line.toCharArray()) contain[toInt(c)] |= mask;
            }
            for (int i = 0; i < 52; i++) {
                if (contain[i] ==  0b111) {
                    total += i + 1;
                    break;
                }
            }
        }
        return total;
    }

    public int toInt(char c) {
        return c >= 'a' ? c - 'a' : c - 'A' + 26;
    }
}