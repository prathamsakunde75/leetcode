
import java.util.*;

class Solution {

    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1000000;

        int[][] cost = new int[n][n];

        // Precompute minimum changes for every substring
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;
                cost[i][j] = INF;

                // Try all valid divisors
                for (int d = 1; d < len; d++) {

                    if (len % d != 0)
                        continue;

                    int changes = 0;

                    // Compare characters in each group
                    for (int r = 0; r < d; r++) {

                        int left = i + r;
                        int right = i + len - d + r;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right))
                                changes++;

                            left += d;
                            right -= d;
                        }
                    }

                    cost[i][j] = Math.min(cost[i][j], changes);
                }
            }
        }

        // DP
        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp)
            Arrays.fill(row, INF);

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {

            for (int i = 1; i <= n; i++) {

                for (int len = 2; len <= i; len++) {

                    int j = i - len;

                    if (dp[parts - 1][j] != INF) {

                        dp[parts][i] = Math.min(
                            dp[parts][i],
                            dp[parts - 1][j] + cost[j][i - 1]
                        );
                    }
                }
            }
        }

        return dp[k][n];
    }
}
