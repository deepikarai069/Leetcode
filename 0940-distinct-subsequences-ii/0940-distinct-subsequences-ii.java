class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1000000007L;

        long[] dp = new long[26];
        long total = 0;

        for (char c : s.toCharArray()) {
            int x = c - 'a';

            long newSubseq = (total + 1) % MOD;

            // Replace subsequences ending with this character
            total = (total + newSubseq - dp[x] + MOD) % MOD;

            dp[x] = newSubseq;
        }

        return (int) total;
    }
}