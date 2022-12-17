package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q11 extends BaseSolution {

    private static final String OLD = "old";

    @Run({"input-aoc/Q20221211.txt"})
    public long part1(String s) {
        Scanner scanner = new Scanner(s);
        List<Monkey> monkeys = new ArrayList<>();
        while (scanner.hasNextLine()) monkeys.add(Monkey.createMonkey(scanner));
        for (int i = 0; i < 20; i++) Monkey.round(monkeys);
        return Monkey.business(monkeys);
    }


    @Run({"input-aoc/Q20221211.txt"})
    public long part2(String s) {
        Scanner scanner = new Scanner(s);
        List<Monkey> monkeys = new ArrayList<>();
        while (scanner.hasNextLine()) monkeys.add(Monkey.createMonkey(scanner));
        long mod = 1;
        for (Monkey monkey : monkeys) {
            mod *= monkey.divisibleBy;
            monkey.operation = Operation2.fromOperation(monkey.operation);
        }
        Operation2.mod = mod;
        for (int i = 0; i < 10000; i++) Monkey.round(monkeys);
        return Monkey.business(monkeys);
    }


    @AllArgsConstructor
    static class Monkey {
        private final int divisibleBy;
        private final int throwIfTrue;
        private final int throwIfFalse;
        private Operation operation;
        private final Deque<Long> items;

        private int count;

        public void turn(List<Monkey> allMonkey) {
            while (!items.isEmpty()) {
                Long item = items.removeFirst();
                item = operation.apply(item);
                throwItem(item, allMonkey);
                count++;
            }
        }

        public int getCount() {
            return count;
        }

        private void throwItem(long item, List<Monkey> allMonkey) {
            int monkey = item % divisibleBy == 0 ? throwIfTrue : throwIfFalse;
            throwItemTo(monkey, item, allMonkey);
        }

        private static void throwItemTo(int monkey, long item, List<Monkey> allMonkey) {
            allMonkey.get(monkey).items.add(item);
        }

        public static Monkey createMonkey(Scanner scanner) {
            scanner.nextLine();
            String itemsStr = scanner.nextLine().substring(18);
            Deque<Long> items = new LinkedList<>();
            for (String item : itemsStr.split(", ")) {
                items.add(Long.parseLong(item));
            }
            String operationStr = scanner.nextLine().substring(19);
            Operation operation = Operation.createOperation(operationStr);
            int divisibleBy = Integer.parseInt(scanner.nextLine().substring(21));
            int throwIfTrue = Integer.parseInt(scanner.nextLine().substring(29));
            int throwIfFalse = Integer.parseInt(scanner.nextLine().substring(30));
            if (scanner.hasNext()) scanner.nextLine();
            return new Monkey(divisibleBy, throwIfTrue, throwIfFalse, operation, items, 0);
        }

        public static void round(List<Monkey> monkeys) {
            for (Monkey monkey : monkeys) monkey.turn(monkeys);
        }

        public static long business(List<Monkey> monkeys) {
            PriorityQueue<Long> heap = new PriorityQueue<>();
            for (Monkey monkey : monkeys) {
                heap.add((long) monkey.getCount());
                if (heap.size() > 2) heap.poll();
            }
            return heap.poll() * heap.poll();
        }
    }

    @AllArgsConstructor
    static class Operation {

        private final Long var1;
        private final char op;
        private final Long var2;

        public long apply(long old) {
            long v1 = Optional.ofNullable(var1).orElse(old);
            long v2 = Optional.ofNullable(var2).orElse(old);
            long newVal = switch (op) {
                case '+' -> v1 + v2;
                case '-' -> v1 - v2;
                case '*' -> v1 * v2;
                case '/' -> v1 / v2;
                default -> throw new UnsupportedOperationException();
            };
            return bored(newVal);
        }

        public long bored(long old) {
            return old / 3;
        }

        public static Operation createOperation(String exp) {
            String[] operationTokens = exp.split(" ");
            return new Operation(
                    OLD.equals(operationTokens[0]) ? null : Long.parseLong(operationTokens[0]),
                    operationTokens[1].charAt(0),
                    OLD.equals(operationTokens[2]) ? null : Long.parseLong(operationTokens[2]));
        }

    }

    static class Operation2 extends Operation {

        public static long mod;

        public Operation2(Long var1, char op, Long var2) {
            super(var1, op, var2);
        }

        @Override
        public long bored(long old) {
            return old % mod;
        }

        public static Operation2 fromOperation(Operation operation) {
            return new Operation2(operation.var1, operation.op, operation.var2);
        }
    }

}
