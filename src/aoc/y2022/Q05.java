package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q05 extends BaseSolution {

    @Run("input-aoc/Q20221205.txt")
    public String part1(String s) {

        Scanner scanner = new Scanner(s);
        StringBuilder str = new StringBuilder();
        String line;
        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank()) {
            str.append(line);
            str.append("\n");
        }
        List<Deque<Character>> stacks = parseInput(str.toString());
        scanner.useDelimiter("(move | from | to |\nmove )");
        while (scanner.hasNextLine()) {
            int move = scanner.nextInt();
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            if (scanner.hasNextLine()) scanner.skip("\n");
            Deque<Character> fromStack = stacks.get(from);
            Deque<Character> toStack = stacks.get(to);
            for (int i = 0; i < move; i++) {
                toStack.addLast(fromStack.removeLast());
            }
        }
        StringBuilder result = new StringBuilder();
        for (Deque<Character> stack : stacks) {
            result.append(stack.getLast());
        }
        return result.toString();
    }

    @Run("input-aoc/Q20221205.txt")
    public String part2(String s) {
        Scanner scanner = new Scanner(s);
        StringBuilder str = new StringBuilder();
        String line;
        while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank()) {
            str.append(line);
            str.append("\n");
        }
        List<Deque<Character>> stacks = parseInput(str.toString());
        Deque<Character> tmp = new LinkedList<>();

        scanner.useDelimiter("(move | from | to |\nmove )");
        while (scanner.hasNextLine()) {
            int move = scanner.nextInt();
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;

            Deque<Character> fromStack = stacks.get(from);
            Deque<Character> toStack = stacks.get(to);
            for (int i = 0; i < move; i++) {
                tmp.addLast(fromStack.removeLast());
            }
            for (int i = 0; i < move; i++) {
                toStack.addLast(tmp.removeLast());
            }
        }
        StringBuilder result = new StringBuilder();
        for (Deque<Character> stack : stacks) {
            result.append(stack.getLast());
        }
        return result.toString();
    }


    public List<Deque<Character>> parseInput(String s) {
        List<Deque<Character>> result = new ArrayList<>();
        String[] lines = s.split("\n");
        for (String line : lines) {
            int index = 0;
            for (int j = 1; j < line.length(); j += 4, index++) {
                if (index >= result.size()) {
                    result.add(new LinkedList<>());
                }
                if (!Character.isAlphabetic(line.charAt(j))) continue;
                result.get(index).addFirst(line.charAt(j));

            }
        }
        return result;
    }
}