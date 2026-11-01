
class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        int sum = 0;

        // Find minimum element
        for (int num : nums) {
            min = Math.min(min, num);
        }

        // Calculate total moves
        for (int num : nums) {
            sum += num - min;
        }

        return sum;
    }
}
