package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q20 extends BaseSolution {


    @Run({"input-aoc/Q20221220.txt"})
    public int part1(String s) {
        List<Node> nodes = parseNodes(s);
        int sum = 0;
        for (Node node : nodes) node.move(nodes.size());
        Node cur = nodes.stream().filter(n -> n.val == 0).findFirst().get();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1000; j++) cur = cur.next;
            sum += cur.val;
        }
        return sum;
    }

    @Run({"input-aoc/Q20221220.txt"})
    public long part2(String s) {
        int key = 811589153;
        List<Node> nodes = parseNodes(s);
        for (Node node : nodes) node.multiply(key);
        for (int i = 0; i < 10; i++) for (Node node : nodes) node.move(nodes.size());
        long sum = 0;
        Node cur = nodes.stream().filter(n -> n.val == 0).findFirst().get();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1000; j++) cur = cur.next;
            sum += cur.val;
        }
        return sum;
    }

    static class Node {
        private Node next, prev;
        private long val;

        public Node(int val) {
            this.val = val;
        }

        public void multiply(int key) {
            val *= key;
        }

        public void move(int size) {
            int step = (int) (val % (size - 1));
            if (step == 0) return;
            Node[] target = step > 0 ? forward(step) : backward(step);
            Node next = this.next;
            Node prev = this.prev;
            Node targetPrev = target[0];
            Node targetNext = target[1];
            prev.next = next;
            next.prev = prev;
            targetPrev.next = this;
            targetNext.prev = this;
            this.next = targetNext;
            this.prev = targetPrev;
        }

        // targetPrev, targetNext
        private Node[] forward(int step) {
            Node cur = this;
            for (int i = 0; i < step; i++) cur = cur.next;
            return new Node[]{cur, cur.next};
        }

        private Node[] backward(int step) {
            step = Math.abs(step);
            Node cur = this;
            for (int i = 0; i < step; i++) cur = cur.prev;
            return new Node[]{cur.prev, cur};
        }


    }

    private List<Node> parseNodes(String s) {
        Scanner scanner = new Scanner(s);
        List<Node> nodes = new ArrayList<>();
        while (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            nodes.add(new Node(val));
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node cur = nodes.get(i);
            Node next = nodes.get((i + 1) % nodes.size());
            cur.next = next;
            next.prev = cur;
        }
        return nodes;
    }
}
