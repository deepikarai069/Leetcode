class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++)
            pre[i + 1] = pre[i] + stoneValue[i];

        int[][] dp = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;

                for (int k = i; k < j; k++) {
                    int left = pre[k + 1] - pre[i];
                    int right = pre[j + 1] - pre[k + 1];

                    if (left < right)
                        dp[i][j] = Math.max(dp[i][j],
                                left + dp[i][k]);
                    else if (left > right)
                        dp[i][j] = Math.max(dp[i][j],
                                right + dp[k + 1][j]);
                    else
                        dp[i][j] = Math.max(dp[i][j],
                                Math.max(left + dp[i][k],
                                         right + dp[k + 1][j]));
                }
            }
        }

        return dp[0][n - 1];
    }
}