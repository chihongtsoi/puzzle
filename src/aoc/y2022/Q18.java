package aoc.y2022;

import lombok.AllArgsConstructor;
import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q18 extends BaseSolution {

    private static final int SPACE = 20;

    @Run({"input-aoc/Q20221218.txt"})
    public int part1(String s) {
        List<int[]> cubes = parseCubes(s);
        boolean[][][] space = new boolean[SPACE][SPACE][SPACE];
        int faces = cubes.size() * 6;
        for (int[] cube : cubes) {
            int x = cube[0];
            int y = cube[1];
            int z = cube[2];
            space[x][y][z] = true;
            for (Face face : Face.values()) {
                int _x = x + face.x;
                int _y = y + face.y;
                int _z = z + face.z;
                if (inBound(_x, SPACE) && inBound(_y, SPACE) && inBound(_z, SPACE) && space[_x][_y][_z]) faces -= 2;
            }
        }
        return faces;
    }

    @Run({"input-aoc/Q20221218.txt"})
    public int part2(String s) throws InterruptedException {

        List<int[]> cubes = parseCubes(s);
        boolean[][][] space = new boolean[SPACE][SPACE][SPACE];
        int faces = cubes.size() * 6;
        for (int[] cube : cubes) {
            int x = cube[0];
            int y = cube[1];
            int z = cube[2];
            space[x][y][z] = true;
            for (Face face : Face.values()) {
                int _x = x + face.x;
                int _y = y + face.y;
                int _z = z + face.z;
                if (inBound(_x, SPACE) && inBound(_y, SPACE) && inBound(_z, SPACE) && space[_x][_y][_z]) faces -= 2;
            }
        }

        boolean[][][] visited = new boolean[SPACE][SPACE][SPACE];
        boolean[][][] counted = new boolean[SPACE][SPACE][SPACE];
        for (int x = 0; x < SPACE; x++) {
            for (int y = 0; y < SPACE; y++) {
                for (int z = 0; z < SPACE; z++) {
                    if (space[x][y][z] || visited[x][y][z]) continue;
                    visited[x][y][z] = true;
                    boolean surround = true;
                    boolean[][][] traveled = new boolean[SPACE][SPACE][SPACE];
                    traveled[x][y][z] = true;
                    for (Face face : Face.values()) {
                        int _x = x + face.x;
                        int _y = y + face.y;
                        int _z = z + face.z;
                        if (!inBound(_x, SPACE) || !inBound(_y, SPACE) || !inBound(_z, SPACE) || (!space[_x][_y][_z]
                                && !dfs(traveled, visited, space, _x, _y, _z))) {
                            surround = false;
                            break;
                        }
                    }
                    if (surround) faces -= dfsCount(space, counted, x, y, z);
                }
            }
        }
        return faces;
    }

    // TODO combine these two method to one method
    private int dfsCount(boolean[][][] space, boolean[][][] counted, int x, int y, int z) {
        int count = 0;
        counted[x][y][z] = true;
        for (Face face : Face.values()) {
            int _x = x + face.x;
            int _y = y + face.y;
            int _z = z + face.z;
            if (space[_x][_y][_z]) count++;
            else if (!counted[_x][_y][_z]) count += dfsCount(space, counted, _x, _y, _z);
        }
        return count;
    }

    private boolean dfs(boolean[][][] traveled, boolean[][][] visited, boolean[][][] space, int x, int y, int z) {
        visited[x][y][z] = true;
        traveled[x][y][z] = true;
        for (Face face : Face.values()) {
            int _x = x + face.x;
            int _y = y + face.y;
            int _z = z + face.z;
            if (!inBound(_x, SPACE) || !inBound(_y, SPACE) || !inBound(_z, SPACE) || (!space[_x][_y][_z]
                    && !traveled[_x][_y][_z] && !dfs(traveled, visited, space, _x, _y, _z))) return false;
        }
        return true;
    }

    private boolean inBound(int i, int b) {
        return i >= 0 && i < b;
    }

    private List<int[]> parseCubes(String s) {
        Scanner scanner = new Scanner(s).useDelimiter("\n|,");
        List<int[]> cubes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            cubes.add(new int[]{scanner.nextInt(), scanner.nextInt(), scanner.nextInt()});
            if (scanner.hasNextLine()) scanner.nextLine();
        }
        return cubes;
    }

    @AllArgsConstructor
    enum Face {
        UP(0, 1, 0), NORTH(0, 0, 1), EAST(1, 0, 0), SOUTH(0, 0, -1),
        WEST(-1, 0, 0), DOWN(0, -1, 0);
        int x, y, z;
    }
}
