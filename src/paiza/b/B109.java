package paiza.b;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class B109 extends BaseSolution {
    private static final int[][] DIRECTION = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    @Run("9 4 5 2 3\n" +
            "1 0\n" +
            "1 2\n" +
            "1 3\n" +
            "1 4\n" +
            "2 2\n" +
            "2 3\n" +
            "2 4\n" +
            "3 3\n" +
            "3 4")
    public List<int[]> solve(String s) {
        Scanner sc = new Scanner(s);
        int N = sc.nextInt();
        int H = sc.nextInt();
        int W = sc.nextInt();
        int P = sc.nextInt();
        int Q = sc.nextInt();
        boolean[][] seats = new boolean[H][W];
        for (int i = 0; i < N; i++) seats[sc.nextInt()][sc.nextInt()] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{P, Q});
        List<int[]> result = new ArrayList<>();
        boolean[][] visited = new boolean[H][W];
        visited[P][Q] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] p = queue.poll();
                if (!seats[p[0]][p[1]]) result.add(p);
                for (int[] d : DIRECTION) {
                    int x = p[0] + d[0];
                    int y = p[1] + d[1];
                    if (x < 0 || y < 0 || x >= H || y >= W || visited[x][y]) continue;
                    visited[x][y] = true;
                    queue.offer(new int[]{x, y});
                }
            }
            if (result.size() > 0) break;
        }
        return result;
    }

}
