package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class Q07 extends BaseSolution {

    @Run({"input-aoc/Q20221207.txt"})
    public long part1(String s) {
        List<int[]> nodes = calculateNodes(s);
        long sum = 0;
        for (int[] node : nodes) if (node[1] <= 100000) sum += node[1];
        return sum;
    }


    @Run({"input-aoc/Q20221207.txt"})
    public Integer part2(String s) {
        List<int[]> nodes = calculateNodes(s);
        int target = nodes.get(0)[1] - 40000000;
        int min = Integer.MAX_VALUE;
        for (int[] node : nodes) if (node[1] >= target) min = Math.min(min, node[1]);
        return min;
    }

    private List<int[]> calculateNodes(String s) {
        Scanner scanner = new Scanner(s);
        StringBuilder current = new StringBuilder("");
        List<int[]> nodes = new ArrayList<>();
        Map<String, Integer> dirIndexMap = new LinkedHashMap<>();
        nodes.add(new int[]{0, 0});
        dirIndexMap.put(current.toString(), 0);
        String line = scanner.nextLine();
        Set<String> calculated = new HashSet<>();
        while (line != null && scanner.hasNextLine()) {
            String[] tokens = line.split(" ");
            switch (tokens[1]) {
                case "cd" -> {
                    cd(current, nodes, dirIndexMap, tokens[2]);
                    line = scanner.nextLine();
                }
                case "ls" -> {
                    if (calculated.contains(current.toString())) break;
                    calculated.add(current.toString());
                    int currentDirSize = 0;
                    while (scanner.hasNextLine() && !(line = scanner.nextLine()).startsWith("$")) {
                        tokens = line.split(" ");
                        if (!tokens[0].equals("dir")) {
                            currentDirSize += Integer.parseInt(tokens[0]);
                        }
                    }
                    nodes.get(dirIndexMap.get(current.toString()))[1] = currentDirSize;
                }
            }
        }
        Map<Integer, List<Integer>> children = new HashMap<>();
        for (int i = 1; i < nodes.size(); i++) {
            int[] node = nodes.get(i);
            List<Integer> child = children.computeIfAbsent(node[0], k -> new ArrayList<>());
            child.add(i);
        }
        computeNodeSize(nodes, children, new boolean[nodes.size()], 0);
        return nodes;
    }

    private int computeNodeSize(List<int[]> nodes, Map<Integer, List<Integer>> children, boolean[] computed, int cur) {
        if (computed[cur]) return nodes.get(cur)[1];
        int[] node = nodes.get(cur);
        List<Integer> child = children.get(cur);
        child = child == null ? Collections.emptyList() : child;
        for (int c : child) node[1] += computeNodeSize(nodes, children, computed, c);
        computed[cur] = true;
        return node[1];
    }

    private void cd(StringBuilder current, List<int[]> nodes, Map<String, Integer> dirIndexMap, String dir) {
        switch (dir) {
            case ".." -> current.delete(current.lastIndexOf("/"), current.length());
            case "/" -> current.delete(0, current.length());
            default -> {
                String parent = current.toString();
                current.append('/').append(dir);
                String c = current.toString();
                if (!dirIndexMap.containsKey(c)) {
                    dirIndexMap.put(c, nodes.size());
                    nodes.add(new int[]{dirIndexMap.get(parent), 0});
                }
            }
        }
    }

}