
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {

        int n = nums.size();
        int minSum = Integer.MAX_VALUE;

        int[] prefix = new int[n + 1];

        // Calculate prefix sum
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums.get(i);
        }

        // Check all subarrays
        for (int i = 0; i < n; i++) {

            for (int len = l; len <= r; len++) {

                int j = i + len;

                if (j > n) {
                    break;
                }

                int sum = prefix[j] - prefix[i];

                if (sum > 0) {
                    minSum = Math.min(minSum, sum);
                }
            }
        }

        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}
