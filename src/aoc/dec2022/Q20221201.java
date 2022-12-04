package aoc.dec2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Q20221201 extends BaseSolution {


    @Run("input-aoc/Q20221201.txt")
    public long solve(String s) {
        Scanner scanner = new Scanner(s);
        long sum = 0;
        String line;
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        while (scanner.hasNextLine()) {
            long cur = 0;
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank()) {
                cur += Integer.parseInt(line);
            }
            priorityQueue.add(cur);
            if (priorityQueue.size() > 3) priorityQueue.poll();
        }
        int size = priorityQueue.size();
        for (int i = 0; i < size; i++) {
            sum += priorityQueue.poll();
        }
        return sum;
    }
}