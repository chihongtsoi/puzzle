package paiza.a;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Scanner;

public class A059 extends BaseSolution {


    @Run({"5 20\n" +
            "19 10\n" +
            "19 19\n" +
            "7 6\n" +
            "7 3\n" +
            "13 15"})
    public int solve(String s) {
        Scanner sc = new Scanner(s);
        int N = sc.nextInt();
        int x = sc.nextInt();
        int[][] fish = new int[N][2];

        for (int i = 0; i < N; i++) {
            fish[i][0] = sc.nextInt();
            fish[i][1] = sc.nextInt();
        }
        int[][] dp = new int[fish.length + 1][x];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                dp[i][j] = j >= fish[i - 1][0] ?
                        Math.max(dp[i - 1][j], dp[i - 1][j - fish[i - 1][0]] + fish[i - 1][1]) :
                        dp[i - 1][j];

            }
        }
        return dp[fish.length][x - 1];
    }

}
