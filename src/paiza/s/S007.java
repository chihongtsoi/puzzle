package paiza.s;

import objectprinter.oj.BaseSolution;
import objectprinter.oj.Run;

import java.util.Stack;

public class S007 extends BaseSolution {

    @Run("10000(10000(10000(2000(ab)500(dz)c200h)2mu3000(fpr)))")
    public long[] solve(String s) {
        long[] count = new long[26];
        char[] arr = s.toCharArray();
        long multiplier = 1;
        Stack<Long> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            if (Character.isDigit(arr[i])) {
                long n = 0;
                while (i < arr.length && Character.isDigit(arr[i])) {
                    n *= 10;
                    n += arr[i++] - '0';
                }
                if (arr[i] == '(') {
                    stack.push(n);
                    multiplier *= n;
                } else {
                    count[arr[i] - 'a'] += multiplier * n;
                }
            } else if (arr[i] == ')') {
                multiplier /= stack.pop();
            } else {
                count[arr[i] - 'a'] += multiplier;
            }
        }
        return count;
    }
}
