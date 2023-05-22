package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q13 extends BaseSolution {

    @Run({"input-aoc/Q20221213.txt"})
    public int part1(String s) {
        int index = 1;
        int sum = 0;
        Scanner scanner = new Scanner(s);
        while (scanner.hasNextLine()) {
            IIntegerList list1 = IIntegerList.parseIntegerList(scanner.nextLine());
            IIntegerList list2 = IIntegerList.parseIntegerList(scanner.nextLine());
            if (list1.compareTo(list2) < 0) sum += index;
            if (scanner.hasNextLine()) scanner.nextLine();
            index++;
        }
        return sum;
    }


    @Run({"input-aoc/Q20221213.txt"})
    public int part2(String s) {
        Scanner scanner = new Scanner(s);
        String line;
        List<IIntegerList> packets = new ArrayList<>();
        IntegerList divider1 = new IntegerList(List.of(new IntegerList(List.of(new SingleInteger(2)))));
        IntegerList divider2 = new IntegerList(List.of(new IntegerList(List.of(new SingleInteger(6)))));
        packets.add(divider1);
        packets.add(divider2);
        while (scanner.hasNextLine()) {
            if ((line = scanner.nextLine()).isBlank()) continue;
            IIntegerList list = IIntegerList.parseIntegerList(line);
            packets.add(list);
        }
        Collections.sort(packets);
        return packets.indexOf(divider1) * packets.indexOf(divider2);
    }


    interface IIntegerList extends Comparable<IIntegerList> {

        boolean isInteger();

        default int getInteger() {
            throw new UnsupportedOperationException();
        }

        default List<IIntegerList> getList() {
            throw new UnsupportedOperationException();
        }

        static IIntegerList parseIntegerList(String s) {
            if (s.length() == 0) return new IntegerList(Collections.emptyList());
            s = s.substring(1, s.length() - 1);
            List<IIntegerList> list = new ArrayList<>();
            List<String> split = split(s);
            for (String elm : split) {
                if (elm.startsWith("[")) {
                    list.add(parseIntegerList(elm));
                } else {
                    list.add(new SingleInteger(Integer.parseInt(elm)));
                }
            }
            return new IntegerList(list);
        }

        private static List<String> split(String s) {
            int level = 0;
            int prev = 0;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case ',':
                        if (level == 0) {
                            list.add(s.substring(prev, i));
                            prev = i + 1;
                        }
                        break;
                    case '[':
                        level++;
                        break;
                    case ']':
                        level--;
                        break;
                }
            }
            if (prev != s.length()) list.add(s.substring(prev));
            return list;
        }
    }

    record SingleInteger(int n) implements IIntegerList {

        @Override
        public boolean isInteger() {
            return true;
        }

        @Override
        public int getInteger() {
            return n;
        }

        @Override
        public int compareTo(IIntegerList o) {
            return o.isInteger() ? n - o.getInteger() : toIntegerList().compareTo(o);
        }

        private IIntegerList toIntegerList() {
            return new IntegerList(List.of(this));
        }

    }

    record IntegerList(List<IIntegerList> list) implements IIntegerList {

        @Override
        public boolean isInteger() {
            return false;
        }

        @Override
        public List<IIntegerList> getList() {
            return list;
        }

        @Override
        public int compareTo(IIntegerList o) {
            if (o.isInteger()) return -1 * o.compareTo(this);
            List<IIntegerList> oList = o.getList();
            int min = Math.min(list.size(), oList.size());
            for (int i = 0; i < min; i++) {
                int comp = list.get(i).compareTo(oList.get(i));
                if (comp != 0) return comp;
            }
            return list.size() - oList.size();
        }

    }
}
