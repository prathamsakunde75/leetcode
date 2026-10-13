
import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] squares = new long[nums.length];

        for (int i = 0; i < nums.length; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long score = 0;
        int n = squares.length;

        for (int i = 0; i < n; i++) {
            if (i < (n + 1) / 2) {
                score += squares[n - 1 - i];
            } else {
                score -= squares[n - 1 - i];
            }
        }

        return score;
    }
}
