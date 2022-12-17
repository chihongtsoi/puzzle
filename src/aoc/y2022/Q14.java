package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Q14 extends BaseSolution {

    @Run({"input-aoc/Q20221214.txt"})
    public int part1(String s) {
        int mx = 0, my = 0;
        List<List<int[]>> paths = resolvePaths(s);
        for (List<int[]> path : paths) {
            for (int[] p : path) {
                mx = Math.max(mx, p[0]);
                my = Math.max(my, p[1]);
            }
        }
        Reservoir reservoir = new Reservoir(my, mx, paths);
        int drop = 0;
        while (reservoir.drop()) drop++;
        return drop;
    }


    @Run({"input-aoc/Q20221214.txt"})
    public int part2(String s) {
        List<List<int[]>> paths = resolvePaths(s);
        int mx = 0, my = 0;
        for (List<int[]> path : paths) {
            for (int[] p : path) {
                mx = Math.max(mx, p[0]);
                my = Math.max(my, p[1]);
            }
        }
        my += 2;
        mx *= 2;
        paths.add(List.of(new int[]{0, my}, new int[]{mx, my}));
        Reservoir reservoir = new Reservoir(my, mx, paths);
        int drop = 0;
        while (++drop > 0 && reservoir.drop() && reservoir.getY() >= 0) ;
        return drop;
    }


    static class Reservoir {
        private static final int DROP_X = 500;
        private final char[][] space;
        private int dropYMemo = 0;
        private final int m;
        private final int n;

        public Reservoir(int m, int n, List<List<int[]>> paths) {
            this.m = ++m;
            this.n = ++n;
            space = new char[this.m + 1][this.n + 1];
            for (int i = 0; i < space.length; i++) Arrays.fill(space[i], '.');
            for (List<int[]> path : paths) drawPath(path);
            for (int i = 0; i < n; i++) {
                if (space[i][DROP_X] == '#') {
                    dropYMemo = i - 1;
                    break;
                }
            }
        }

        public boolean drop() {
            int x = DROP_X;
            int y = dropYMemo;
            space[y][x] = '+';
            boolean move = false;
            while (x >= 0 && y >= 0 && x < n && y < m) {
                if (space[y + 1][x] == '.') {
                    swap(x, y, x, ++y);
                } else if (space[y + 1][x - 1] == '.') {
                    swap(x, y, --x, ++y);
                } else if (space[y + 1][x + 1] == '.') {
                    swap(x, y, ++x, ++y);
                } else {
                    if (!move) dropYMemo--;
                    break;
                }
                move = true;
            }
            return !(y == m || x == n);
        }

        public int getY() {
            return dropYMemo;
        }

        private void swap(int x1, int y1, int x2, int y2) {
            char t = space[y1][x1];
            space[y1][x1] = space[y2][x2];
            space[y2][x2] = t;
        }

        private void drawPath(List<int[]> path) {
            for (int i = 1; i < path.size(); i++) {
                int[] from = path.get(i - 1);
                int[] to = path.get(i);
                int fx, fy, tx, ty, rx, ry;
                fx = from[0];
                fy = from[1];
                tx = to[0];
                ty = to[1];
                rx = (tx == fx) ? 0 : (tx - fx) / Math.abs(tx - fx);
                ry = (ty == fy) ? 0 : (ty - fy) / Math.abs(ty - fy);
                while (!(fx == tx && fy == ty)) {
                    space[fy][fx] = '#';
                    fx += rx;
                    fy += ry;
                }
                space[fy][fx] = '#';
            }
        }

        public void print() {
            for (int i = 0; i < space.length; i++) System.out.println(Arrays.toString(space[i]));
        }
    }

    private static List<List<int[]>> resolvePaths(String s) {
        Scanner scanner = new Scanner(s);
        int mx = 0, my = 0;
        List<List<int[]>> paths = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(" -> ");
            List<int[]> path = new ArrayList<>();
            for (String t : tokens) {
                int index = t.indexOf(',');
                int x = Integer.parseInt(t.substring(0, index));
                int y = Integer.parseInt(t.substring(index + 1));
                path.add(new int[]{x, y});
                mx = Math.max(mx, x);
                my = Math.max(my, y);
            }
            paths.add(path);
        }
        return paths;
    }
}
