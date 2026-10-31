class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        long MOD = 1000000007;

        long[][] right = new long[m][n];
        long[][] down = new long[m][n];

        long ans = 0;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                long ways;

                // Starting position
                if (i == 0 && j == 0) {
                    ways = 1;
                } else {

                    ways = (right[i][j] + down[i][j]) % MOD;

                }

                // Destination reached
                if (i == m - 1 && j == n - 1) {
                    ans = ways;
                    continue;
                }

                if (grid[i][j] == 0) {

                    // Empty cell: move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + ways) % MOD;
                    }

                    // Empty cell: move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + ways) % MOD;
                    }

                } else {

                    // Mirror: moving right becomes down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + right[i][j]) % MOD;
                    }

                    // Mirror: moving down becomes right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + down[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) ans;
    }
}