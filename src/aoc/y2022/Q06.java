package aoc.y2022;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

public class Q06 extends BaseSolution {

    @Run({"input-aoc/Q20221206.txt", "4"})
    public int part1(String s, int k) {
        char[] arr = s.toCharArray();
        int[] counting = new int[26];
        int countUnique = 0;
        int c;
        for (int i = 0; i < arr.length; i++) {
            c = arr[i] - 'a';
            if (counting[c]++ == 0) {
                countUnique++;
            }
            if (i >= k) {
                c = arr[i - k] - 'a';
                if (--counting[c] == 0) {
                    countUnique--;
                }
            }
            if (countUnique == k) return i + 1;
        }
        return 0;
    }

    @Run({"input-aoc/Q20221206.txt", "14"})
    public int part2(String s, int k) {
        return part1(s, k);
    }


}