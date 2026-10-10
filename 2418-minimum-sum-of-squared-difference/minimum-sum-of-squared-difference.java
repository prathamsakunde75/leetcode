
class minsum {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long[] diff = new long[n];

        long total = 0;
        long maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs((long) nums1[i] - nums2[i]);
            total += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        long k = (long) k1 + k2;

        if (k >= total) {
            return 0;
        }

        // Binary search for the minimum possible maximum difference
        long left = 0, right = maxDiff;

        while (left < right) {
            long mid = left + (right - left) / 2;
            long needed = 0;

            for (long d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        long level = left;
        long used = 0;
        long answer = 0;

        // Reduce every difference to the selected level
        for (long d : diff) {
            if (d > level) {
                used += d - level;
                d = level;
            }
            answer += d * d;
        }

        // Distribute remaining operations one at a time
        // among differences equal to the selected level.
        long remaining = k - used;

        answer -= remaining * (2 * level - 1);

        return answer;
    }
}
