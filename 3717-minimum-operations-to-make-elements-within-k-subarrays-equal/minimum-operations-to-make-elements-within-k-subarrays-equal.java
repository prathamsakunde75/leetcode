
import java.util.*;

class Solution {

    int[] bitCount;
    long[] bitSum;
    int size;

    void update(int[] bit, int idx, int val) {
        while (idx <= size) {
            bit[idx] += val;
            idx += idx & -idx;
        }
    }

    void update(long[] bit, int idx, long val) {
        while (idx <= size) {
            bit[idx] += val;
            idx += idx & -idx;
        }
    }

    int query(int[] bit, int idx) {
        int sum = 0;

        while (idx > 0) {
            sum += bit[idx];
            idx -= idx & -idx;
        }

        return sum;
    }

    long querySum(int idx) {
        long sum = 0;

        while (idx > 0) {
            sum += bitSum[idx];
            idx -= idx & -idx;
        }

        return sum;
    }

    void add(int idx, int value) {
        update(bitCount, idx, 1);
        update(bitSum, idx, value);
    }

    void remove(int idx, int value) {
        update(bitCount, idx, -1);
        update(bitSum, idx, -(long)value);
    }

    int kth(int k) {
        int idx = 0;
        int mask = Integer.highestOneBit(size);

        while (mask > 0) {
            int next = idx + mask;

            if (next <= size && bitCount[next] < k) {
                idx = next;
                k -= bitCount[next];
            }

            mask >>= 1;
        }

        return idx + 1;
    }

    long getCost(int len, int[] values) {

        int mid = kth((len + 1) / 2);

        long leftCount = query(bitCount, mid);
        long leftSum = querySum(mid);

        long totalSum = querySum(size);
        long rightCount = len - leftCount;
        long rightSum = totalSum - leftSum;

        long median = values[mid - 1];

        return median * leftCount - leftSum
             + rightSum - median * rightCount;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] values = Arrays.stream(sorted).distinct().toArray();

        size = values.length;

        bitCount = new int[size + 1];
        bitSum = new long[size + 1];

        int[] compressed = new int[n];

        for (int i = 0; i < n; i++) {
            compressed[i] =
                Arrays.binarySearch(values, nums[i]) + 1;
        }

        int m = n - x + 1;
        long[] cost = new long[m];

        // Calculate first window cost
        for (int i = 0; i < x; i++) {
            add(compressed[i], nums[i]);
        }

        cost[0] = getCost(x, values);

        // Sliding window
        for (int i = x; i < n; i++) {

            remove(compressed[i - x], nums[i - x]);
            add(compressed[i], nums[i]);

            cost[i - x + 1] = getCost(x, values);
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {

            for (int i = 1; i <= n; i++) {

                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {

                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
