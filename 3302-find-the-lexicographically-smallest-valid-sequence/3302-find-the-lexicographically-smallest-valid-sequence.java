class Solution {
    public int[] validSequence(String word1, String word2) {
        char[] a = word1.toCharArray();
        char[] b = word2.toCharArray();

        int n = a.length;
        int m = b.length;

        // dp[i] = maximum number of characters of word2
        // that can be matched using word1[i...]
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && a[i] == b[j]) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        while (i < n && j < m) {

            // Exact match: always take the earliest index
            if (a[i] == b[j]) {
                ans[j] = i;
                j++;
            } 
            // Use our one allowed mismatch
            else {
                int remaining = m - 1 - j;

                // Can the rest be matched exactly?
                if (dp[i + 1] >= remaining) {
                    ans[j] = i;
                    j++;
                    i++;

                    // Mismatch has been used.
                    break;
                }
            }

            i++;
        }

        // Couldn't choose enough indices
        if (j < m && i == n) {
            return new int[0];
        }

        // Match the remaining characters exactly
        while (j < m && i < n) {
            if (a[i] == b[j]) {
                ans[j] = i;
                j++;
            }
            i++;
        }

        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}