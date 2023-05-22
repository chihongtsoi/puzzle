package janestreetpuzzle;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public class Q202301 extends BaseSolution {

    /*
    [0, 5, 0, 6, 0, 3, 6, 0, 0, 0, 7, 4]
    [0, 7, 7, 1, 0, 0, 5, 0, 4, 6, 5, 0]
    [0, 0, 0, 7, 5, 3, 5, 0, 6, 0, 6, 0]
    [6, 4, 3, 0, 0, 7, 0, 5, 7, 1, 0, 0]
    [7, 0, 0, 0, 2, 7, 4, 2, 0, 0, 0, 7]
    [2, 0, 6, 6, 6, 0, 0, 6, 3, 7, 0, 4]
    [5, 4, 4, 0, 7, 0, 0, 7, 0, 6, 2, 5]
    [7, 0, 0, 0, 1, 5, 7, 1, 0, 0, 7, 0]
    [0, 5, 3, 7, 0, 5, 0, 0, 5, 0, 4, 6]
    [6, 5, 0, 0, 0, 7, 2, 0, 6, 0, 0, 5]
    [0, 6, 7, 3, 0, 0, 4, 6, 6, 4, 0, 0]
    [0, 0, 0, 4, 6, 3, 7, 0, 0, 3, 7, 0]
     */
    private final Integer[][] grid = {
            {0, 5, 0, 6, 0, 3, 6, 0, 0, 0, 7, 4},
            {0, 7, 7, 1, 0, 0, 5, 0, 4, 6, 5, 0},
            {0, 0, 0, 7, 5, 3, 5, 0, 6, 0, 6, 0},
            {6, 4, 3, 0, 0, 7, 0, 5, 7, 1, 0, 0},
            {7, 0, 0, 0, 2, 7, 4, 2, 0, 0, 0, 7},
            {2, 0, 6, 6, 6, 0, 0, 6, 3, 7, 0, 4},
            {5, 4, 4, 0, 7, 0, 0, 7, 0, 6, 2, 5},
            {7, 0, 0, 0, 1, 5, 7, null, null, null, null, null,},
            {0, 5, 3, 7, 0, 5, 0, null, null, null, null, null,},
            {6, 5, 0, 0, 0, 7, 2, null, null, null, null, null,},
            {0, 6, 7, 3, 0, 0, 4, null, null, null, null, null,},
            {0, 0, 0, 4, 6, 3, 7, null, null, null, null, null,},
    };
    /*
    private final Integer[][] grid = {
            {0, null, null, null, null, 3, 6, 0, 0, 0, 7, 4,},
            {null, null, null, 1, null, 0, 5, 0, 4, 6, 5, 0,},
            {null, null, null, null, null, 3, 5, 0, 6, 0, 6, 0,},
            {null, 4, null, null, null, 7, 0, 5, 7, 1, 0, 0,},
            {null, null, null, null, 2, 7, 4, 2, 0, 0, 0, 7,},
            {2, 0, 6, 6, 6, 0, 0, 6, 3, 7, 0, 4,},
            {5, 4, 4, 0, 7, 0, 0, 7, 0, 6, 2, 5,},
            {7, 0, 0, 0, 1, 5, 7, null, null, null, null, null,},
            {0, 5, 3, 7, 0, 5, 0, null, null, null, null, null,},
            {6, 5, 0, 0, 0, 7, 2, null, null, null, null, null,},
            {0, 6, 7, 3, 0, 0, 4, null, null, null, null, null,},
            {0, 0, 0, 4, 6, 3, 7, null, null, null, null, 0,},
    };



     */
     /*
    private static final Integer[][] grid = new Integer[12][12];
    static {
        grid[0][0] = 0;
        grid[1][3] = 1;
        grid[3][1] = 4;
        grid[4][4] = 2;
        grid[2][5] = 3;

        grid[0][10] = 7;
        grid[0][11] = 4;

        grid[1][9] = 6;
        grid[1][10] = 5;

        grid[2][10] = 6;
        grid[3][8] = 7;
        grid[4][11] = 7;

        grid[5][2] = 6;
        grid[5][8] = 3;
        grid[5][9] = 7;


        grid[8][3] = 7;
        grid[8][5] = 5;

        grid[9][1] = 5;
        grid[9][5] = 7;

        grid[10][1] = 6;
        grid[10][2] = 7;

        grid[11][4] = 6;
        grid[11][1] = 0;
    }

      */
    private static final int[][] grids = {
            {0, 0, 7, 7},
            {0, 5, 7, 12},
            {5, 0, 12, 7},
            {5, 5, 12, 12},
    };

    private final Integer[] start_row = {5, 7, 7, 33, 29, 2, 40, 28, null, null, 36, null};
    private final Integer[] start_col = {6, 16, 30, 34, 27, 3, 40, 27, null, null, 7, null};
    private final Integer[] end_row = {4, null, null, 1, null, null, null, null, null, null, null, 7};
    private final Integer[] end_col = {6, null, null, 4, null, null, null, null, null, null, null, 5};

    private final int[] rsum = new int[12];
    private final int[] csum = new int[12];

    private final int[][] row_sum = new int[4][7];
    private final int[][] col_sum = new int[4][7];
    private final int[][] row_count = new int[4][7];
    private final int[][] col_count = new int[4][7];
    private final int[][] grid_count = new int[4][8];
    private final int[][] grid2_count = new int[13][13];


    @Run()
    public void solve() {
        init();
        backtracking(0, 0);
    }

    private void init() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                Integer n = grid[i][j];
                if (n != null && n!= 0) {
                    rsum[i] += n;
                    csum[j] += n;
                    Region[] regions = Region.getRegions(i, j);
                    for (Region r : regions) {
                        if (!r.valid) break;
                        row_sum[r.r][r.x] += n;
                        col_sum[r.r][r.y] += n;
                        row_count[r.r][r.x]++;
                        col_count[r.r][r.y]++;
                        grid_count[r.r][n]++;
                    }
                    grid2_count[i + 1][j + 1]++;
                    grid2_count[i + 1][j]++;
                    grid2_count[i][j + 1]++;
                }
            }
        }
        System.out.println(Arrays.deepToString(row_sum));
        System.out.println(Arrays.deepToString(col_sum));
        System.out.println(Arrays.deepToString(row_count));
        System.out.println(Arrays.deepToString(col_count));
        System.out.println(Arrays.deepToString(grid_count));
        System.out.println(Arrays.deepToString(grid2_count));
    }


    private void backtracking(int x, int y) {

        if (x >= 12) {
            if(finalCheck()) System.out.println("r"+ Arrays.deepToString(grid) + Arrays.deepToString(row_sum) + Arrays.deepToString(col_sum));
            return;
        }

        int nx = x + (y + 1) / 12;
        int ny =( y + 1) % 12;
        if (grid[x][y] != null) {
            if(endRowCheck2(x, y) && endColCheck2(x, y)) backtracking(nx, ny);
            return;
        }
        if (!endRowCheck1(x, y) || !endColCheck1(x, y)) return;
        boolean valid = true;
        Region[] regions = Region.getRegions(x, y);
        set(x, y, 0, regions);
        if(endRowCheck2(x, y) && endColCheck2(x, y)) backtracking(nx, ny);
        rollback(x, y, 0, regions);
        for (Region r : regions) {
            if (!r.valid) break;
            if (row_count[r.r][r.x] >= 4 || col_count[r.r][r.y] >= 4 || grid2_count[x][y] >= 3
                    || row_sum[r.r][r.x] >= 20 || col_sum[r.r][r.y] >= 20) {
                //System.out.println("rule "+x+" "+y+" "+Arrays.toString(grid[0])+" "+row_count[r.r][r.x]+" "+col_count[r.r][r.y]+" "
                //+grid2_count[x][y]+" "+row_sum[r.r][r.x]+" " +col_sum[r.r][r.y]+ " "+Arrays.toString(regions));
                valid = false;
                break;
            }
        }
        if (!valid) return;
        int min = Integer.MAX_VALUE;
        for (Region r : regions) {
            if (!r.valid) break;
            min = Math.min(min, 20 - row_sum[r.r][r.x]);
            min = Math.min(min, 20 - col_sum[r.r][r.y]);
        }

        //System.out.println(x+" "+y+" min"+min +" "+ Arrays.toString(row_sum[0]) + Arrays.toString(col_sum[0]));
        for (int i = 1; i <= 7; i++) {
            valid = true;
            for (Region r : regions) {
                if (!r.valid) break;
                if (grid_count[r.r][i] >= i) {
                    valid = false;
                    break;
                }
            }
            if (!valid) continue;
            valid = true;
            set(x, y, i, regions);
            for (Region r : regions) {

                if (!r.valid) break;
                if (row_sum[r.r][r.x] > 20 || col_sum[r.r][r.y] > 20) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                rollback(x, y, i, regions);
                break;
            }
            if (endRowCheck2(x, y) && endColCheck2(x, y)) backtracking(nx, ny);
            rollback(x, y, i, regions);

        }
    }

    private boolean endRowCheck1(int x, int y) {

        if (y == 6) {
            int r = x > 6 ? 2 : 0;
            int _x = x >= 7 ? x - 5 : x;
            if (row_count[r][_x] < 3 || row_sum[r][_x] < 13) {
                //System.out.println("endRowCheck1 "+x+" "+y+" "+row_count[r][_x]+" "+row_sum[r][_x] + Arrays.toString(grid[0]));
                return false;
            }
        } else if (y == 11) {
            int r = x > 6 ? 3 : 1;
            int _x = x >= 7 ? x - 5 : x;
            if (row_count[r][_x] < 3 || row_sum[r][_x] < 13) return false;
        }
        return true;
    }

    private boolean endRowCheck2(int x, int y) {
        if (y == 6) {
            int r = x > 6 ? 2 : 0;
            int _x = x >= 7 ? x - 5 : x;
            if (row_count[r][_x] != 4 || row_sum[r][_x] != 20) {
                //System.out.println("endRowCheck2");
                return false;
            }
        } else if (y == 11) {
            int r = x > 6 ? 3 : 1;
            int _x = x >= 7 ? x - 5 : x;
            if (row_count[r][_x] != 4 || row_sum[r][_x] != 20) return false;
            if (start_row[x] != null && start_row[x] >= 20 && start_row[x] != rsum[x]) {
                return false;
            }
        }
        return true;
    }

    private boolean endColCheck1(int x, int y) {
        if (x == 6) {
            int r = y / 6;
            int _y = y >= 7 ? y - 5 : y;
            if (col_count[r][_y] < 3 || col_sum[r][_y] < 13) return false;
        } else if (x == 11) {
            int r = y / 6 + 2;
            int _y = y >= 7 ? y - 5 : y;
            if (col_count[r][_y] < 3 || col_sum[r][_y] < 13) return false;
        }
        return true;
    }

    private boolean endColCheck2(int x, int y) {
        if (x == 6) {
            int r = y / 6;
            int _y = y >= 7 ? y - 5 : y;
            if (col_count[r][_y] != 4 || col_sum[r][_y] != 20) return false;
        } else if (x == 11) {
            int r = y / 6 + 2;
            int _y = y >= 7 ? y - 5 : y;
            if (col_count[r][_y] != 4 || col_sum[r][_y] != 20) {
                //System.out.println("endColCheck2");
                return false;
            }
        }
        return true;
    }
    private boolean finalCheck(){
        for (int i = 0; i < 12; i++) {
            if(start_row[i] != null && start_row[i]<8 && start_row[i] != getRowFirstNumber(i)) return false;
            if(end_row[i] != null && end_row[i]<8 && end_row[i] != getRowLastNumber(i)) return false;
            if(start_col[i] != null && start_col[i]<8 && start_col[i] != getColFirstNumber(i)) return false;
            if(end_col[i] != null && end_col[i]<8 && end_col[i] != getColLastNumber(i)) return false;

            if(start_row[i] != null && start_row[i]>20 && start_row[i] != rsum[i]) return false;
            if(start_col[i] != null && start_col[i]>20 && start_col[i] != csum[i]) return false;


        }
        return dfsCount()>=75;
    }
    private int getRowFirstNumber(int x){
        for (int i = 0; i < 12; i++) {
            if(grid[x][i]!=null && grid[x][i]!=0) return grid[x][i];
        }
        return -1;
    }
    private int getRowLastNumber(int x){
        for (int i = 11; i>=0 ; i--) {
            if(grid[x][i]!=null && grid[x][i]!=0) return grid[x][i];
        }
        return -1;
    }
    private int getColFirstNumber(int x){
        for (int i = 0; i < 12; i++) {
            if(grid[i][x]!=null && grid[i][x]!=0) return grid[i][x];
        }
        return -1;
    }
    private int getColLastNumber(int x){
        for (int i = 11; i>=0 ; i--) {
            if(grid[i][x]!=null && grid[i][x]!=0) return grid[i][x];
        }
        return -1;
    }
    private void set(int x, int y, int n, Region[] regions) {
        grid[x][y] = n;
        if (n == 0) return;
        for (Region r : regions) {
            if (!r.valid) break;
            row_sum[r.r][r.x] += n;
            col_sum[r.r][r.y] += n;
            row_count[r.r][r.x]++;
            col_count[r.r][r.y]++;
            grid_count[r.r][n]++;
        }
        rsum[x] += n;
        csum[y] += n;

        grid2_count[x + 1][y + 1]++;
        grid2_count[x + 1][y]++;
        grid2_count[x][y + 1]++;
    }

    private void rollback(int x, int y, int n, Region[] regions) {
        grid[x][y] = null;
        if (n == 0) return;
        for (Region r : regions) {
            if (!r.valid) break;
            row_sum[r.r][r.x] -= n;
            col_sum[r.r][r.y] -= n;
            row_count[r.r][r.x]--;
            col_count[r.r][r.y]--;
        }
        rsum[x] -= n;
        csum[y] -= n;
        grid2_count[x + 1][y + 1]--;
        grid2_count[x + 1][y]--;
        grid2_count[x][y + 1]--;
    }


    private int dfsCount(){
        boolean[][] visited = new boolean[12][12];
        return dfs(0, 1, visited);
    }
    private int dfs(int x, int y,boolean[][] visited ){
        visited[x][y] = true;
        int count = 1;
        if(x>0 && !visited[x-1][y] &&grid[x-1][y]!=0) count+=dfs(x-1, y, visited);
        if(y>0 && !visited[x][y-1]&&grid[x][y-1]!=0) count+=dfs(x, y-1, visited);
        if(x<11 && !visited[x+1][y]&&grid[x+1][y]!=0) count+=dfs(x+1, y, visited);
        if(y<11 && !visited[x][y+1]&&grid[x][y+1]!=0) count+=dfs(x, y+1, visited);
        return count;
    }

    static class Region {
        private static final Region[] regions = new Region[5];

        static {
            for (int i = 0; i < 5; i++) regions[i] = new Region(false);

        }

        int r;
        int x;
        int y;

        public Region(boolean valid) {
            this.valid = valid;
        }

        boolean valid;

        public static Region[] getRegions(int x, int y) {
            Region[] regions  = new Region[5];
            int index = 0;
            for (int k = 0; k < 4; k++) {
                int[] g = grids[k];
                if (x >= g[0] && x < g[2] && y >= g[1] && y < g[3]) {
                    Region r = new Region(true);
                    r.x = g[0] >= 5 ? x - 5 : x;
                    r.y = g[1] >= 5 ? y - 5 : y;
                    r.r = k;
                    regions[index++] = r;
                }
            }
            regions[index] = new Region(false);
            return regions;
        }

        @Override
        public String toString() {
            return "Region{" +
                    "r=" + r +
                    ", x=" + x +
                    ", y=" + y +
                    ", valid=" + valid +
                    '}';
        }
    }

}
