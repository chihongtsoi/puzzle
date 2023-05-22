package aoc.y2022;

import lombok.Data;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Q21 extends BaseSolution {


    @Run({"input-aoc/Q20221221.txt"})
    public long part1(String s) {
        Solver solver = new Solver(s);
        return solver.getRootResult();
    }

    @Run({"input-aoc/Q20221221.txt"})
    public long part2(String s) {
        Solver solver = new Solver(s, false);
        long l = 0;
        long r = 5000000000000L;
        while(l<=r){
            long m = l+(r-l)/2;
            solver.setFakeMonkey(m);
            long diff = solver.rootDiff();
            if(diff==0) return m;
            if(diff>0) l = m+1;
            else r = m-1;
        }
        return -1;
    }

    static class Solver {

        private final boolean cache;
        private static final String fakeMonkey = "humn";
        private final Map<String, Monkey> monkeys;

        public Solver(String s) {
            this(s, true);
        }
        public Solver(String s, boolean cache) {
            this.monkeys = new HashMap<>();
            parseMonkey(s);
            this.cache = cache;
        }
        public void setFakeMonkey(long fake){
            monkeys.put(fakeMonkey, new NumberMonkey(fake));
        }


        public long rootDiff(){
            OperationMonkey root = (OperationMonkey) monkeys.get("root");
            Monkey first = monkeys.get(root.getFirst());
            Monkey second = monkeys.get(root.getSecond());
            return first.getResult() - second.getResult();
        }

        public long getRootResult() {
            return monkeys.get("root").getResult();
        }

        interface Monkey {
            long getResult();
        }

        record NumberMonkey(long num) implements Monkey {

            @Override
            public long getResult() {
                return num;
            }

        }

        @Data
        class OperationMonkey implements Monkey {

            private final String first;
            private final String second;
            private final char operation;
            private Long result;

            public OperationMonkey(String first, char operation, String second) {
                this.first = first;
                this.second = second;
                this.operation = operation;
            }

            @Override
            public long getResult() {
                if (cache && result != null) return result;
                long num1 = monkeys.get(first).getResult();
                long num2 = monkeys.get(second).getResult();
                return (result = calc(num1, num2));
            }

            private long calc(long num1, long num2) {
                return switch (operation) {
                    case '+' -> num1 + num2;
                    case '-' -> num1 - num2;
                    case '*' -> num1 * num2;
                    case '/' -> num1 / num2;
                    default -> Integer.MIN_VALUE;
                };
            }
        }

        private void parseMonkey(String s) {
            Scanner scanner = new Scanner(s);
            while (scanner.hasNext()) {
                String name = scanner.next();
                name = name.substring(0, name.length() - 1);
                Solver.Monkey monkey;
                if (scanner.hasNextLong()) {
                    monkey = new Solver.NumberMonkey(scanner.nextLong());
                } else {
                    monkey = new OperationMonkey(scanner.next(), scanner.next().charAt(0), scanner.next());
                }
                monkeys.put(name, monkey);
            }
        }
    }


}
