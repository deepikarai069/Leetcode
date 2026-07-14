class Solution {
    private static final int MOD = 1_000_000_007;
    private Long[][][] dp;
    private int[] nums;

    public int subsequencePairCount(int[] nums) {
        this.nums = nums;
        dp = new Long[nums.length][201][201];
        return (int) dfs(0, 0, 0);
    }

    private long dfs(int i, int g1, int g2) {
        if (i == nums.length) {
            return (g1 == g2 && g1 != 0) ? 1 : 0;
        }

        if (dp[i][g1][g2] != null) {
            return dp[i][g1][g2];
        }

        long ans = dfs(i + 1, g1, g2);

        int ng1 = (g1 == 0) ? nums[i] : gcd(g1, nums[i]);
        ans = (ans + dfs(i + 1, ng1, g2)) % MOD;

        int ng2 = (g2 == 0) ? nums[i] : gcd(g2, nums[i]);
        ans = (ans + dfs(i + 1, g1, ng2)) % MOD;

        return dp[i][g1][g2] = ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}