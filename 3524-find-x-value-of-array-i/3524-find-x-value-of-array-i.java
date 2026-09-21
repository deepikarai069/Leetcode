class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            int val = num % k;
            long[] next = new long[k];

            // Subarray containing only nums[i]
            next[val]++;

            // Extend all previous subarrays
            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    int newR = (int) ((long) r * val % k);
                    next[newR] += dp[r];
                }
            }

            // Add all subarrays ending at current position
            for (int r = 0; r < k; r++) {
                dp[r] = next[r];
                ans[r] += next[r];
            }
        }

        return ans;
    }
}