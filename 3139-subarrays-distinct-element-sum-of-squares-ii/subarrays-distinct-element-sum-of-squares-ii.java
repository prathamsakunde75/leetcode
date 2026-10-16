class Solution {
    static final long MOD = 1_000_000_007L;

    long[] sum, sq, lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        java.util.HashMap<Integer, Integer> last = new java.util.HashMap<>();

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last.getOrDefault(nums[i], -1);

            // Increase distinct counts for affected subarrays
            update(1, 0, n - 1, prev + 1, i);

            last.put(nums[i], i);

            // Sum of squares for subarrays ending at i
            ans = (ans + sqQuery(1, 0, n - 1, 0, i)) % MOD;
        }

        return (int) ans;
    }

    void update(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            long len = end - start + 1L;

            sq[node] = (sq[node] + 2L * sum[node] + len) % MOD;
            sum[node] = (sum[node] + len) % MOD;
            lazy[node]++;

            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        update(node * 2, start, mid, l, r);
        update(node * 2 + 1, mid + 1, end, l, r);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }

    void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) return;

        int mid = (start + end) / 2;
        long v = lazy[node];

        apply(node * 2, start, mid, v);
        apply(node * 2 + 1, mid + 1, end, v);

        lazy[node] = 0;
    }

    void apply(int node, int start, int end, long v) {
        long len = end - start + 1L;

        sq[node] = (sq[node] + 2L * v * sum[node]
                    + v * v % MOD * len) % MOD;

        sum[node] = (sum[node] + v * len) % MOD;
        lazy[node] = (lazy[node] + v) % MOD;
    }

    long sqQuery(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return 0;

        if (l <= start && end <= r) {
            return sq[node];
        }

        push(node, start, end);

        int mid = (start + end) / 2;

        long left = sqQuery(node * 2, start, mid, l, r);
        long right = sqQuery(node * 2 + 1, mid + 1, end, l, r);

        return (left + right) % MOD;
    }
}