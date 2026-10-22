
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        // Case 1: Sort using rotations of the original array.
        int zeroPos = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroPos = i;
                break;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (checkRotation(nums, zeroPos)) {
            // Left rotations, or two reversals to simulate right rotations.
            ans = Math.min(zeroPos, n - zeroPos + 2);
        }

        // Case 2: Reverse, then rotate to obtain the sorted array.
        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = nums[n - 1 - i];
        }

        int k = n - 1 - zeroPos;

        if (checkRotation(reversed, k)) {
            // Rotations can be performed on either side of the reversal.
            ans = Math.min(ans, 1 + Math.min(k, n - k));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private boolean checkRotation(int[] arr, int k) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            if (arr[(i + k) % n] != i) {
                return false;
            }
        }

        return true;
    }
}
