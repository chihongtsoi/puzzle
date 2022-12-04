package aoc.dec2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Scanner;

public class Q20221202 extends BaseSolution {


    //Rock,  Paper, Scissors
    private static final int[][] LOOKUP = {
            {3, 6, 0},
            {0, 3, 6},
            {6, 0, 3},
    };

    @Run("input-aoc/Q20221202.txt")
    public int solve(String s) {
        Scanner scanner = new Scanner(s);

        int score = 0;
        while (scanner.hasNext()) {
            int opp = toInt(scanner.next().charAt(0));
            int me = toInt(scanner.next().charAt(0));
            me = findMe(opp, me * 3);
            score += me + 1;
            score += LOOKUP[opp][me];
        }
        return score;
    }

    private static int toInt(char c) {
        return c >= 'X' ? c - 'X' : c - 'A';
    }

    private static int findMe(int opp, int i) {
        for (int j = 0; j < 3; j++) {
            if (LOOKUP[opp][j] == i) return j;
        }
        throw new IllegalStateException();
    }
}