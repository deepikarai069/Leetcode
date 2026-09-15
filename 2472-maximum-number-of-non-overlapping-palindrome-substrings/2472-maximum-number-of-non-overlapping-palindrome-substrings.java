class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        // dp[i] = maximum number of valid palindromes
        // that can be selected from s[0 ... i-1]
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];

            // Odd length palindrome
            for (int len = k; len <= i; len++) {
                if (isPalindrome(s, i - len, i - 1)) {
                    dp[i] = Math.max(dp[i],
                            dp[i - len] + 1);
                    break;
                }
            }
        }

        return dp[n];
    }

    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}