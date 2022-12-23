package paiza.b;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.*;

public class B066 extends BaseSolution {


    @Run({"10 6 3\n" +
            "YYYBYB\n" +
            "BYYBBB\n" +
            "BYYBBB\n" +
            "BYYBYB\n" +
            "BBYYYY\n" +
            "YYYYYB\n" +
            "YYB\n" +
            "BBY\n" +
            "BYB\n" +
            "BBY\n" +
            "YBB\n" +
            "BYB\n" +
            "BBY\n" +
            "YBB\n" +
            "YYB\n" +
            "BBB\n" +
            "BBB\n" +
            "BYB\n" +
            "BYB\n" +
            "BYB\n" +
            "YYB\n" +
            "YYY\n" +
            "YYY\n" +
            "YBY\n" +
            "YYY\n" +
            "YBB\n" +
            "YYB\n" +
            "YYB\n" +
            "YYB\n" +
            "YYY\n" +
            "YYB\n" +
            "YYY\n" +
            "BYB\n" +
            "BBB\n" +
            "YYB\n" +
            "BYY"})
    public String solve(String s) {
        Scanner scanner = new Scanner(s);
        StringBuilder out = new StringBuilder();
        int X = scanner.nextInt();
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        // true: Y, false: B
        boolean[][] matrix = new boolean[N][N];
        scanner.nextLine();
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j) == 'Y';
            }
        }
        Map<BitSet, Queue<int[]>> puzzleParts = new HashMap<>();
        for (int x = 0; x < N; x += M) {
            for (int y = 0; y < N; y += M) {
                final int _x = x;
                final int _y = y;
                puzzleParts.compute(toBitSet(matrix, x, y, M), (k, v) -> {
                    if (v == null) v = new LinkedList<>();
                    v.add(new int[]{_x + 1, _y + 1});
                    return v;
                });
            }
        }
        Queue<int[]> q;
        for (int i = 0; i < X; i++) {
            boolean[][] puzzle = new boolean[M][M];
            for (int j = 0; j < M; j++) {
                String line = scanner.nextLine();
                for (int k = 0; k < M; k++) {
                    puzzle[j][k] = line.charAt(k) == 'Y';
                }
            }
            int j;
            int[] place = null;

            for (j = 0; j < 4; j++) {
                BitSet puzzleBits = toBitSet(puzzle, 0, 0, M);
                if ((q = puzzleParts.get(puzzleBits)) != null) {
                    place = q.poll();
                    if (q.isEmpty()) puzzleParts.remove(puzzleBits);
                    break;
                }
                rotate(puzzle, M);
            }
            if (place == null) out.append("-1");
            else out.append(place[0]).append(" ").append(place[1]).append(" ").append(j % 4);
            out.append("\n");
        }
        return out.toString();
    }

    private static BitSet toBitSet(boolean[][] matrix, int x, int y, int M) {
        BitSet bits = new BitSet();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                bits.set(i * M + j, matrix[x + i][y + j]);
            }
        }
        return bits;
    }

    private static void rotate(boolean[][] matrix, int M) {
        for (int i = 0; i < M / 2; i++) {
            for (int j = i; j < M - i - 1; j++) {
                boolean tmp = matrix[i][j];
                matrix[i][j] = matrix[M - 1 - j][i];
                matrix[M - 1 - j][i] = matrix[M - 1 - i][M - 1 - j];
                matrix[M - 1 - i][M - 1 - j] = matrix[j][M - 1 - i];
                matrix[j][M - 1 - i] = tmp;
            }
        }
    }
}
