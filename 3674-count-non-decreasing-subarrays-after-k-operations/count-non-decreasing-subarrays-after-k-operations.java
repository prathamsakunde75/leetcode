
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 18;

        int[] nge = new int[n];
        int[] stack = new int[n];
        int top = -1;

        // Find next strictly greater element
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            nge[i] = (top == -1) ? n : stack[top];
            stack[++top] = i;
        }

        // Binary lifting tables
        int[][] up = new int[LOG][n];
        long[][] sum = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = nge[i];

            if (nge[i] < n) {
                sum[0][i] =
                    (long) (nge[i] - i) * nums[i];
            }
        }

        for (int p = 1; p < LOG; p++) {
            for (int i = 0; i < n; i++) {
                int mid = up[p - 1][i];

                if (mid < n) {
                    up[p][i] = up[p - 1][mid];

                    sum[p][i] =
                        sum[p - 1][i] + sum[p - 1][mid];
                } else {
                    up[p][i] = n;
                }
            }
        }

        // Prefix sum
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Calculate minimum operations for [l, r]
        long[] ignored = new long[1];

        int left = 0;
        long answer = 0;

        for (int right = 0; right < n; right++) {

            while (left <= right &&
                   getCost(left, right, nums, up, sum,
                           prefix, LOG) > k) {
                left++;
            }

            answer += right - left + 1;
        }

        return answer;
    }

    private long getCost(
        int l, int r,
        int[] nums,
        int[][] up,
        long[][] sum,
        long[] prefix,
        int LOG
    ) {
        long maxSum = 0;
        int cur = l;

        // Jump through next greater elements
        for (int p = LOG - 1; p >= 0; p--) {
            int next = up[p][cur];

            if (next <= r) {
                maxSum += sum[p][cur];
                cur = next;
            }
        }

        // Remaining final block
        maxSum += (long) (r - cur + 1) * nums[cur];

        long originalSum = prefix[r + 1] - prefix[l];

        return maxSum - originalSum;
    }
}
