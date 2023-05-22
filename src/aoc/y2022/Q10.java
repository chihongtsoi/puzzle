package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Q10 extends BaseSolution {


    @Run({"input-aoc/Q20221210.txt"})
    public int part1(String s) {
        List<Integer> cycles = calculateCycles(s);
        int sum = 0;
        for (int i = 20; i <= 220; i += 40) sum += cycles.get(i - 1) * i;
        return sum;
    }


    @Run({"input-aoc/Q20221210.txt"})
    public String part2(String s) {
        List<Integer> cycles = calculateCycles(s);
        char[][] screen = new char[6][40];
        for (int i = 0; i < 6; i++) {
            int start = i * 40;
            for (int j = 0; j < 40; j++) {
                int sprite = cycles.get(start + j);
                screen[i][j] = (j >= sprite - 1 && j <= sprite + 1) ? '#' : '.';
            }
        }
        String display = String.join("\n",
                Arrays.stream(screen).map(String::new)
                        .collect(Collectors.toList()));

        return "\n" + display;
    }

    private List<Integer> calculateCycles(String s) {
        Scanner scanner = new Scanner(s);
        List<Integer> cycles = new ArrayList<>();
        int prev = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            cycles.add(prev);
            if (line.startsWith("addx")) {
                int inc = Integer.parseInt(line.substring(5));
                cycles.add(prev);
                prev += inc;
            }
        }
        return cycles;
    }

}
