
class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1000000007;

        long even = 1;
        long odd = 0;
        long prefix = 0;
        long ans = 0;

        for (int num : arr) {
            prefix += num;

            if (prefix % 2 == 0) {
                ans = (ans + odd) % MOD;
                even++;
            } else {
                ans = (ans + even) % MOD;
                odd++;
            }
        }

        return (int) ans;
    }
}
